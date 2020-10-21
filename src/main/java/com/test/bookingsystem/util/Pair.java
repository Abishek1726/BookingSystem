package com.test.bookingsystem.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Pair<V1,V2> {
    @Getter
    V1 first;
    @Getter
    V2 second;
}
