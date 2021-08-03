package com.example.techshop4;


import com.example.techshop4.entity.Product;
import com.example.techshop4.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.UUID;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService.clear();
    }

    @Test
    public void cartFillingTest() {
        for(int i = 0; i < 10; i++) {
            var product = new Product();
            product.setId(UUID.randomUUID());
            product.setPrice(new BigDecimal(100 + (long) i * 10));
            product.setName("Product #" + i);
            cartService.getProducts().add(product);
        }

        Assertions.assertEquals(10, cartService.getProducts().size());
    }

    @Test
    public void cartClearTest() {
        for(int i = 0; i < 5; i++) {
            var product = new Product();
            product.setId(UUID.randomUUID());
            product.setPrice(new BigDecimal(100 + (long) i * 10));
            product.setName("Product #" + i);
            cartService.getProducts().add(product);
        }

        Assertions.assertEquals(5, cartService.getProducts().size());
        cartService.clear();
        Assertions.assertEquals(0, cartService.getProducts().size());
    }
}

