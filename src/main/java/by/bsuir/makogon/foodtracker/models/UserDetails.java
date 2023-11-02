package by.bsuir.makogon.foodtracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserID;
    private float Height;
    private float Weight;
    private int Age;
    private int Activity;
    private String Sex;
}
