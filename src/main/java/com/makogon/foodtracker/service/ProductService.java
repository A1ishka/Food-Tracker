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
    public Product updateProduct(Product updatedProduct) {
        Product existingProduct = getProductById(updatedProduct.getProductID());

//        float fats = 0.1F;
//        existingProduct.setFats(updatedProduct.setFats(fats));

        return productRepository.save(existingProduct);
    }
    public void deleteProductById(long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
}
