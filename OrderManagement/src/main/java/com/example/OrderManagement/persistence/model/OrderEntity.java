package com.example.OrderManagement.persistence.model;

import com.example.OrderManagement.model.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class OrderEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String customerName;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "order_id")
  private List<OrderItemEntity> items;

  private double totalPrice;

  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private String paymentId; 

  @ElementCollection
  @CollectionTable(name = "order_tickets", joinColumns = @JoinColumn(name = "order_id"))
  private List<String> tickets;
}
