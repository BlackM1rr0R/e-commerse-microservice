package com.example.productservice.repository;

import com.example.productservice.documents.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;

public interface ProductElasticRepository extends ElasticsearchRepository<ProductDocument, String> {
    List<ProductDocument> findByNameContainingIgnoreCase(String name);
}
