package com.endava.garagesale.entity;

import com.endava.garagesale.request.OrderRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a product
 */
@Entity(name = "Product")
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long id;
    private String code;

    private String name;
    private double price;
    @ElementCollection(targetClass = String.class)
    private List<String> issues;
    private int nrOfItems;

    @ManyToOne(targetEntity = Receipt.class)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(targetEntity = OrderRequest.class)
    @JoinColumn(name = "orderRequest_id")
    private OrderRequest orderRequest;

    public enum Category {
        LAPTOPS,//0
        MONITORS,//1
        TVs,//2
        PROJECTORS,//3
        PHONES,//4
        TABLETS//5
    }

    Category category;

    public Product() {

    }

    public Product(Long id, String code, String name, double price, List<String> issues, int nrOfItems, Category category) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.issues = issues;
        this.nrOfItems = nrOfItems;
        this.category = category;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getIssues() {
        return issues;
    }

    public int getNrOfItems() {
        return nrOfItems;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIssues(ArrayList<String> issues) {
        this.issues = issues;
    }

    public void setNrOfItems(int nrOfItems) {
        this.nrOfItems = nrOfItems;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 && nrOfItems == product.nrOfItems && Objects.equals(code, product.code) && Objects.equals(name, product.name) && Objects.equals(issues, product.issues) && Objects.equals(category, product.category);
    }

    @Override
    public String toString() {
        return "Code: " + code +
                ", Id: " + id +
                ", Name: " + name +
                ", Price: " + price +
                ", Issues: " + issues.toString() +
                ", Items available: " + nrOfItems +
                ", Category: " + category + '\n';
    }

}