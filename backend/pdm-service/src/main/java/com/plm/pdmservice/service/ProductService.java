package com.plm.pdmservice.service;

import com.plm.pdmservice.entity.Product;
import com.plm.pdmservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    public Product createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        
        // Publish event to Kafka
        kafkaTemplate.send("product-events", "CREATE", savedProduct.getId().toString());
        
        return savedProduct;
    }
    
    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setVersion(productDetails.getVersion());
            product.setStatus(productDetails.getStatus());
            product.setOwnerId(productDetails.getOwnerId());
            
            Product updatedProduct = productRepository.save(product);
            
            // Publish event to Kafka
            kafkaTemplate.send("product-events", "UPDATE", updatedProduct.getId().toString());
            
            return updatedProduct;
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
    
    public void deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            
            // Publish event to Kafka
            kafkaTemplate.send("product-events", "DELETE", id.toString());
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
    
    public List<Product> getProductsByStatus(String status) {
        try {
            Product.ProductStatus productStatus = Product.ProductStatus.valueOf(status.toUpperCase());
            return productRepository.findByStatus(productStatus);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }
    }
    
    public List<Product> getProductsByOwner(Long ownerId) {
        return productRepository.findByOwnerId(ownerId);
    }
    
    public List<Product> searchProductsByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}