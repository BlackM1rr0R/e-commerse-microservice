package com.example.subcategoryservice.service;

import com.example.subcategoryservice.entity.Subcategory;
import com.example.subcategoryservice.repository.SubcategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryService {
    private SubcategoryRepository subcategoryRepository;
    public SubcategoryService(SubcategoryRepository subcategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
    }

    public Subcategory add(Subcategory subcategory) {
        return subcategoryRepository.save(subcategory);
    }

    public Subcategory getById(Long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subcategory not found"));
    }
}
