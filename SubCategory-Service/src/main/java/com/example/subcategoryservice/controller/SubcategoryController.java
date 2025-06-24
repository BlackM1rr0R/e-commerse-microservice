package com.example.subcategoryservice.controller;

import com.example.subcategoryservice.entity.Subcategory;
import com.example.subcategoryservice.service.SubcategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {
    private SubcategoryService subcategoryService;
    public SubcategoryController(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }
    @PostMapping("/add")
    public Subcategory add(@RequestBody Subcategory subcategory) {
        return subcategoryService.add(subcategory);
    }
    @GetMapping("/{id}")
    public Subcategory getById(@PathVariable Long id) {
        return subcategoryService.getById(id); // bunu serviste yazman gerekir
    }
}
