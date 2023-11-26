package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.ProductWeight;
import com.makogon.foodtracker.repository.ProductWeightRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductWeightService {

    private final ProductWeightRepository productWeightRepository;

    public ProductWeightService(ProductWeightRepository productWeightRepository) {
        this.productWeightRepository = productWeightRepository;
    }

    public ProductWeight getProductWeightById(long productWeightId) {
        return productWeightRepository.findById(productWeightId)
                .orElseThrow(() -> new IllegalArgumentException("Вес товара с идентификатором " + productWeightId + " не найден"));
    }

    public ProductWeight saveProductWeight(ProductWeight productWeight) {
        return productWeightRepository.save(productWeight);
    }

    public ProductWeight updateProductWeight(ProductWeight updatedProductWeight) {
        ProductWeight existingProductWeight = getProductWeightById(updatedProductWeight.getProductWeightID());

        existingProductWeight.setAddTime(updatedProductWeight.getAddTime());
        existingProductWeight.setProduct(updatedProductWeight.getProduct());
        existingProductWeight.setWeight(updatedProductWeight.getWeight());
        existingProductWeight.setStatistics(updatedProductWeight.getStatistics());

        return productWeightRepository.save(existingProductWeight);
    }

    public void deleteProductWeightById(long productWeightId) {
        ProductWeight productWeight = getProductWeightById(productWeightId);
        productWeightRepository.delete(productWeight);
    }
}