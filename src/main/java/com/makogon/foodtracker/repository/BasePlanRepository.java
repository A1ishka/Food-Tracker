package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.BasePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasePlanRepository extends JpaRepository<BasePlan, Long> {
}
