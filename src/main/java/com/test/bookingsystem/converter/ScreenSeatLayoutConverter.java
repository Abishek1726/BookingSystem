package com.test.bookingsystem.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.ScreenSeatLayout;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ScreenSeatLayoutConverter implements AttributeConverter<ScreenSeatLayout,String> {
    @Override
    public String convertToDatabaseColumn(ScreenSeatLayout screenSeatLayout) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(screenSeatLayout);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScreenSeatLayout convertToEntityAttribute(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, ScreenSeatLayout.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
