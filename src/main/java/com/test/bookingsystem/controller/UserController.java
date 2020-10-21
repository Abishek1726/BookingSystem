package com.test.bookingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bookingsystem.model.persistence.User;
import com.test.bookingsystem.service.JWTService;
import com.test.bookingsystem.service.UserService;
import com.test.bookingsystem.util.ResourcePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(ResourcePath.USERS_PATH)
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody User user) {
        Optional<String> tokenOptional = userService.authenticateUser(user);
        if(tokenOptional.isPresent()) {
          return new ResponseEntity<>(tokenOptional.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
