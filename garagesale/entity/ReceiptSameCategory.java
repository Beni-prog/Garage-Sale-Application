package com.endava.garagesale.entity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

/**
 * This class creates a receipt that treats the status IS_FROM_THE_SAME_CATEGORY,
 * i.e. the user want at least 2 products within the same category
 */
@Entity(name = "ReceiptSameCategory")
public class ReceiptSameCategory extends Receipt {

    @ElementCollection(targetClass = String.class)
    private final List<String> purchasedProducts;

    public ReceiptSameCategory(List<String> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
        this.status = Order.Status.PRODUCT_FROM_THE_SAME_CATEGORY;
    }

    public List<String> getPurchasedProducts() {
        return purchasedProducts;
    }

    @Override
    public String toString() {
        return "ReceiptSameCategory{" +
                "status=" + status +
                ", purchasedProducts=" + purchasedProducts +
                '}';
    }
}
