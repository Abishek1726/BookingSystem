package com.test.bookingsystem.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
public class BookedTicketId implements Serializable {
    @Getter @Setter
    private Long showId;

    @Getter @Setter
    private Date showDate;

    @Getter @Setter
    private String seatNumber;

    public static List<BookedTicketId> getBookedTicketIds(Ticket ticket) {
        Long showId = ticket.getShow().getId();
        Date showDate =  ticket.getShow().getShowDate();
        List<String> seats = ticket.getSeatDetails().getSeatNumbers();
        return seats.stream()
                    .map((seatNumber)-> new BookedTicketId(showId, showDate, seatNumber))
                    .collect(Collectors.toList());
    }

}
