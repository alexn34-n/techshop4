package com.example.techshop4.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private BigDecimal price;

    @PrePersist
    private void init() {
        if(this.id == null) {
            this.id = UUID.randomUUID();
        }
    }


    public UUID getId() {
        return id;
    }

    public Product setId(UUID id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Product setCount(int count) {
        this.count = count;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void incrementCount() {
        this.count++;
    }

    public void decreaseCount() {
        this.count--;
    }
}
