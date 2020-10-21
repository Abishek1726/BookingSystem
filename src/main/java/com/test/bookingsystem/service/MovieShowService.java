package com.test.bookingsystem.service;

import com.test.bookingsystem.model.persistence.MovieShow;
import com.test.bookingsystem.model.persistence.Screen;
import com.test.bookingsystem.repository.MovieShowRepository;
import com.test.bookingsystem.repository.ScreenRepository;
import com.test.bookingsystem.requestform.MovieShowForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieShowService {
    @Autowired
    MovieShowRepository movieShowRepository;

    @Autowired
    ScreenRepository screenRepository;

    public Optional<MovieShow> create(MovieShowForm movieShowForm) {
        MovieShow movieShow = new MovieShow();
        movieShow.setMovieName(movieShowForm.getMovieName());
        movieShow.setShowPrices(movieShowForm.getShowPrices());
        movieShow.setStartTime(movieShowForm.getStartTime());
        movieShow.setEndTime(movieShowForm.getEndTime());

        Optional<Screen> screenOptional = screenRepository.findById(movieShowForm.getScreenId());
        if( screenOptional.isPresent() ) {
            movieShow.setScreen( screenOptional.get() );
            return Optional.of(movieShowRepository.save(movieShow));
        }
        return Optional.empty();

    }

    public List<MovieShow> getMovieShowsByMovieName(String movieName) {
        return movieShowRepository.findByMovieName(movieName);
    }

    public List<MovieShow> getMovieShowsByScreen(Long screenId) {
        return movieShowRepository.findByScreenId(screenId);
    }

    public boolean update(MovieShow movieShow) {
        Optional<MovieShow> movieShowOptional = movieShowRepository.findById(movieShow.getId());
        if(movieShowOptional.isPresent()) {
            movieShowRepository.save(movieShow);
            return true;
        }
        return false;
    }
}
