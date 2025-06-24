package com.example.productservice.service;

import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.SubCategoryResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.feigen.CategoryClient;
import com.example.productservice.feigen.SubcategoryClient;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryClient categoryClient;
    private final SubcategoryClient subcategoryClient;
    public ProductService(ProductRepository productRepository, CategoryClient categoryClient, SubcategoryClient subcategoryClient) {
        this.productRepository = productRepository;
        this.categoryClient = categoryClient;
        this.subcategoryClient = subcategoryClient;
    }
    public Product addProduct(Product product) {
        CategoryResponse category = categoryClient.getCategoryById(product.getCategoryId());
        SubCategoryResponse subCategory = subcategoryClient.getSubCategoryById(product.getSubCategoryId());

        if (subCategory.getCategoryId() == null || !subCategory.getCategoryId().equals(category.getId())) {
            throw new IllegalArgumentException("Alt kategori, seçilen kategoriye ait değil!");
        }

        return productRepository.save(product);
    }


    public List<Product> getMyProducts(String userEmail) {
        return productRepository.findByUserEmail(userEmail);
    }


    public Product getProductId(Long id) {
        return productRepository.findById(id).get();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
