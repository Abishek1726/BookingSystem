package com.test.bookingsystem.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class MovieShowPrice {
    @Getter @Setter private String seatClass;
    @Getter @Setter private Float price;
}
