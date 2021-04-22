package com.endava.garagesale.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

/**
 * This class creates a receipt that treats the status: PRODUCT_OUT_OF_STOCK,
 * i.e. the quantity of a product is 0
 */
@Entity(name = "ReceiptProductOutOfStock")
public class ReceiptProductOutOfStock extends Receipt {

    @ElementCollection(targetClass = String.class)
    private final List<String> purchasedProducts;

    public ReceiptProductOutOfStock(List<String> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
        status = Order.Status.PRODUCT_OUT_OF_STOCK;
    }

    public List<String> getPurchasedProducts() {
        return purchasedProducts;
    }

    @Override
    public String toString() {
        return "ReceiptProductOutOfStock{" +
                "status=" + status +
                ", purchasedProducts=" + purchasedProducts +
                '}';
    }
}
