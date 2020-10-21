package com.test.bookingsystem.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.persistence.OpenShow;
import com.test.bookingsystem.requestform.BulkCreateShowsForm;
import com.test.bookingsystem.service.ShowService;
import com.test.bookingsystem.util.DateUtil;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcePath.V1_SHOWS_PATH)
public class ShowController {
    @Autowired
    private ShowService showService;
    @PostMapping
    public ResponseEntity<List<OpenShow>> createShows(@RequestBody BulkCreateShowsForm showForm) {
        Optional<List<OpenShow>> openShows = showService.createShows(showForm);
        if(openShows.isPresent()){
            return new ResponseEntity<>(openShows.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<List<OpenShow>> getShows(@RequestParam("from") String from,
                                   @RequestParam("to") String to,
                                   @RequestParam("movie-show-id") Long movieShowId) {
        Optional<Date> fromDateOptional = DateUtil.getDate(from, DateUtil.YYYY_MM_DD_FORMAT);
        Optional<Date> toDateOptional = DateUtil.getDate(to, DateUtil.YYYY_MM_DD_FORMAT);

        if(fromDateOptional.isPresent() && toDateOptional.isPresent()) {
            List<OpenShow> openShowList = showService.getShows(fromDateOptional.get(), toDateOptional.get(), movieShowId);
            return new ResponseEntity<>(openShowList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
