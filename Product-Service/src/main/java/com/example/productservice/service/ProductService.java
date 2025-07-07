package com.example.productservice.service;

import com.example.productservice.documents.ProductDocument;
import com.example.productservice.dto.CategoryResponse;
import com.example.productservice.dto.SubCategoryResponse;
import com.example.productservice.entity.Product;
import com.example.productservice.feigen.CategoryClient;
import com.example.productservice.feigen.SubcategoryClient;

import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.ProductElasticRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductElasticRepository productElasticRepository;
    private final CategoryClient categoryClient;
    private final SubcategoryClient subcategoryClient;

    public ProductService(ProductRepository productRepository,
                          ProductElasticRepository productElasticRepository,
                          CategoryClient categoryClient,
                          SubcategoryClient subcategoryClient) {
        this.productRepository = productRepository;
        this.productElasticRepository = productElasticRepository;
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
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProductWithImage(String name, Double price, String description,
                                       Long categoryId, Long subCategoryId,
                                       MultipartFile image, String userEmail) throws IOException {

        // Kategori - alt kategori kontrolü
        CategoryResponse category = categoryClient.getCategoryById(categoryId);
        SubCategoryResponse subCategory = subcategoryClient.getSubCategoryById(subCategoryId);

        if (subCategory.getCategoryId() == null || !subCategory.getCategoryId().equals(category.getId())) {
            throw new IllegalArgumentException("Alt kategori, seçilen kategoriye ait değil!");
        }

        // Resmi kaydet
        String uploadDir = "src/main/resources/static/uploads/";
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, image.getBytes());

        // Product nesnesi oluştur
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setCategoryId(categoryId);
        product.setSubCategoryId(subCategoryId);
        product.setUserEmail(userEmail);
        product.setImageUrl("/uploads/" + fileName);

        // MySQL'e kaydet
        Product savedProduct = productRepository.save(product);

        ProductDocument document = new ProductDocument();
        document.setId(savedProduct.getId().toString());
        document.setName(savedProduct.getName());
        document.setDescription(savedProduct.getDescription());
        document.setPrice(savedProduct.getPrice());
        document.setUserEmail(savedProduct.getUserEmail());
        document.setCategoryId(savedProduct.getCategoryId());
        document.setSubCategoryId(savedProduct.getSubCategoryId());
        document.setImageUrl(savedProduct.getImageUrl());

        productElasticRepository.save(document);

        return savedProduct;
    }
}
