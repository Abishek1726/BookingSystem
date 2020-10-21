package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.MovieShow;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface MovieShowRepository extends PagingAndSortingRepository<MovieShow, Long> {
    List<MovieShow> findByMovieName(String movieName);

    List<MovieShow> findByScreenId(Long screenId);
}
