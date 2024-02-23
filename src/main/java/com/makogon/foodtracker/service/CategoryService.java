package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.Category;
import com.makogon.foodtracker.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findByCategoryID(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + id));
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

//        public Category updateCategory(Category updatedCategory) {
//            Category existingCategory = getCategoryById(updatedCategory.getCategoryId());
//            existingCategory.setName(updatedCategory.getName());
//            return categoryRepository.save(existingCategory);
//        }

    public void deleteCategoryById(long categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);

    }
}