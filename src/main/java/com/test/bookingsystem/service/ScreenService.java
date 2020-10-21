package com.test.bookingsystem.service;

import com.test.bookingsystem.model.persistence.Screen;
import com.test.bookingsystem.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenService {
    @Autowired
    ScreenRepository screenRepository;

    public Optional<List<Screen>> getScreens(Long theatreId) {
        return screenRepository.findByTheatreId(theatreId);
    }

    public Screen create(Screen screen) {
        return screenRepository.save(screen);
    }

    public Optional<Screen> getScreen(Long id) {
        return screenRepository.findById(id);
    }

    public boolean update(Screen screen) {
        Optional<Screen> optionalScreen = screenRepository.findById(screen.getId());
        if(optionalScreen.isPresent()) {
            screenRepository.save(screen);
            return true;
        } else {
            return false;
        }
    }
}
