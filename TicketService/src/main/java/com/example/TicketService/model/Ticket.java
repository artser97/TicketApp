package com.example.TicketService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Ticket {
  @Id
  @GeneratedValue
  private Long id;
  private String eventName;
  private double price;
}
