package com.example.OrderManagement.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "bus_services")
public class BusServiceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String companyName;

  private String startCity;

  private String destinationCity;

  private LocalDate departureDate;

  private int capacity;

  private int ticketsLeft;

  private double pricePerTicket;
}

