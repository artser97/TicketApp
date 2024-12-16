package com.example.OrderManagement.persistence.repository;

import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.model.OrderItemEntity;
import com.example.OrderManagement.persistence.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class OrderPersistenceTest {

  @Autowired
  private OrderRepository orderRepository;

  @Test
  public void testPersistOrderWithItems() {
    // Given
    OrderEntity order = new OrderEntity();
    order.setCustomerName("John Doe");
    order.setTotalPrice(66.5);
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);

    OrderItemEntity item1 = new OrderItemEntity();
    item1.setBusServiceId(35L);
    item1.setQuantity(2);
    item1.setPricePerTicket(25.5);
    item1.setTotalPrice(51.0);
    item1.setOrder(order);

    OrderItemEntity item2 = new OrderItemEntity();
    item2.setBusServiceId(36L);
    item2.setQuantity(1);
    item2.setPricePerTicket(15.5);
    item2.setTotalPrice(15.5);
    item2.setOrder(order);

    order.setItems(List.of(item1, item2));

    // When
    OrderEntity savedOrder = orderRepository.save(order);

    // Then
    assertNotNull(savedOrder.getId(), "Order ID should not be null");
    assertEquals(2, savedOrder.getItems().size(), "Order should have 2 items");

    savedOrder.getItems().forEach(item -> {
      assertEquals(savedOrder.getId(), item.getOrder().getId(), "OrderItem should reference the correct Order");
      assertNotNull(item.getId(), "OrderItem ID should not be null");
    });
  }

  @Test
  public void testRetrieveOrderWithItems() {
    // Given
    OrderEntity order = new OrderEntity();
    order.setCustomerName("Jane Smith");
    order.setTotalPrice(100.0);
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus(OrderStatus.PAID);

    OrderItemEntity item = new OrderItemEntity();
    item.setBusServiceId(42L);
    item.setQuantity(3);
    item.setPricePerTicket(30.0);
    item.setTotalPrice(90.0);
    item.setOrder(order);

    order.setItems(List.of(item));

    // When
    OrderEntity savedOrder = orderRepository.save(order);

    // Then
    OrderEntity retrievedOrder = orderRepository.findById(savedOrder.getId()).orElseThrow();
    assertEquals("Jane Smith", retrievedOrder.getCustomerName(), "Customer name should match");
    assertEquals(1, retrievedOrder.getItems().size(), "Order should have 1 item");

    OrderItemEntity retrievedItem = retrievedOrder.getItems().get(0);
    assertEquals(3, retrievedItem.getQuantity(), "Quantity should match");
    assertEquals(30.0, retrievedItem.getPricePerTicket(), "Price per ticket should match");
  }
}
