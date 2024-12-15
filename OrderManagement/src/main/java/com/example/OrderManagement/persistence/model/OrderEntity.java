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

  private Double totalPrice;

  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  private String paymentId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> items;

  @ElementCollection
  @CollectionTable(name = "order_tickets", joinColumns = @JoinColumn(name = "order_id"))
  private List<String> tickets;
}
