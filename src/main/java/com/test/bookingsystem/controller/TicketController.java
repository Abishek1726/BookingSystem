package com.test.bookingsystem.controller;

import com.test.bookingsystem.model.persistence.Ticket;
import com.test.bookingsystem.requestform.TicketForm;
import com.test.bookingsystem.service.TicketService;
import com.test.bookingsystem.util.Pair;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(ResourcePath.V1_TICKETS_PATH)
public class TicketController {
    @Autowired
    TicketService ticketService;

    @PostMapping
    public ResponseEntity<? extends Object> bookTicket(@RequestBody TicketForm ticketForm) {
        Pair<String, Optional<Ticket>> status = ticketService.createTicket(ticketForm);
        if(status.getFirst().equals("BOOKED") && status.getSecond().isPresent()) {
            return new ResponseEntity<>(status.getSecond().get(), HttpStatus.CREATED);
        } else if(status.getFirst().equals("ERROR")) {
            return new ResponseEntity<>(status.getFirst(),HttpStatus.INTERNAL_SERVER_ERROR);
        } else if(status.getFirst().equals("INVALID_SHOW_ID")) {
            return new ResponseEntity<>(status.getFirst(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(status.getFirst(), HttpStatus.OK);
    }

    @PutMapping("/{id}/payment-success")
    public void paymentSuccess(@PathVariable Long id) {
        ticketService.paymentSuccess(id);
    }

    @PutMapping("/{id}/payment-failure")
    public void paymentFailure(@PathVariable Long id) {
        ticketService.paymentFailure(id);
    }

    @GetMapping("/{id}")
    public Ticket getTicket(@PathVariable Long id) {
        return ticketService.getTicket(id);
    }
}
