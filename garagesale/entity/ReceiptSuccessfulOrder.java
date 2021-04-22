package com.endava.garagesale.entity;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.List;

/**
 * This class creates a receipt that treats the successful order status,
 * i.e. all the validations had successfully passed
 */
@Entity(name = "ReceiptSuccessfulOrder")
public class ReceiptSuccessfulOrder extends Receipt {

    @OneToOne(targetEntity = User.class, mappedBy = "receipt", cascade = {CascadeType.ALL})
    private final User user;
    private final String cardNumber;
    @ElementCollection(targetClass = String.class)
    private final List<String> purchasedProducts;
    private final double sum;

    public ReceiptSuccessfulOrder(User user, String cardNumber, List<String> purchasedProducts, double sum, Order.Status orderStatus) {
        this.user = user;
        this.cardNumber = cardNumber;
        this.purchasedProducts = purchasedProducts;
        this.sum = sum;
        this.status = orderStatus;
    }

    public User getUser() {
        return user;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public List<String> getPurchasedProducts() {
        return purchasedProducts;
    }

    public double getSum() {
        return sum;
    }

    @Override
    public String toString() {
        return "ReceiptSuccessfulOrder{" +
                "status=" + status +
                ", user=" + user +
                ", cardNumber='" + cardNumber + '\'' +
                ", purchasedProducts=" + purchasedProducts +
                ", sum=" + sum +
                '}';
    }
}
