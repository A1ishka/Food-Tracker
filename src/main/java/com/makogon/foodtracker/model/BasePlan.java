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
@Table(name = "base_plan")
public class BasePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long base_planid;
    private float calories;
    private float protein;
    private float fats;
    private float carbs;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "planid")
    private Plan plan;
}