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
@Table(name = "product_weight")
public class ProductWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productWeightID;
    private String addTime;
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;
    private float weight;
    @ManyToOne
    @JoinColumn(name = "statisticsid")
    private Statistics statistics;
}