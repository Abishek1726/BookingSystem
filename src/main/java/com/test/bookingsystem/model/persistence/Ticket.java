package com.test.bookingsystem.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.bookingsystem.converter.BookedSeatDetailsConverter;
import com.test.bookingsystem.model.BookedSeatDetails;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;



@Entity
@Table(name="tickets")
public class Ticket {
    public enum TicketStatus { BOOKED, PAID, PAYMENT_FAILED, REJECTED, SHOULD_REFUND, REFUNDED }

    @Getter @Setter
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(targetEntity = OpenShow.class, fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    @Getter @Setter
    @JsonIgnore
    private OpenShow show;

    @Getter @Setter private Float amount;

    @Getter @Setter
    @Column(columnDefinition = "json")
    @Convert(converter = BookedSeatDetailsConverter.class)
    BookedSeatDetails seatDetails;

    @Getter @Setter
    TicketStatus ticketStatus;

    @Getter @Setter
    Long userId;

}

