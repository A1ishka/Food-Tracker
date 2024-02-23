package com.makogon.foodtracker.repository;

import java.util.Optional;

import com.makogon.foodtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByLogin(String login);
    Optional<User> findByUserID(long userid);

}
