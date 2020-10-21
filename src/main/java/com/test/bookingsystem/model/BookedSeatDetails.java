package com.test.bookingsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class BookedSeatDetails {
    @Getter @Setter
    private List<String> seatNumbers;
    @Getter @Setter
    private String className;
    @Getter @Setter
    private Float seatPrice;
}
