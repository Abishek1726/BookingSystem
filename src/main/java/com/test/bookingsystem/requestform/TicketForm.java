package com.test.bookingsystem.requestform;

import com.test.bookingsystem.model.BookedSeatDetails;
import lombok.Getter;
import lombok.Setter;

public class TicketForm {
    @Getter @Setter
    Long showId;
    @Getter @Setter
    BookedSeatDetails seatDetails;
}
