package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.Theatre;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TheatreRepository extends PagingAndSortingRepository<Theatre, Long> {
}
