package com.example.OrderManagement.service;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.model.OrderItem;
import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.mapping.OrderMapper;
import com.example.OrderManagement.persistence.model.BusServiceEntity;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.repository.BusServiceRepository;
import com.example.OrderManagement.persistence.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private BusServiceRepository busServiceRepository;

  @Mock
  private OrderMapper orderMapper;

  @InjectMocks
  private OrderService orderService;

  private Order order;
  private OrderEntity orderEntity;
  private OrderItem orderItem;
  private BusServiceEntity busServiceEntity;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);

    // Sample BusServiceEntity
    busServiceEntity = new BusServiceEntity();
    busServiceEntity.setId(1L);
    busServiceEntity.setCompanyName("FlixBus");
    busServiceEntity.setTicketsLeft(50);
    busServiceEntity.setPricePerTicket(25.5);

    orderItem = new OrderItem();
    orderItem.setBusServiceId(1L);
    orderItem.setQuantity(2);

    order = new Order();
    order.setItems(List.of(orderItem));
    order.setStatus(OrderStatus.PENDING);

    orderEntity = new OrderEntity();
    orderEntity.setId(1L);
    orderEntity.setStatus(OrderStatus.PENDING);
    orderEntity.setTotalPrice(51.0);
    orderEntity.setCreatedAt(LocalDateTime.now());
  }

  @Test
  void testCreateOrder_Success() {
    when(busServiceRepository.findById(1L)).thenReturn(Optional.of(busServiceEntity));
    when(orderMapper.toEntity(any(Order.class))).thenReturn(orderEntity);
    when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
    when(orderMapper.toDto(orderEntity)).thenReturn(order);

    Order createdOrder = orderService.createOrder(order);

    assertNotNull(createdOrder);
    assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
    assertEquals(51.0, createdOrder.getTotalPrice());
    verify(busServiceRepository, times(1)).findById(1L);
    verify(busServiceRepository, times(1)).save(busServiceEntity);
    verify(orderRepository, times(1)).save(orderEntity);
  }

  @Test
  void testCreateOrder_NotEnoughTickets() {
    busServiceEntity.setTicketsLeft(1); // Insufficient tickets
    when(busServiceRepository.findById(1L)).thenReturn(Optional.of(busServiceEntity));

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        orderService.createOrder(order)
    );

    assertEquals("Not enough tickets available for bus service: 1", exception.getMessage());
    verify(busServiceRepository, times(1)).findById(1L);
    verify(busServiceRepository, never()).save(busServiceEntity);
    verify(orderRepository, never()).save(any());
  }

  @Test
  void testCreateOrder_BusServiceNotFound() {
    when(busServiceRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        orderService.createOrder(order)
    );

    assertEquals("Bus service not found: 1", exception.getMessage());
    verify(busServiceRepository, times(1)).findById(1L);
    verify(busServiceRepository, never()).save(any());
    verify(orderRepository, never()).save(any());
  }

  @Test
  void testGetAllOrders() {
    when(orderRepository.findAll()).thenReturn(List.of(orderEntity));
    when(orderMapper.toDto(orderEntity)).thenReturn(order);

    List<Order> orders = orderService.getAllOrders();

    assertEquals(1, orders.size());
    assertEquals(OrderStatus.PENDING, orders.get(0).getStatus());
    verify(orderRepository, times(1)).findAll();
  }

  @Test
  void testGetOrderById_Success() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));
    when(orderMapper.toDto(orderEntity)).thenReturn(order);

    Order foundOrder = orderService.getOrderById(1L);

    assertNotNull(foundOrder);
    assertEquals(OrderStatus.PENDING, foundOrder.getStatus());
    verify(orderRepository, times(1)).findById(1L);
  }

  @Test
  void testGetOrderById_NotFound() {
    when(orderRepository.findById(1L)).thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class, () ->
        orderService.getOrderById(1L)
    );

    assertEquals("Order not found", exception.getMessage());
    verify(orderRepository, times(1)).findById(1L);
  }

  @Test
  void testUpdateOrderStatus() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

    orderService.updateOrderStatus(1L, "PAID");

    assertEquals(OrderStatus.PAID, orderEntity.getStatus());
    verify(orderRepository, times(1)).save(orderEntity);
  }

  @Test
  void testLinkPayment() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

    orderService.linkPayment(1L, "payment123");

    assertEquals("payment123", orderEntity.getPaymentId());
    assertEquals(OrderStatus.PAID, orderEntity.getStatus());
    verify(orderRepository, times(1)).save(orderEntity);
  }

  @Test
  void testLinkTickets() {
    when(orderRepository.findById(1L)).thenReturn(Optional.of(orderEntity));

    orderService.linkTickets(1L, List.of("ticket1", "ticket2"));

    assertEquals(List.of("ticket1", "ticket2"), orderEntity.getTickets());
    verify(orderRepository, times(1)).save(orderEntity);
  }
}
