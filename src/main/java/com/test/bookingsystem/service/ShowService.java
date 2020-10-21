package com.test.bookingsystem.service;

import com.test.bookingsystem.model.ScreenSeatLayout;
import com.test.bookingsystem.model.ShowSeatsInfo;
import com.test.bookingsystem.model.persistence.MovieShow;
import com.test.bookingsystem.model.persistence.OpenShow;
import com.test.bookingsystem.repository.MovieShowRepository;
import com.test.bookingsystem.repository.ShowRepository;
import com.test.bookingsystem.requestform.BulkCreateShowsForm;
import com.test.bookingsystem.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShowService{
    @Autowired
    ShowRepository showRepository;

    @Autowired
    MovieShowRepository movieShowRepository;

    public Optional<List<OpenShow>> createShows(BulkCreateShowsForm form) {
        try {
            Optional<MovieShow> movieShowOptional = movieShowRepository.findById(form.getMovieShowId());
            if (!movieShowOptional.isPresent()) {
                return Optional.empty();
            }
            List<OpenShow> openShows = new ArrayList<>();
            MovieShow movieShow = movieShowOptional.get();
            ScreenSeatLayout screenSeatLayout = movieShow.getScreen().getSeatLayout();

            ShowSeatsInfo showSeatsInfo = ShowSeatsInfo.constructShowSeatLayoutInfo(screenSeatLayout, movieShow);
            Integer days = DateUtil.dateDiff(form.getFromDate(), form.getToDate());

            for (Integer i = 0; i < days; ++i) {
                OpenShow show = new OpenShow();
                Date currentDate = DateUtil.incrementDateBy(form.getFromDate(), i);
                show.setShowDate(currentDate);
                show.setShowSeatsInfo(showSeatsInfo);
                show.setMovieShow(movieShow);
                openShows.add(show);
            }
            showRepository.saveAll(openShows);
            return Optional.of(openShows);
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }

    public List<OpenShow> getShows(Date fromDate, Date toDate, Long movieShowId) {
        return showRepository.findByMovieShowIdAndShowDateBetween(movieShowId,fromDate, toDate);
    }
}
