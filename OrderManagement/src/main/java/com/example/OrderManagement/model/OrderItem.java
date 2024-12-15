package com.example.OrderManagement.model;

import lombok.Data;

@Data
public class OrderItem {
  private Long busServiceId;
  private int quantity;
  private double pricePerTicket;
  private double totalPrice;
}

