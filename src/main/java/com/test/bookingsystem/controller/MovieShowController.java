package com.test.bookingsystem.controller;

import com.test.bookingsystem.model.persistence.MovieShow;
import com.test.bookingsystem.requestform.MovieShowForm;
import com.test.bookingsystem.service.MovieShowService;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcePath.V1_MOVIE_SHOWS_PATH)
public class MovieShowController {
    @Autowired
    MovieShowService movieShowService;

    @PostMapping
    public ResponseEntity<MovieShow> createNew(@RequestBody MovieShowForm movieShowForm) {
        Optional<MovieShow> movieShowOptional = movieShowService.create(movieShowForm);
        if(movieShowOptional.isPresent()) {
            return new ResponseEntity<>(movieShowOptional.get(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<MovieShow> getMovieShowsByMovieName(@RequestParam("movie-name") String movieName) {
        return movieShowService.getMovieShowsByMovieName(movieName);
    }
}
