package com.example.OrderManagement.persistence.mapping;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.model.OrderItem;
import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.model.OrderItemEntity;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

  public Order toDto(OrderEntity entity) {
    if (entity == null) {
      return null;
    }

    Order order = new Order();
    order.setId(entity.getId());
    order.setCustomerName(entity.getCustomerName());
    order.setItems(
        entity.getItems() == null ? null : entity.getItems().stream().map(this::toItemDto).toList()
    );
    order.setTotalPrice(entity.getTotalPrice());
    order.setCreatedAt(entity.getCreatedAt());
    order.setStatus(entity.getStatus().name());
    order.setPaymentId(entity.getPaymentId());
    order.setTickets(entity.getTickets());

    return order;
  }

  public OrderEntity toEntity(Order dto) {
    if (dto == null) {
      return null;
    }

    OrderEntity entity = new OrderEntity();
    entity.setCustomerName(dto.getCustomerName());
    entity.setItems(
        dto.getItems() == null ? null : dto.getItems().stream().map(this::toItemEntity).toList()
    );
    entity.setTotalPrice(dto.getTotalPrice());
    entity.setCreatedAt(dto.getCreatedAt());
    entity.setStatus(dto.getStatus() == null ? null : OrderStatus.valueOf(dto.getStatus().toUpperCase()));
    entity.setPaymentId(dto.getPaymentId());
    entity.setTickets(dto.getTickets());

    return entity;
  }

  private OrderItem toItemDto(OrderItemEntity entity) {
    if (entity == null) {
      return null;
    }

    OrderItem item = new OrderItem();
    item.setBusServiceId(entity.getBusServiceId());
    item.setQuantity(entity.getQuantity());
    item.setPricePerTicket(entity.getPricePerTicket());
    item.setTotalPrice(entity.getTotalPrice());
    return item;
  }

  private OrderItemEntity toItemEntity(OrderItem item) {
    if (item == null) {
      return null;
    }

    OrderItemEntity entity = new OrderItemEntity();
    entity.setBusServiceId(item.getBusServiceId());
    entity.setQuantity(item.getQuantity());
    entity.setPricePerTicket(item.getPricePerTicket());
    entity.setTotalPrice(item.getTotalPrice());
    return entity;
  }
}


