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
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productID;
    private String productName;
    private float calories;
    private float protein;
    private float fats;
    private float carbs;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryid")
    private Category category;
}