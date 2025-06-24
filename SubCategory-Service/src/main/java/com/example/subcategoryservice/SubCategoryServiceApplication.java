package com.example.subcategoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SubCategoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubCategoryServiceApplication.class, args);
    }

}
