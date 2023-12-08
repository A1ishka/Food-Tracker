package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.Person;
import com.makogon.foodtracker.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    void delete(Statistics statistics);
    //Statistics findByPersonAndDate(Person person, LocalDate date);
    @Query("SELECT s FROM Statistics s WHERE s.person = :person AND s.date = :date")
    Statistics findByPersonAndDate(Person person, LocalDate date);
}
