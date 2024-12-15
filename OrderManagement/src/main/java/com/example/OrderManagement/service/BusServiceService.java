package com.example.OrderManagement.service;

import com.example.OrderManagement.model.BusService;
import com.example.OrderManagement.persistence.mapping.BusServiceMapper;
import com.example.OrderManagement.persistence.model.BusServiceEntity;
import com.example.OrderManagement.persistence.repository.BusServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BusServiceService {

  private final BusServiceRepository busServiceRepository;
  private final BusServiceMapper busServiceMapper;

  public BusService create(BusService dto) {
    log.info("Received request to create bus service: {}", dto);
    BusServiceEntity entity = busServiceMapper.toEntity(dto);
    BusServiceEntity savedEntity = busServiceRepository.save(entity);
    log.info("Bus service created: {}", savedEntity);
    return busServiceMapper.toDto(savedEntity);
  }

  public BusService update(Long id, BusService dto) {
    BusServiceEntity entity = busServiceRepository.findById(id)
        .orElseThrow(() -> new ObjectNotFoundException("Bus service not found . Id:", id));

    entity.setCompanyName(dto.getCompanyName());
    entity.setStartCity(dto.getStartCity());
    entity.setDestinationCity(dto.getDestinationCity());
    entity.setDepartureDate(LocalDate.parse(dto.getDepartureDate()));
    entity.setCapacity(dto.getCapacity());
    entity.setTicketsLeft(dto.getTicketsLeft());
    entity.setPricePerTicket(dto.getPricePerTicket());

    BusServiceEntity updatedEntity = busServiceRepository.save(entity);
    log.info("Bus service created: {}", updatedEntity);
    return busServiceMapper.toDto(updatedEntity);
  }

  public void delete(Long id) {
    if (!busServiceRepository.existsById(id)) {
      throw new ObjectNotFoundException("Bus service not found . Id:", id);
    }
    busServiceRepository.deleteById(id);
  }

  public List<BusService> search(String startCity, String destinationCity, LocalDate departureDate) {
    List<BusServiceEntity> entities = busServiceRepository.search(startCity, destinationCity, departureDate);
    return entities.stream()
        .map(busServiceMapper::toDto)
        .toList();
  }
}