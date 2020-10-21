package com.test.bookingsystem.service;

import com.test.bookingsystem.job.Job;
import com.test.bookingsystem.job.JobMappings;
import com.test.bookingsystem.job.JobQueue;
import com.test.bookingsystem.job.RejectBookingJobWorker;
import com.test.bookingsystem.model.persistence.*;
import com.test.bookingsystem.repository.BookedTicketRepository;
import com.test.bookingsystem.repository.ShowRepository;
import com.test.bookingsystem.repository.TicketRepository;
import com.test.bookingsystem.requestform.TicketForm;
import com.test.bookingsystem.util.DateUtil;
import com.test.bookingsystem.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import com.test.bookingsystem.model.persistence.Ticket.TicketStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BookedTicketRepository bookedTicketRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private JobQueue jobQueue;

    public Pair<String,Optional<Ticket>> createTicket(TicketForm ticketForm) {
        Optional<OpenShow> openShow = showRepository.findById(ticketForm.getShowId());
        if(!openShow.isPresent()) {
            return new Pair<>("INVALID_SHOW_ID", Optional.empty());
        }

        Ticket ticket = buildTicket(ticketForm, openShow.get());

        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
        return transactionTemplate.execute(transactionStatus -> {
            try {
                if (!validateTicketCountBooked(ticket)) {
                    return new Pair<>("SEAT_MAX_LIMIT_ERROR", Optional.empty());
                }

                List<BookedTicketId> bookedTicketIds = BookedTicketId.getBookedTicketIds(ticket);
                if( isAnySeatAlreadyBooked(bookedTicketIds) ) {
                    return new Pair<>("SEAT_ALREADY_BOOKED", Optional.empty());
                }
                OpenShow show = ticket.getShow();
                show.getShowSeatsInfo().updateShowSeats(ticket, true);
                List<BookedTicket> bookedTickets = bookedTicketIds.stream().map(BookedTicket::new).collect(Collectors.toList());

                ticket.setTicketStatus(TicketStatus.BOOKED);

                ticketRepository.save(ticket);
                showRepository.save(show);
                bookedTicketRepository.saveAll(bookedTickets);

                scheduleRejectTicketJob(ticket);
                return new Pair<>("BOOKED", Optional.of(ticket));
            } catch (DataIntegrityViolationException dEx) {
                dEx.printStackTrace();
                transactionManager.rollback(transactionStatus);
                return new Pair<>("SEAT_ALREADY_BOOKED", Optional.empty());
            } catch (RuntimeException re) {
                re.printStackTrace();
                throw re;
            } catch (Exception e) {
                e.printStackTrace();
                transactionManager.rollback(transactionStatus);
                return new Pair<>("ERROR", Optional.empty());
            }
        });
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void paymentSuccess(Long ticketId) {
         Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

         if( ticket.getTicketStatus().equals(TicketStatus.BOOKED) || ticket.getTicketStatus().equals(TicketStatus.PAYMENT_FAILED)) {
             ticket.setTicketStatus(TicketStatus.PAID);
             ticketRepository.save(ticket);
         } else if (ticket.getTicketStatus().equals(TicketStatus.REJECTED)) {
             ticket.setTicketStatus(TicketStatus.SHOULD_REFUND);
             ticketRepository.save(ticket);
        } else {
             throw new RuntimeException("Unexpected payment callback");
         }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void paymentFailure(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));

        if( ticket.getTicketStatus().equals(TicketStatus.PAYMENT_FAILED) ) {
            return;
        }

        if( ticket.getTicketStatus().equals(TicketStatus.BOOKED) ) {
            ticket.setTicketStatus(TicketStatus.PAYMENT_FAILED);
            ticketRepository.save(ticket);
        } else {
            throw new RuntimeException("Unexpected payment callback");
        }
    }

    public Ticket getTicket(Long ticketId) {
        return ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    private static final Integer MAX_ALLOWED_TICKET = 6;

    private Boolean validateTicketCountBooked(Ticket ticket) {
        Integer currentSeatCount = ticket.getSeatDetails().getSeatNumbers().size();

        if (currentSeatCount > MAX_ALLOWED_TICKET) {
            return false;
        }
        User currentUser =  userService.getCurrentUser();
        List<Ticket> ticketsBookedByUser = ticketRepository.findByUserIdAndShowIdAndTicketStatus(currentUser.getId(), ticket.getShow().getId(), TicketStatus.PAID);
        Integer previousBookedCount = ticketsBookedByUser.stream()
                .map(otherTicket -> otherTicket.getSeatDetails().getSeatNumbers().size())
                .reduce(Integer::sum).orElse(0);

        return currentSeatCount + previousBookedCount <= MAX_ALLOWED_TICKET;
    }

    private Boolean isAnySeatAlreadyBooked(List<BookedTicketId> bookedTicketIds) {
        return bookedTicketRepository.findAllById(bookedTicketIds).iterator().hasNext();
    }

    private void scheduleRejectTicketJob(Ticket ticket) {
        Map<String,String> jobInfo = new HashMap<>();
        jobInfo.put(RejectBookingJobWorker.WORKER_INPUT_TICKET_ID, ticket.getId().toString());
        Job rejectTicketJob = new Job(JobMappings.REJECT_BOOKED_TICKET_JOB.getJobName(), jobInfo);
        jobQueue.performIn(rejectTicketJob, DateUtil._1_MINUTES_IN_MILLIS * 2 );
    }

    private Ticket buildTicket(TicketForm form, OpenShow openShow) {
        Ticket ticket = new Ticket();
        ticket.setShow( openShow );
        ticket.setSeatDetails(form.getSeatDetails());
        Float seatPrice = openShow.getShowSeatsInfo()
                                  .getShowSeatGroups()
                                  .stream().filter((showSeatGroup -> showSeatGroup.getClassName().equals(ticket.getSeatDetails().getClassName())))
                                  .map(showSeatGroup -> showSeatGroup.getSeatPrice()).findFirst().get();

        Float amount = seatPrice * form.getSeatDetails().getSeatNumbers().size();
        ticket.getSeatDetails().setSeatPrice(seatPrice);
        ticket.setAmount(amount);
        ticket.setUserId( userService.getCurrentUser().getId() );
        return ticket;
    }
}
