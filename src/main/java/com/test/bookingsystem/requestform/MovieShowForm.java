package com.test.bookingsystem.requestform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.bookingsystem.model.MovieShowPrice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

public class MovieShowForm {
    @Getter @Setter Long screenId;
    @Getter @Setter String movieName;
    @Getter @Setter List<MovieShowPrice> showPrices;

    @Getter @Setter
    @JsonFormat(pattern="HH:mm")
    private Date startTime;

    @Getter @Setter
    @JsonFormat(pattern="HH:mm")
    private Date endTime;
}
