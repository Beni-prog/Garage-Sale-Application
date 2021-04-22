package com.endava.garagesale.request;

import com.endava.garagesale.entity.Card;
import com.endava.garagesale.entity.Product;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class OrderRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToMany(targetEntity = Product.class, mappedBy = "orderRequest", cascade = {CascadeType.ALL})
    private List<Long> productsIds;
    private Long userId;
    @OneToOne(targetEntity = Card.class, mappedBy = "orderRequest", cascade = CascadeType.ALL)
    private Card card;

    public OrderRequest() {
    }

    public OrderRequest(List<Long> productsIds, Long userId, Card card) {
        this.productsIds = productsIds;
        this.userId = userId;
        this.card = card;
    }

    public List<Long> getProductsIds() {
        return productsIds;
    }

    public void setProductsIds(List<Long> productsIds) {
        this.productsIds = productsIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
}
