package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.ProductWeight;
import com.makogon.foodtracker.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductWeightRepository extends JpaRepository<ProductWeight, Long> {
    void delete(ProductWeight productWeight);
}