package com.example.techshop4.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@TypeDef(name="jsonb",typeClass = JsonBinaryType.class)
public class Cart {
    @Id
    private UUID id;

    @ManyToOne
    private User user;

    @Type(type="jsonb")
    private Set<Product> products;

    @ManyToOne
    private Order order;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Cart setUser(User user) {
        this.user = user;
        return this;
    }

    public Set<Product> getProductList() {
        return products;
    }

    public void setProductList(Set<Product> productList) {
        this.products = productList;
    }

    public Cart addToProductList(Set<Product> product){
        if(this.products==null){
            this.products=new HashSet<>();
        }
        this.products.addAll(product);

        return this;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
