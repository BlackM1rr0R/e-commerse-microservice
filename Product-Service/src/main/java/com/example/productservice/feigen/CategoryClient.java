package com.example.productservice.feigen;

import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.SubCategoryResponse;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryClient {
    @GetMapping("/category/{id}")
    CategoryResponse getCategoryById(@PathVariable Long id);
}
