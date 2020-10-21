package com.test.bookingsystem.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.ShowSeatsInfo;

import javax.persistence.AttributeConverter;

public class ShowSeatsInfoConverter implements AttributeConverter<ShowSeatsInfo,String> {
    @Override
    public String convertToDatabaseColumn(ShowSeatsInfo showSeatsInfo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(showSeatsInfo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public ShowSeatsInfo convertToEntityAttribute(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, ShowSeatsInfo.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
