package com.example.categoryservice.service;

import com.example.categoryservice.dto.CategoryRequest;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    public Category addCategory(CategoryRequest request) {
     Category category = new Category();
     category.setCategoryname(request.getName());
     return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }
}
