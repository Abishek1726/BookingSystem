package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.BookedTicket;
import com.test.bookingsystem.model.persistence.BookedTicketId;
import org.springframework.data.repository.CrudRepository;

public interface BookedTicketRepository extends CrudRepository<BookedTicket, BookedTicketId> {
}
