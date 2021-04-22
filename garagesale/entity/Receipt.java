package com.endava.garagesale.entity;

import javax.persistence.*;

/**
 * This class represents a receipt and contains an order status
 */
@Entity(name = "Receipt")
@Table(name = "receipts")
public abstract class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Order.Status status;

    public Receipt() {
    }

    public Order.Status getStatus() {
        return status;
    }
}
