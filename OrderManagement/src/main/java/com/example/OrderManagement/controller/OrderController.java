package com.example.OrderManagement.controller;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public Order createOrder(@RequestBody Order order) {
    return orderService.createOrder(order);
  }

  @GetMapping
  public List<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public Order getOrderById(@PathVariable Long id) {
    return orderService.getOrderById(id);
  }

  @PutMapping("/{id}/status")
  public void updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
    orderService.updateOrderStatus(id, status);
  }

  @PutMapping("/{id}/payment")
  public void linkPayment(@PathVariable Long id, @RequestParam String paymentId) {
    orderService.linkPayment(id, paymentId);
  }

  @PutMapping("/{id}/tickets")
  public void linkTickets(@PathVariable Long id, @RequestBody List<String> ticketIds) {
    orderService.linkTickets(id, ticketIds);
  }
}
