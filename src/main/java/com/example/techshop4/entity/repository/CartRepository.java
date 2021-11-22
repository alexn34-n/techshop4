package com.example.techshop4.entity.repository;

import com.example.techshop4.entity.Cart;
import com.example.techshop4.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
  @Query("select c from Cart c where c.order is null")
    Optional<Cart> findCartByOrderIdIsNull();


  @Query("select c from Cart c where c.user=:user and c.order is null")
    Optional<Cart> findByUser(User user);

}
