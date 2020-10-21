package com.test.bookingsystem.model;

import com.test.bookingsystem.model.persistence.MovieShow;
import com.test.bookingsystem.model.persistence.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
public class ShowSeatsInfo {
    @Getter @Setter
    private List<ShowSeatGroup> showSeatGroups = new ArrayList<>();

    public static ShowSeatsInfo constructShowSeatLayoutInfo(ScreenSeatLayout screenSeatLayout, MovieShow movieShow) {
        ShowSeatsInfo showSeatsInfo = new ShowSeatsInfo();
        List<MovieShowPrice> movieShowPrices = movieShow.getShowPrices();
        List<String> movieShowClasses = movieShowPrices.stream()
                                                       .map(movieShowPrice -> movieShowPrice.getSeatClass())
                                                       .collect(Collectors.toList());

        screenSeatLayout.getSeatGroups().stream()
                .filter(seatGroup -> movieShowClasses.contains(seatGroup.getSeatClass()))
                .forEach(showSeatsInfo::addShowSeatGroups);

        movieShowPrices.forEach(showSeatsInfo::setSeatGroupPrice);
        return showSeatsInfo;
    }

    public void updateShowSeats(Ticket ticket, Boolean isBooked) {
         BookedSeatDetails seatDetails = ticket.getSeatDetails();
         String bookedClass = seatDetails.getClassName();
         ShowSeatGroup showSeatGroup = showSeatGroups.stream()
                                       .filter( showSeatGrp -> showSeatGrp.getClassName().equals(bookedClass) )
                                       .findFirst()
                                       .orElseThrow(()-> new RuntimeException("Invalid Seat class in ticket"));

         Consumer<String> seatConsumer = (isBooked) ? (showSeatGroup::bookShowSeats) : (showSeatGroup::cancelShowSeats);
         seatDetails.getSeatNumbers().forEach(seatConsumer);
    }

    private void addShowSeatGroups(ScreenSeatLayout.SeatGroup seatGroup) {
        ShowSeatGroup showSeatGroup = new ShowSeatGroup();
        showSeatGroup.setClassName(seatGroup.getSeatClass());
        showSeatGroup.setSeatCount(seatGroup.getSeatCount());
        showSeatGroup.setBookedSeatCount(0);
        seatGroup.getSeats().forEach(s -> showSeatGroup.getShowSeats().put(s, false));
        this.showSeatGroups.add(showSeatGroup);
    }

    private void setSeatGroupPrice(MovieShowPrice movieShowPrice) {
        showSeatGroups.stream().filter(showSeatGroup -> showSeatGroup.getClassName().equals(movieShowPrice.getSeatClass()))
                               .findFirst()
                               .ifPresent(showSeatGroup -> showSeatGroup.setSeatPrice(movieShowPrice.getPrice()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    public static class ShowSeatGroup {
        @Getter @Setter private String className;

        @Getter @Setter private Map<String,Boolean> showSeats = new HashMap<>();

        @Getter @Setter private Integer seatCount;

        @Getter @Setter private Integer bookedSeatCount;

        @Getter @Setter private Float seatPrice;

        void bookShowSeats(String seat) {
            updateShowSeat(seat, true);
        }

        void cancelShowSeats(String seat) {
            updateShowSeat(seat, false);
        }

        private void updateShowSeat(String seatNumber, Boolean isBooked) {
            if(this.showSeats.containsKey(seatNumber)) {
                showSeats.put(seatNumber, isBooked);
                return;
            }
            throw new RuntimeException("Invalid Seat number");
        }

    }
}
