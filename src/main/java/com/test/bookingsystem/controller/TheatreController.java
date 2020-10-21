package com.test.bookingsystem.controller;

import com.test.bookingsystem.model.persistence.Theatre;
import com.test.bookingsystem.pagination.Pagination;
import com.test.bookingsystem.service.TheatreService;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.test.bookingsystem.util.ResourcePath.V1_THEATRES_PATH;

@RestController
@RequestMapping(ResourcePath.V1_THEATRES_PATH)
public class TheatreController {
    @Autowired
    private TheatreService theatreService;

    @GetMapping("/{id}")
    public ResponseEntity<Theatre> getTheatre(@PathVariable Long id) {
        Optional<Theatre> theatreResponse = theatreService.getTheatre(id);
        if (theatreResponse.isPresent()) {
            return new ResponseEntity<>(theatreResponse.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public List<Theatre> getTheatres(@RequestParam("pageNum") Optional<Integer> pageNumber,
                                     @RequestParam("pageSize") Optional<Integer> pageSize) {
        return theatreService.getTheatres( new Pagination(pageNumber, pageSize) );
    }

    @PostMapping
    public ResponseEntity<Theatre> createNew(@RequestBody Theatre theatre) {
        theatreService.save(theatre);
        return new ResponseEntity<>(theatre,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody Theatre theatre, @PathVariable Long id) {
        theatre.setId(id);
        boolean isUpdateSuccess = theatreService.update(theatre);
        HttpStatus status = isUpdateSuccess ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(status);
    }

    @DeleteMapping("/{id}")
    public void deleteTheatre(@PathVariable Long id) {
        theatreService.delete(id);
    }

}
