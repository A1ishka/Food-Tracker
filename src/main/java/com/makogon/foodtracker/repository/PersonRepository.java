package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByFirstName(String firstName);

    Optional<Object> findByUser(User user);
}