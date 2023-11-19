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
    @JoinColumn(name = "product_id")
    private Product product;
    private float weight;
    @ManyToOne
    @JoinColumn(name = "statistics_id")
    private Statistics statistics;
}