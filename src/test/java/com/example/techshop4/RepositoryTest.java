package com.example.techshop4;

import com.example.techshop4.entity.Product;
import com.example.techshop4.entity.repository.ProductRepository;
import com.example.techshop4.entity.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void productRepositorySaveTest() {
        var product = new Product();
        product.setId(UUID.randomUUID());
        product.setPrice(BigDecimal.TEN);
        product.setName("asdasdasd");

        var product2 = new Product();
        product2.setId(UUID.randomUUID());
        product2.setPrice(BigDecimal.TEN);
        product2.setName("123123");
        entityManager.persist(product);
        entityManager.persist(product2);
        entityManager.flush();

        List<Product> productList = productRepository.findAll();

        Assertions.assertEquals(2, productList.size());
        Assertions.assertEquals("123123", productList.get(productList.size() - 1).getName());
        Assertions.assertEquals("asdasdasd", productList.get(0).getName());
    }

    @Test
    public void initDbTest() {
        var userList = userRepository.findAll();
        Assertions.assertEquals(1, userList.size());
    }
}
