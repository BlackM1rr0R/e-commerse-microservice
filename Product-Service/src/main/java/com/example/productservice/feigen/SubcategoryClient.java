package com.example.productservice.feigen;

import com.example.productservice.dto.SubCategoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "SUBCATEGORY-SERVICE")
public interface SubcategoryClient {
    @GetMapping("/subcategory/{id}")
    SubCategoryResponse getSubCategoryById(@PathVariable Long id);
}
