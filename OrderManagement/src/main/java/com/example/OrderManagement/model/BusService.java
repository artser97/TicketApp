package com.example.OrderManagement.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusService {
  private Long id;
  @NotBlank(message = "Company name is required")
  private String companyName;

  @NotBlank(message = "Start city is required")
  private String startCity;

  @NotBlank(message = "Destination city is required")
  private String destinationCity;

  private String departureDate;
  private int capacity;
  private int ticketsLeft;
  private double pricePerTicket;
}
