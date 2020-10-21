package com.test.bookingsystem.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.BookedSeatDetails;

import javax.persistence.AttributeConverter;

public class BookedSeatDetailsConverter implements AttributeConverter<BookedSeatDetails, String> {
    @Override
    public String convertToDatabaseColumn(BookedSeatDetails bookedSeatDetails) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(bookedSeatDetails);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookedSeatDetails convertToEntityAttribute(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, BookedSeatDetails.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
