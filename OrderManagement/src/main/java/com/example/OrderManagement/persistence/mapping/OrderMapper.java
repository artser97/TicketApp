package com.example.OrderManagement.persistence.mapping;

import com.example.OrderManagement.model.Order;
import com.example.OrderManagement.model.OrderItem;
import com.example.OrderManagement.model.OrderStatus;
import com.example.OrderManagement.persistence.model.OrderEntity;
import com.example.OrderManagement.persistence.model.OrderItemEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class OrderMapper {

  public Order toDto(OrderEntity entity) {
    if (entity == null) {
      log.warn("Attempting to map a null OrderEntity to DTO");
      return null;
    }

    Order order = new Order();
    order.setId(entity.getId());
    order.setCustomerName(entity.getCustomerName());
    order.setTotalPrice(entity.getTotalPrice());
    order.setCreatedAt(entity.getCreatedAt());
    order.setStatus(entity.getStatus()); // Direct mapping of enum
    order.setPaymentId(entity.getPaymentId());

    // Map items
    order.setItems(entity.getItems() == null ? null
        : entity.getItems().stream().map(this::toItemDto).toList());

    log.debug("Mapped OrderEntity with ID {} to DTO with {} items",
        entity.getId(), entity.getItems() != null ? entity.getItems().size() : 0);
    return order;
  }

  public OrderEntity toEntity(Order dto) {
    if (dto == null) {
      log.warn("Attempting to map a null Order DTO to Entity");
      return null;
    }

    OrderEntity entity = new OrderEntity();
    entity.setCustomerName(dto.getCustomerName());
    entity.setTotalPrice(dto.getTotalPrice());
    entity.setCreatedAt(dto.getCreatedAt());
    entity.setStatus(dto.getStatus()); // Direct mapping of enum
    entity.setPaymentId(dto.getPaymentId());

    // Map items
    if (dto.getItems() != null) {
      List<OrderItemEntity> itemEntities = dto.getItems().stream()
          .map(item -> {
            OrderItemEntity itemEntity = toItemEntity(item);
            itemEntity.setOrder(entity); // Set parent relationship
            return itemEntity;
          })
          .toList();
      entity.setItems(itemEntities);
    } else {
      entity.setItems(null);
    }

    log.info("Mapped OrderEntity with ID {} and {} items.",
        entity.getId(), entity.getItems() != null ? entity.getItems().size() : 0);
    return entity;
  }

  private OrderItem toItemDto(OrderItemEntity entity) {
    if (entity == null) {
      log.warn("Attempting to map a null OrderItemEntity to DTO");
      return null;
    }

    OrderItem item = new OrderItem();
    item.setBusServiceId(entity.getBusServiceId());
    item.setQuantity(entity.getQuantity());
    item.setPricePerTicket(entity.getPricePerTicket());
    item.setTotalPrice(entity.getTotalPrice());
    log.debug("Mapped OrderItemEntity with ID {} to DTO", entity.getId());
    return item;
  }

  private OrderItemEntity toItemEntity(OrderItem item) {
    if (item == null) {
      log.warn("Attempting to map a null OrderItem");
      return null;
    }

    // Validate item fields
    if (item.getQuantity() <= 0) {
      log.error("Invalid quantity for OrderItem: {}", item);
      throw new IllegalArgumentException("Quantity must be greater than 0");
    }

    OrderItemEntity entity = new OrderItemEntity();
    entity.setBusServiceId(item.getBusServiceId());
    entity.setQuantity(item.getQuantity());
    entity.setPricePerTicket(item.getPricePerTicket());
    entity.setTotalPrice(item.getTotalPrice());
    log.debug("Mapped OrderItem to OrderItemEntity with busServiceId {}", item.getBusServiceId());
    return entity;
  }
}
