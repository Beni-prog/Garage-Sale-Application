package com.endava.garagesale.entity;

import javax.persistence.Entity;

@Entity(name = "ReceiptNonExistentUser")
public class ReceiptNonExistentUser extends Receipt{

    public ReceiptNonExistentUser(){
        this.status= Order.Status.NONEXISTENT_USER;
    }

    @Override
    public String toString() {
        return "ReceiptNonExistentUser{" +
                "status=" + status +
                '}';
    }
}
