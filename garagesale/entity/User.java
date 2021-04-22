package com.endava.garagesale.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a user
 */
@Entity(name = "User")
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;

    public enum AccountStatus {
        ACTIVE,
        BLOCKED
    }

    private AccountStatus accountStatus;
    @OneToOne(targetEntity = ReceiptInvalidUserAccount.class)
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    public User() {
    }

    public User(String name, String email, AccountStatus accountStatus) {
        this.name = name;
        this.email = email;
        this.accountStatus = accountStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id){ this.id=id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && accountStatus == user.accountStatus && Objects.equals(receipt, user.receipt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, accountStatus, receipt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", accountStatus=" + accountStatus +
                '}';
    }

}
