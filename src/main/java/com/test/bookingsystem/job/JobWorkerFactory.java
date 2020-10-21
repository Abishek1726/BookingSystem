package com.test.bookingsystem.job;

import com.test.bookingsystem.repository.BookedTicketRepository;
import com.test.bookingsystem.repository.ShowRepository;
import com.test.bookingsystem.repository.TheatreRepository;
import com.test.bookingsystem.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Optional;

public class JobWorkerFactory {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    BookedTicketRepository bookedTicketRepository;

    @Autowired
    ShowRepository showRepository;

    public JobWorker createWorker(Job job) {
        Optional<JobMappings> jobMappingsOptional = JobMappings.getJobMapping(job.getJobName());
        if(jobMappingsOptional.isPresent()) {
            JobMappings jobMappings = jobMappingsOptional.get();
            switch (jobMappings) {
                case REJECT_BOOKED_TICKET_JOB:
                    return new RejectBookingJobWorker(job, ticketRepository, bookedTicketRepository, showRepository);
                case DUMMY_JOB:
                    return new DummyJobWorker(job, showRepository);
            }
        }
        throw new IllegalArgumentException();
    }
}