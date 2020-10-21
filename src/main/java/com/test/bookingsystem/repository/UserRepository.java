package com.test.bookingsystem.repository;

import com.test.bookingsystem.model.persistence.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    Optional<User> findUserByNameAndPassword(String userName, String passwordHash);
    boolean existsByName(String name);

}
