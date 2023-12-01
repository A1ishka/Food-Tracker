package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findByUserDetailsid(long userdetailsid);
    Optional<UserDetails> findByPerson(Person peson);
}
