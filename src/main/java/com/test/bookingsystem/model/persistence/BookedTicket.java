package com.test.bookingsystem.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "booked_tickets")
@AllArgsConstructor @NoArgsConstructor
public class BookedTicket {
    @EmbeddedId
    @Getter @Setter
    private BookedTicketId bookedTicketId;
}

