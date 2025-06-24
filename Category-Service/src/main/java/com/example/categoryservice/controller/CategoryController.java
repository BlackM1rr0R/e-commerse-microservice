package com.example.categoryservice.controller;

import com.example.categoryservice.dto.CategoryRequest;
import com.example.categoryservice.entity.Category;
import com.example.categoryservice.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @GetMapping("all_category")
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }
    @PostMapping("/add")
    public Category addCategory(@RequestBody CategoryRequest request){
        return categoryService.addCategory(request);
    }
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }

}
