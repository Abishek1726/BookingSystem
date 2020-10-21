package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.OpenShow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShowRepository extends CrudRepository<OpenShow,Long> {
    List<OpenShow> findByMovieShowIdAndShowDateBetween(Long movieShowId, Date startDate, Date endDate);
}
