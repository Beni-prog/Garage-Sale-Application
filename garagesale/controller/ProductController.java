package com.endava.garagesale.controller;

import com.endava.garagesale.entity.Product;
import com.endava.garagesale.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Represents the asset controller
 */
@RestController
public class ProductController {
    private final ProductService productService;

    private final Logger log = LoggerFactory.getLogger(ProductController.class);


    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * @return: all the products
     */
    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Request received for getting all the products");

        return ResponseEntity.ok(productService.getAvailableProducts());
    }

    /**
     * @param product: Product
     * @return: a new product
     */
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        log.info("\nRequest received for adding a product {}", product);

        return ResponseEntity.ok(productService.createProduct(product));
    }

    /**
     * @param id: Long
     * @return: a product
     */
    @GetMapping("/get-product-by-id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Request received for getting a product by id {}", id);

        return ResponseEntity.ok(productService.getProductById(id));
    }
}
