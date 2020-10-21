package com.test.bookingsystem.job;

import com.test.bookingsystem.model.persistence.BookedTicket;
import com.test.bookingsystem.model.persistence.BookedTicketId;
import com.test.bookingsystem.model.persistence.OpenShow;
import com.test.bookingsystem.model.persistence.Ticket;
import com.test.bookingsystem.model.persistence.Ticket.TicketStatus;
import com.test.bookingsystem.repository.BookedTicketRepository;
import com.test.bookingsystem.repository.ShowRepository;
import com.test.bookingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RejectBookingJobWorker extends JobWorker<Long> {
    TicketRepository ticketRepository;

    BookedTicketRepository bookedTicketRepository;

    ShowRepository showRepository;

    public static final String WORKER_INPUT_TICKET_ID = "ticketID";

    RejectBookingJobWorker(Job job, TicketRepository ticketRepository, BookedTicketRepository bookedTicketRepository,
                           ShowRepository showRepository) {
        super(job);
        this.ticketRepository = ticketRepository;
        this.bookedTicketRepository = bookedTicketRepository;
        this.showRepository = showRepository;
    }

    @Override
    public Long transformJobInfo(Map<String, String> jobInfo) {
        String ticketString = jobInfo.get(WORKER_INPUT_TICKET_ID);
        return Long.valueOf(ticketString);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
    public void perform(Long ticketId) {
        System.out.println("Thread Name -> " + Thread.currentThread().getName());
        System.out.println("RejectBookingJobWorker job started. TicketId :::: " + ticketId);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(()-> new RuntimeException("Ticket not found"));

        if (ticket.getTicketStatus().equals(TicketStatus.PAID)) {
            System.out.println("Thread Name -> " + Thread.currentThread().getName());
            System.out.println("RejectBookingJobWorker job completed. TicketId :::: " + ticketId);
            return;
        }

        if (ticket.getTicketStatus().equals(TicketStatus.SHOULD_REFUND) || ticket.getTicketStatus().equals(TicketStatus.REFUNDED)
            || ticket.getTicketStatus().equals(TicketStatus.REJECTED)) {
            throw new RuntimeException("Execution of RejectBookingJob is Unexpected ");
        }

        OpenShow openShow = ticket.getShow();
        openShow.getShowSeatsInfo().updateShowSeats(ticket, false);

        List<BookedTicket> bookedTickets = BookedTicketId.getBookedTicketIds(ticket).stream()
                                           .map(bookedTicketId -> new BookedTicket(bookedTicketId))
                                           .collect(Collectors.toList());

        ticket.setTicketStatus(TicketStatus.REJECTED);
        ticketRepository.save(ticket);
        bookedTicketRepository.deleteAll(bookedTickets);
        showRepository.save(openShow);
        System.out.println("Thread Name -> " + Thread.currentThread().getName());
        System.out.println("Rejected ticket. TicketId :::: " + ticketId);
    }
}
