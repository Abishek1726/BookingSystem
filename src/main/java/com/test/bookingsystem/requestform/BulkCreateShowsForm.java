package com.test.bookingsystem.requestform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class BulkCreateShowsForm {
    @Getter
    @Setter
    private Long movieShowId;
    @Getter @Setter @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @Getter @Setter @JsonFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
}
