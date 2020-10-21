package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.Screen;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface ScreenRepository extends PagingAndSortingRepository<Screen, Long> {
    Optional<List<Screen>> findByTheatreId(Long theatreId);
}
