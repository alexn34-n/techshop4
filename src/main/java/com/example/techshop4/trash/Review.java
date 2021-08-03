package com.example.techshop4.trash;

import com.example.techshop4.entity.Product;
import com.example.techshop4.entity.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Review {
    @Id
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Product product;

    private Boolean isModerated;

    private String text;

    public UUID getId() {
        return id;
    }

    public Review setId(UUID id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Review setUser(User user) {
        this.user = user;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Review setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Boolean getModerated() {
        return isModerated;
    }

    public Review setModerated(Boolean moderated) {
        isModerated = moderated;
        return this;
    }

    public String getText() {
        return text;
    }

    public Review setText(String text) {
        this.text = text;
        return this;
    }
}
