package com.example.productservice.controller;

import com.example.productservice.documents.ProductDocument;
import com.example.productservice.entity.Product;
import com.example.productservice.jwtservice.JwtService;
import com.example.productservice.repository.ProductElasticRepository;
import com.example.productservice.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final JwtService jwtService;
    private final ProductElasticRepository productElasticRepository;
    public ProductController(ProductService productService, JwtService jwtService,ProductElasticRepository productElasticRepository) {
        this.productService = productService;
        this.jwtService = jwtService;
        this.productElasticRepository = productElasticRepository;
    }
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Product addProduct(
            @RequestParam("name") String name,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("subCategoryId") Long subCategoryId,
            @RequestParam("image") MultipartFile image,
            HttpServletRequest request
    ) throws IOException {

        String token = request.getHeader("Authorization").substring(7);
        String userEmail = jwtService.extractUsername(token);

        return productService.addProductWithImage(name, price, description, categoryId, subCategoryId, image, userEmail);
    }

    @GetMapping("/my-products")
    public List<Product> getMyProducts(HttpServletRequest request) {
        String token=request.getHeader("Authorization").substring(7);
        String userEmail=jwtService.extractUsername(token);
        return productService.getMyProducts(userEmail);
    }
    @GetMapping("/{id}")
    public Product getProductId(@PathVariable Long id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String userEmail = jwtService.extractUsername(token);
        Product product = productService.getProductId(id);
        if (!product.getUserEmail().equals(userEmail)) {
            throw new RuntimeException("You are not authorized to view this product.");
        }
        return product;
    }
    @GetMapping("/all-products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/search")
    public List<ProductDocument> searchProduct(@RequestParam("keyword") String keyword){
        return productElasticRepository.findByNameContainingIgnoreCase(keyword);
    }
}
