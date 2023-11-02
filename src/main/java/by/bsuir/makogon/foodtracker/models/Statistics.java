package by.bsuir.makogon.foodtracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int StatisticsID;
    private String Date;
    private int Calories;
    private float Protein;
    private float Fats;
    private float Carbs;
    private int PersonID;
}
