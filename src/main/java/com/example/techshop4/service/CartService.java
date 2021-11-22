package com.example.techshop4.service;

import com.example.techshop4.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CartService {
    private List<Product> products;

    public CartService() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addProduct(Set<Product> products) {
        this.products.addAll(products.stream().map(
                product -> {
                    var newProduct = new Product();
                    newProduct.setCount(1);
                    newProduct.setName(product.getName());
                    newProduct.setId(product.getId());
                    return newProduct;
                }
        ).collect(Collectors.toList()));
    }

    public void deleteProduct(Product product) {
        products.removeIf(p -> p.getId().equals(product.getId()));
    }

    public void increaseProductCount(Product product) {
        for(Product innerProduct: products) {
            if(product.getId().equals(innerProduct.getId())) {
                innerProduct.incrementCount();
                return;
            }
        }
    }

    public void decreaseProductCount(Product product) {
        for(Product innerProduct: products) {
            if(product.getId().equals(innerProduct.getId())) {
                innerProduct.decreaseCount();
                return;
            }
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}
