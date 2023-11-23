package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.Activity;
import com.makogon.foodtracker.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByactivityName(String activityName);
}