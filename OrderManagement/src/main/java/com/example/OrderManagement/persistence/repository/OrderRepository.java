package com.example.OrderManagement.persistence.repository;

import com.example.OrderManagement.persistence.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
