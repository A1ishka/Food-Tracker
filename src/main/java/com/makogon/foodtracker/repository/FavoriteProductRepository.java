package com.makogon.foodtracker.repository;

import com.makogon.foodtracker.model.FavoriteProduct;
import com.makogon.foodtracker.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteProductRepository extends JpaRepository<FavoriteProduct, Long> {
    //FavoriteProduct findFavoriteProductsByFavorite_productID(Long Favorite_productID);
    List<FavoriteProduct> findAllByPerson(Person person);

    FavoriteProduct getFavoriteProductByfavoriteproductID(long favoriteProductID);
}
