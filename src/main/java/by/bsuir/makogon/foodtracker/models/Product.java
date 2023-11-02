package by.bsuir.makogon.foodtracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ProductID;
    private String ProductName;
    private int Calories;
    private float Protein;
    private float Fats;
    private float Carbs;
    private int CategoryID;
}
