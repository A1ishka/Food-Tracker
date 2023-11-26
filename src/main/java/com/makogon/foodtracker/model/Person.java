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
@Table (name="person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long personID;
    private String firstName;
    private String lastName;
    private String planName;
    private long basePlanID;
    @OneToOne(mappedBy = "person")
    private User user;
    @OneToOne
    @JoinColumn(name = "base_planid")
    private BasePlan basePlan;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_detailsid")
    private UserDetails userDetails;
    @OneToMany(mappedBy = "person")
    private List<Statistics> statisticsList;
}
