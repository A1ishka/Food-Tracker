package com.makogon.foodtracker.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_details")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userDetailsID;
    @OneToOne
    @MapsId
    private Person person;
    private float height;
    private float weight;
    private int age;
    @ManyToOne
    @JoinColumn(name = "activityid")
    private Activity activity;
    private String sex;
}
