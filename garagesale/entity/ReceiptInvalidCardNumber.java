package com.endava.garagesale.entity;

import javax.persistence.Entity;

/**
 * This class creates a receipt that treats the invalid card number status
 */
@Entity(name = "ReceiptInvalidCardNumber")
public class ReceiptInvalidCardNumber extends Receipt {

    private final String cardNumber;

    public ReceiptInvalidCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        this.status = Order.Status.INVALID_CARD_NUMBER;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "ReceiptInvalidCardNumber{" +
                "status=" + status +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
