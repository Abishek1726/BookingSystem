package com.test.bookingsystem.controller;

import com.test.bookingsystem.model.persistence.MovieShow;
import com.test.bookingsystem.model.persistence.Screen;
import com.test.bookingsystem.service.MovieShowService;
import com.test.bookingsystem.service.ScreenService;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ResourcePath.V1_SCREENS_PATH)
public class ScreenController {
    @Autowired
    ScreenService screenService;
    @Autowired
    MovieShowService movieShowService;

    @GetMapping
    public ResponseEntity<List<Screen>> getScreens(@PathVariable Long theatreId){
        Optional<List<Screen>> screensOptional = screenService.getScreens(theatreId);
        if(screensOptional.isPresent()) {
            return new ResponseEntity<>(screensOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screen> getScreen(@PathVariable Long id){
        Optional<Screen> screenOptional = screenService.getScreen(id);
        if(screenOptional.isPresent()) {
            return new ResponseEntity<>(screenOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{id}/movie-shows")
    public List<MovieShow> getMovieShowsForScreen(@PathVariable Long id) {
        return movieShowService.getMovieShowsByScreen(id);
    }

    @PostMapping
    public ResponseEntity<Screen> createNew(@PathVariable Long theatreId, @RequestBody Screen screen) {
        screen.setTheatreId(theatreId);
        screen = screenService.create(screen);
        return new ResponseEntity<>(screen, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> update(@PathVariable Long theatreId, @RequestBody Screen screen) {
        screen.setTheatreId(theatreId);
        if( screenService.update(screen) ) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
