package com.example.productservice.controller;

import com.example.productservice.entity.Product;
import com.example.productservice.jwtservice.JwtService;
import com.example.productservice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final JwtService jwtService;

    public ProductController(ProductService productService, JwtService jwtService) {
        this.productService = productService;
        this.jwtService = jwtService;
    }
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product, HttpServletRequest request) {
        String token=request.getHeader("Authorization").substring(7);
        String userEmail=jwtService.extractUsername(token);
        product.setUserEmail(userEmail);
        return productService.addProduct(product);
    }
    @GetMapping("/my-products")
    public List<Product> getMyProducts(HttpServletRequest request) {
        String token=request.getHeader("Authorization").substring(7);
        String userEmail=jwtService.extractUsername(token);
        return productService.getMyProducts(userEmail);
    }
}
