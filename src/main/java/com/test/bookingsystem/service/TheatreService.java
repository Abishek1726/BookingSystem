package com.test.bookingsystem.service;

import com.test.bookingsystem.model.persistence.Theatre;
import com.test.bookingsystem.pagination.Pagination;
import com.test.bookingsystem.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    @Autowired
    private TheatreRepository theatreRepository;
    public List<Theatre> getTheatres(Pagination pagination) {
        Page<Theatre> page = theatreRepository.findAll(PageRequest.of(pagination.getPageNumber(), pagination.getPageSize()));
        return page.toList();
    }

    public void save(Theatre theatre) {
        theatreRepository.save(theatre);
    }

    //change this
    public boolean update(Theatre theatre) {
        Optional<Theatre> theatreOptional = getTheatre(theatre.getId());
        if(theatreOptional.isPresent()){
            theatreRepository.save(theatre);
            return true;
        }
        return false;
    }

    public void delete(Long id) {
        theatreRepository.deleteById(id);
    }

    public Optional<Theatre> getTheatre(Long id) {
        return theatreRepository.findById(id);
    }
}
