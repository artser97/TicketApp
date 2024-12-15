package com.example.OrderManagement.service;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.model.OrderItem;
import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.mapping.OrderMapper;
import com.example.OrderManagement.persistence.model.BusServiceEntity;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.model.OrderItemEntity;
import com.example.OrderManagement.persistence.repository.BusServiceRepository;
import com.example.OrderManagement.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final BusServiceRepository busServiceRepository;
  private final OrderMapper orderMapper;

  public Order createOrder(Order order) {
    double totalPrice = 0.0;

    // Fetch bus service details and calculate total price
    for (OrderItem item : order.getItems()) {
      BusServiceEntity busServiceEntity = busServiceRepository.findById(item.getBusServiceId())
          .orElseThrow(() -> new RuntimeException("Bus service not found: " + item.getBusServiceId()));

      if (busServiceEntity.getTicketsLeft() < item.getQuantity()) {
        throw new RuntimeException("Not enough tickets available for bus service: " + item.getBusServiceId());
      }

      // Update item with ticket price and calculate total
      item.setPricePerTicket(busServiceEntity.getPricePerTicket());
      item.setTotalPrice(item.getPricePerTicket() * item.getQuantity());
      totalPrice += item.getTotalPrice();

      // Reduce the number of tickets available
      busServiceEntity.setTicketsLeft(busServiceEntity.getTicketsLeft() - item.getQuantity());
      busServiceRepository.save(busServiceEntity); // Update tickets in DB
    }

    // Set calculated total price and initial status
    order.setTotalPrice(totalPrice);
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus("PENDING");

    // Save the order
    OrderEntity savedEntity = orderRepository.save(orderMapper.toEntity(order));

    return orderMapper.toDto(savedEntity);
  }

  public List<Order> getAllOrders() {
    return orderRepository.findAll().stream()
        .map(orderMapper::toDto)
        .toList();
  }

  public Order getOrderById(Long id) {
    OrderEntity entity = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    return orderMapper.toDto(entity);
  }

  public void updateOrderStatus(Long id, String status) {
    OrderEntity entity = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    entity.setStatus(OrderStatus.valueOf(status.toUpperCase()));
    orderRepository.save(entity);
  }

  public void linkPayment(Long orderId, String paymentId) {
    OrderEntity entity = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    entity.setPaymentId(paymentId);
    entity.setStatus(OrderStatus.PAID);
    orderRepository.save(entity);
  }

  public void linkTickets(Long orderId, List<String> ticketIds) {
    OrderEntity entity = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("Order not found"));
    entity.setTickets(ticketIds);
    orderRepository.save(entity);
  }
}

