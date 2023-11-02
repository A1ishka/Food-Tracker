package by.bsuir.makogon.foodtracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ProductWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ProductID;
    private float Weight;
    private int StatisticsID;
    private int PersonID;
}
