package com.example.OrderManagement.persistence.mapping;

import com.example.OrderManagement.model.BusService;
import com.example.OrderManagement.persistence.model.BusServiceEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class BusServiceMapper {

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public BusService toDto(BusServiceEntity entity) {
    if (entity == null) {
      return null;
    }

    BusService busService = new BusService();
    busService.setId(entity.getId());
    busService.setCompanyName(entity.getCompanyName());
    busService.setStartCity(entity.getStartCity());
    busService.setDestinationCity(entity.getDestinationCity());
    busService.setDepartureDate(entity.getDepartureDate() != null ? entity.getDepartureDate().format(DATE_FORMATTER) : null);
    busService.setCapacity(entity.getCapacity());
    busService.setTicketsLeft(entity.getTicketsLeft());
    busService.setPricePerTicket(entity.getPricePerTicket());

    return busService;
  }

  public BusServiceEntity toEntity(BusService dto) {
    if (dto == null) {
      return null;
    }

    BusServiceEntity entity = new BusServiceEntity();
    entity.setId(dto.getId());
    entity.setCompanyName(dto.getCompanyName());
    entity.setStartCity(dto.getStartCity());
    entity.setDestinationCity(dto.getDestinationCity());
    entity.setDepartureDate(dto.getDepartureDate() != null ? LocalDate.parse(dto.getDepartureDate(), DATE_FORMATTER) : null);
    entity.setCapacity(dto.getCapacity());
    entity.setTicketsLeft(dto.getTicketsLeft());
    entity.setPricePerTicket(dto.getPricePerTicket());

    return entity;
  }
}
