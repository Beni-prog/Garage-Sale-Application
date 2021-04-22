package com.endava.garagesale.service;

import com.endava.garagesale.entity.Product;
import com.endava.garagesale.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Operations on assets in service
 */
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * @param product: Product
     * @return: asset repository with the new product added
     */
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }


    /**
     * @return: all the available products, i.e.
     * the products on the stock and whose category
     * isn't already in the customer's cart
     */
    @Override
    public List<Product> getAvailableProducts() {
        List<Product> products = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.getNrOfItems() > 0)
                products.add(product);
        }
        return products;
    }

    /**
     * @param id: Long
     * @return: a product by a given id
     */
    @Override
    public Product getProductById(Long id) {
        for (Product product : productRepository.findAll()) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
