package com.example.OrderManagement.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "order_items")
public class OrderItemEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long busServiceId;

  private int quantity = 1;

  private double pricePerTicket;

  private double totalPrice;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;
}

