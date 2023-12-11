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
@Table(name = "favorite_product")
public class FavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long favoriteproductID;
    @ManyToOne
    @JoinColumn(name = "productid")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "personid")
    private Person person;
}
