package com.makogon.foodtracker.model;

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
    private float calories;
    private float protein;
    private float fats;
    private float carbs;
    @ManyToOne
    @JoinColumn(name = "personid")
    private Person person;
}