package com.example.techshop4.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    private UUID id;

    private OffsetDateTime createdAt;
    private OffsetDateTime deliveredAt;

    public UUID getId() {
        return id;
    }

    public Order setId(UUID id) {
        this.id = id;
        return this;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public Order setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public OffsetDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public Order setDeliveredAt(OffsetDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
        return this;
    }
}

