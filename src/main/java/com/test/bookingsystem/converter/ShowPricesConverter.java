package com.test.bookingsystem.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.MovieShowPrice;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.List;

public class ShowPricesConverter implements AttributeConverter<List<MovieShowPrice>, String> {
    @Override
    public String convertToDatabaseColumn(List<MovieShowPrice> movieShowPrices) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(movieShowPrices);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MovieShowPrice> convertToEntityAttribute(String s) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(s, new TypeReference<List<MovieShowPrice>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
