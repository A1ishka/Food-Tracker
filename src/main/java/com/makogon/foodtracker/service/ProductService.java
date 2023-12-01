package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.Category;
import com.makogon.foodtracker.model.Product;
import com.makogon.foodtracker.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
    }
    public void updateProduct(Long productID, Product updatedProduct) {
        Product existingProduct = getProductById(productID);
        if (updatedProduct.getCategory() != null) {
            //existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setCalories(updatedProduct.getCalories());
            existingProduct.setCarbs(updatedProduct.getCarbs());
            existingProduct.setProtein(updatedProduct.getProtein());
            existingProduct.setFats(updatedProduct.getFats());

            productRepository.save(existingProduct);
        }
    }
    public void deleteProductById(long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
}
