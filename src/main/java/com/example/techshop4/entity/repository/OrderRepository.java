package com.example.techshop4.entity.repository;

import com.example.techshop4.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
}
