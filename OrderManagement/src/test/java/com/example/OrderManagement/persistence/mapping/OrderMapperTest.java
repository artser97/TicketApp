package com.example.OrderManagement.persistence.mapping;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.model.OrderItem;
import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.model.OrderItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

  private OrderMapper orderMapper;

  @BeforeEach
  void setup() {
    orderMapper = new OrderMapper();
  }

  @Test
  void testToDto_Success() {
    // Given
    OrderItemEntity itemEntity = new OrderItemEntity();
    itemEntity.setId(1L);
    itemEntity.setBusServiceId(101L);
    itemEntity.setQuantity(2);
    itemEntity.setPricePerTicket(25.0);
    itemEntity.setTotalPrice(50.0);

    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setId(1L);
    orderEntity.setCustomerName("John Doe");
    orderEntity.setTotalPrice(50.0);
    orderEntity.setCreatedAt(LocalDateTime.now());
    orderEntity.setStatus(OrderStatus.PENDING);
    orderEntity.setItems(List.of(itemEntity));

    // When
    Order order = orderMapper.toDto(orderEntity);

    // Then
    assertNotNull(order);
    assertEquals(1L, order.getId());
    assertEquals("John Doe", order.getCustomerName());
    assertEquals(50.0, order.getTotalPrice());
    assertEquals(OrderStatus.PENDING, order.getStatus());
    assertEquals(1, order.getItems().size());

    OrderItem item = order.getItems().get(0);
    assertEquals(101L, item.getBusServiceId());
    assertEquals(2, item.getQuantity());
    assertEquals(25.0, item.getPricePerTicket());
    assertEquals(50.0, item.getTotalPrice());
  }

  @Test
  void testToEntity_Success() {
    // Given
    OrderItem item = new OrderItem();
    item.setBusServiceId(101L);
    item.setQuantity(2);
    item.setPricePerTicket(25.0);
    item.setTotalPrice(50.0);

    Order order = new Order();
    order.setCustomerName("John Doe");
    order.setTotalPrice(50.0);
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);
    order.setItems(List.of(item));

    // When
    OrderEntity orderEntity = orderMapper.toEntity(order);

    // Then
    assertNotNull(orderEntity);
    assertEquals("John Doe", orderEntity.getCustomerName());
    assertEquals(50.0, orderEntity.getTotalPrice());
    assertEquals(OrderStatus.PENDING, orderEntity.getStatus());
    assertEquals(1, orderEntity.getItems().size());

    OrderItemEntity itemEntity = orderEntity.getItems().get(0);
    assertEquals(101L, itemEntity.getBusServiceId());
    assertEquals(2, itemEntity.getQuantity());
    assertEquals(25.0, itemEntity.getPricePerTicket());
    assertEquals(50.0, itemEntity.getTotalPrice());
    assertEquals(orderEntity, itemEntity.getOrder());
  }

  @Test
  void testToDto_NullEntity() {
    // When
    Order order = orderMapper.toDto(null);

    // Then
    assertNull(order);
  }

  @Test
  void testToEntity_NullDto() {
    // When
    OrderEntity orderEntity = orderMapper.toEntity(null);

    // Then
    assertNull(orderEntity);
  }

  @Test
  void testToEntity_InvalidQuantity() {
    // Given
    OrderItem item = new OrderItem();
    item.setBusServiceId(101L);
    item.setQuantity(0);
    item.setPricePerTicket(25.0);
    item.setTotalPrice(50.0);

    Order order = new Order();
    order.setCustomerName("John Doe");
    order.setTotalPrice(50.0);
    order.setCreatedAt(LocalDateTime.now());
    order.setStatus(OrderStatus.PENDING);
    order.setItems(List.of(item));

    // When
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
        orderMapper.toEntity(order)
    );

    // Then
    assertEquals("Quantity must be greater than 0", exception.getMessage());
  }
}
