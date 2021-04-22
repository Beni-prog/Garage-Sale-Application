package com.endava.garagesale.entity;

import com.endava.garagesale.request.OrderRequest;

import javax.persistence.*;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Represents a card
 */
@Entity(name = "Card")
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private String number;
    private YearMonth expDate;
    private String cvv;

    @OneToOne(targetEntity = OrderRequest.class)
    @JoinColumn(name = "orderRequest_id")
    private OrderRequest orderRequest;

    public Card() {
    }

    public Card(String name, String number, YearMonth expDate, String cvv) {
        this.name = name;
        this.number = number;
        this.expDate = expDate;
        this.cvv = cvv;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public YearMonth getExpDate() {
        return expDate;
    }

    public void setExpDate(YearMonth expDate) {
        this.expDate = expDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(id, card.id) && Objects.equals(name, card.name) && Objects.equals(expDate, card.expDate) && Objects.equals(cvv, card.cvv);
    }
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number'" + number + '\'' +
                ", expDate='" + expDate + '\'' +
                ", cvv='" + cvv + '\'' +
                '}';
    }

}
