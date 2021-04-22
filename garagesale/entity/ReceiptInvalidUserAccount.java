package com.endava.garagesale.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * This class creates a receipt that treats the invalid user account status,
 * i.e. the account is BLOCKED
 */
@Entity(name = "ReceiptInvalidUserAccount")
public class ReceiptInvalidUserAccount extends Receipt {

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private final User user;

    public ReceiptInvalidUserAccount(User user) {
        this.user = user;
        this.status = Order.Status.USER_ACCOUNT_BLOCKED;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ReceiptInvalidUserAccount{" +
                "status=" + status +
                ", user=" + user +
                '}';
    }
}
