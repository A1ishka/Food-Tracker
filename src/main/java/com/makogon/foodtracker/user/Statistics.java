package com.makogon.foodtracker.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long statisticsID;
    private String date;
    private int kalories;
    private float protein;
    private float fats;
    private float carbs;
    @OneToMany(mappedBy = "statistics")
    private List<ProductWeight> productWeights;
}