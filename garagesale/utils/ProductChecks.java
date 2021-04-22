package com.endava.garagesale.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * this class deals with operations on products
 */
@Component
public class ProductChecks {
    private final ProductUtils productUtils;

    @Autowired
    public ProductChecks(ProductUtils productUtils) {
        this.productUtils = productUtils;
    }

    /**
     * @param id: Long
     * @return: true, if the product still exists on the stock
     */
    public boolean productExistsOnStock(Long id) {
        return productUtils.getProductById(id).getNrOfItems() > 0;
    }
}
