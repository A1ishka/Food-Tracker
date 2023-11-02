package by.bsuir.makogon.foodtracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BasePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int BasePlanID;
    private int Calories;
    private float Protein;
    private float Fats;
    private float Carbs;
}
