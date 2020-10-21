package com.test.bookingsystem.service;

import com.test.bookingsystem.model.persistence.User;
import com.test.bookingsystem.repository.UserRepository;
import com.test.bookingsystem.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    JWTService jwtService;

    private ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public Optional<String> authenticateUser(User user) {
        Optional<User> userOptional = userRepository.findUserByNameAndPassword(user.getName(), user.getPassword());
        if(userOptional.isPresent()) {
            user = userOptional.get();
            Long expiryTime = user.isAdmin() ? ( DateUtil._1_MINUTES_IN_MILLIS * 15 ) : DateUtil._1_HOUR_IN_MILLIS;
            return Optional.of(jwtService.generateToken(user.getId().toString(),expiryTime ));
        }
        return Optional.empty();
    }

    public User createUser(User user) {
        if( user.getRole() != null && user.isAdmin() ) {
            throw new RuntimeException("Admin users cannot be created.");
        }
        if ( userRepository.existsByName(user.getName()) ) {
            throw new RuntimeException("User name already taken");
        }

        //should hash password
        if (user.getPassword().isEmpty()) {
            throw new RuntimeException("Password not set");
        }

        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    public void setCurrentUser(User user) {
        userThreadLocal.set(user);
    }

    public void resetCurrentUser() {
        userThreadLocal.remove();
    }

    public User getCurrentUser() {
        return userThreadLocal.get();
    }


    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }
}
