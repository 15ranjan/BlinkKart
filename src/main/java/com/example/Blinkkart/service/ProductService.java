package com.example.Blinkkart.service;

import com.example.Blinkkart.model.Product;

import java.util.List;

public interface ProductService {

    // Get a list of all products
    List<Product> getAllProducts();

    // Get a list of products by category
    List<Product> getProductsByCategory(Long categoryId);

    // Get product details by ID
    Product getProductById(Long id);

    // Add a new product
    Product addProduct(Product product);

    // Update an existing product
    Product updateProduct(Long id, Product product);

    // Delete a product by ID
    void deleteProduct(Long id);
}
