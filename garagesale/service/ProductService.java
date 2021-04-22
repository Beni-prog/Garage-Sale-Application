package com.endava.garagesale.service;


import com.endava.garagesale.entity.Product;

import java.util.List;

/**
 * Represents the  service interface for Asset
 */
public interface ProductService {
    List<Product> getAvailableProducts();

    Product createProduct(Product product);

    Product getProductById(Long id);
}
