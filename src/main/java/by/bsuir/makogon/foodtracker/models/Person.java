package by.bsuir.makogon.foodtracker.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int PersonID;
    private String FirstName;
    private String LastName;
    private String PlanName;
    private int BasePlan;
    private int UserID;
}
