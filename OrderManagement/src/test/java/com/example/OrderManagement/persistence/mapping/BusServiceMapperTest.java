package com.example.OrderManagement.persistence.mapping;

import com.example.OrderManagement.model.BusService;
import com.example.OrderManagement.persistence.model.BusServiceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BusServiceMapperTest {

  private BusServiceMapper busServiceMapper;

  private static final String START_CITY = "Vilnius";
  private static final String DESTINATION_CITY = "Kaunas";
  private static final String COMPANY_NAME = "FlixBus";

  @BeforeEach
  void setup() {
    busServiceMapper = new BusServiceMapper();
  }

  @Test
  void testToDto_Success() {
    // Given
    BusServiceEntity entity = new BusServiceEntity();
    entity.setId(1L);
    entity.setCompanyName(COMPANY_NAME);
    entity.setStartCity(START_CITY);
    entity.setDestinationCity(DESTINATION_CITY);
    entity.setDepartureDate(LocalDate.of(2024, 12, 25));
    entity.setCapacity(50);
    entity.setTicketsLeft(30);
    entity.setPricePerTicket(20.0);

    // When
    BusService dto = busServiceMapper.toDto(entity);

    // Then
    assertNotNull(dto);
    assertEquals(1L, dto.getId());
    assertEquals(COMPANY_NAME, dto.getCompanyName());
    assertEquals(START_CITY, dto.getStartCity());
    assertEquals(DESTINATION_CITY, dto.getDestinationCity());
    assertEquals("2024-12-25", dto.getDepartureDate());
    assertEquals(50, dto.getCapacity());
    assertEquals(30, dto.getTicketsLeft());
    assertEquals(20.0, dto.getPricePerTicket());
  }

  @Test
  void testToDto_NullEntity() {
    BusService dto = busServiceMapper.toDto(null);
    assertNull(dto);
  }

  @Test
  void testToEntity_Success() {
    // Given
    BusService dto = new BusService();
    dto.setId(1L);
    dto.setCompanyName(COMPANY_NAME);
    dto.setStartCity(START_CITY);
    dto.setDestinationCity(DESTINATION_CITY);
    dto.setDepartureDate("2024-12-25");
    dto.setCapacity(50);
    dto.setTicketsLeft(30);
    dto.setPricePerTicket(20.0);

    // When
    BusServiceEntity entity = busServiceMapper.toEntity(dto);

    // Then
    assertNotNull(entity);
    assertEquals(1L, entity.getId());
    assertEquals(COMPANY_NAME, entity.getCompanyName());
    assertEquals(START_CITY, entity.getStartCity());
    assertEquals(DESTINATION_CITY, entity.getDestinationCity());
    assertEquals(LocalDate.of(2024, 12, 25), entity.getDepartureDate());
    assertEquals(50, entity.getCapacity());
    assertEquals(30, entity.getTicketsLeft());
    assertEquals(20.0, entity.getPricePerTicket());
  }

  @Test
  void testToEntity_NullDto() {
    BusServiceEntity entity = busServiceMapper.toEntity(null);
    assertNull(entity);
  }

  @Test
  void testToEntity_NullDepartureDate() {
    // Given
    BusService dto = new BusService();
    dto.setId(1L);
    dto.setCompanyName(COMPANY_NAME);
    dto.setStartCity(START_CITY);
    dto.setDestinationCity(DESTINATION_CITY);
    dto.setDepartureDate(null);
    dto.setCapacity(50);
    dto.setTicketsLeft(30);
    dto.setPricePerTicket(20.0);

    // When
    BusServiceEntity entity = busServiceMapper.toEntity(dto);

    // Then
    assertNotNull(entity);
    assertNull(entity.getDepartureDate());
  }
}
