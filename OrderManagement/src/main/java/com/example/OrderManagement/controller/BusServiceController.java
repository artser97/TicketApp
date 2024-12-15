package com.example.OrderManagement.controller;

import com.example.OrderManagement.model.BusService;
import com.example.OrderManagement.service.BusServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/bus-services")
@RequiredArgsConstructor
public class BusServiceController {

  private final BusServiceService busServiceService;

  @PostMapping
  public ResponseEntity<BusService> createBusService(@RequestBody BusService busService) {
    return ResponseEntity.ok(busServiceService.create(busService));
  }


  @PutMapping("/{id}")
  public ResponseEntity<BusService> updateBusService(@PathVariable Long id, @RequestBody BusService busService) {
    return ResponseEntity.ok(busServiceService.update(id, busService));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBusService(@PathVariable Long id) {
    busServiceService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search")
  public ResponseEntity<List<BusService>> searchBusServices(
      @RequestParam(required = false) String startCity,
      @RequestParam(required = false) String destinationCity,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate) {
    List<BusService> results = busServiceService.search(startCity, destinationCity, departureDate);
    return ResponseEntity.ok(results);
  }
}

