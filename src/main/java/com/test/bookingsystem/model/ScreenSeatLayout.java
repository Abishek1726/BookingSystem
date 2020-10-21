package com.test.bookingsystem.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

public class ScreenSeatLayout {
    @Getter @Setter private List<SeatGroup> seatGroups;

    @Getter @Setter private Integer totalSeats;

    public static class SeatGroup {
        @Getter @Setter private String seatClass;

        @Getter @Setter private List<String> seats;

        @Getter @Setter private Integer seatCount;
    }

}
