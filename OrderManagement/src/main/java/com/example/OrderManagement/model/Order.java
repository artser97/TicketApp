package com.example.OrderManagement.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
  private Long id; // Generated during creation
  private String customerName;
  private List<OrderItem> items;
  private double totalPrice;
  private LocalDateTime createdAt;
  private OrderStatus status; // PENDING, PAID, CANCELED
  private String paymentId; // To track payment
  private List<String> tickets; // Ticket IDs generated after payment
}
