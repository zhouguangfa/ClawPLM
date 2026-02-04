package com.plm.pdmservice.repository;

import com.plm.pdmservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByStatus(Product.ProductStatus status);
    
    List<Product> findByOwnerId(Long ownerId);
    
    List<Product> findByNameContainingIgnoreCase(String name);
}