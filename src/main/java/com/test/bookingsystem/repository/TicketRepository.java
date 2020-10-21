package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findByUserIdAndShowIdAndTicketStatus(Long userId, Long openShowId, Ticket.TicketStatus status);
}
