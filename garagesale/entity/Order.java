package com.endava.garagesale.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Represents an order
 */
@Entity(name = "Order")
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @OneToMany(targetEntity = Product.class, mappedBy = "order", cascade = {CascadeType.ALL})
    private List<Product> productList = new ArrayList<>();
    private Date createdDate;

    public enum Status {
        PENDING,
        PAYED,
        NONEXISTENT_USER,
        INVALID_CARD_NUMBER,
        CARD_DATE_EXPIRED,
        USER_ACCOUNT_BLOCKED,
        PRODUCT_OUT_OF_STOCK,
        NONEXISTENT_PRODUCT,
        PRODUCT_FROM_THE_SAME_CATEGORY
    }

    private Status status;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    private double totalAmount;

    public Order() {
    }

    public Order(List<Product> productList, Date createdDate, Status status, User user, double totalAmount) {
        this.productList = productList;
        this.createdDate = createdDate;
        this.status = status;
        this.user = user;
        this.totalAmount = totalAmount;
    }

    public void addProduct(Product product) {
        product.setOrder(this);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", productList=" + productList +
                ", createdDate=" + createdDate +
                ", status=" + status +
                ", user=" + user +
                ", totalAmount=" + totalAmount +
                '}';
    }

    public static class OrderBuilder {
        private Long id;
        private List<Product> productList;
        private Date createdDate;
        private Status status;
        private User user;
        private double totalAmount;

        public OrderBuilder() {
        }

        public static OrderBuilder anOrder() {
            return new OrderBuilder();
        }

        public OrderBuilder withTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public OrderBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrderBuilder withProductList(List<Product> productList) {
            this.productList = productList;
            return this;
        }

        public OrderBuilder withCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public OrderBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public OrderBuilder withUser(User user) {
            this.user = user;
            return this;
        }


        public Order build() {
            return new Order(productList, createdDate, status, user, totalAmount);
        }
    }
}
