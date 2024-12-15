package com.example.OrderManagement.persistence;

import com.example.OrderManagement.persistence.model.BusServiceEntity;
import com.example.OrderManagement.persistence.repository.BusServiceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BusServicePersistenceTest {

  @Autowired
  private BusServiceRepository busServiceRepository;

  @BeforeEach
  public void setup() {

    BusServiceEntity bus1 = new BusServiceEntity();
    bus1.setCompanyName("FlixBus");
    bus1.setStartCity("Vilnius");
    bus1.setDestinationCity("Warsaw");
    bus1.setDepartureDate(LocalDate.of(2024, 12, 20));
    bus1.setCapacity(50);
    bus1.setTicketsLeft(30);
    bus1.setPricePerTicket(15.5);

    BusServiceEntity bus2 = new BusServiceEntity();
    bus2.setCompanyName("LuxExpress");
    bus2.setStartCity("Vilnius");
    bus2.setDestinationCity("Kaunas");
    bus2.setDepartureDate(LocalDate.of(2024, 12, 21));
    bus2.setCapacity(40);
    bus2.setTicketsLeft(20);
    bus2.setPricePerTicket(20.0);

    busServiceRepository.save(bus1);
    busServiceRepository.save(bus2);
  }

  @Test
  public void testSearchByStartCity() {
    List<BusServiceEntity> results = busServiceRepository.search("Vilnius", null, null);

    assertEquals(2, results.size(), "There should be 2 results for buses starting from Vilnius");
  }

  @Test
  public void testSearchByStartCityAndDestinationCity() {
    List<BusServiceEntity> results = busServiceRepository.search("Vilnius", "Warsaw", null);

    assertEquals(1, results.size(), "There should be 1 result for buses from Vilnius to Warsaw");
    assertEquals("Warsaw", results.get(0).getDestinationCity());
  }

  @Test
  public void testSearchByStartCityAndDepartureDate() {
    List<BusServiceEntity> results = busServiceRepository.search("Vilnius", null, LocalDate.of(2024, 12, 20));

    assertEquals(1, results.size(), "There should be 1 result for buses from Vilnius departing on 2024-12-20");
    assertEquals(LocalDate.of(2024, 12, 20), results.get(0).getDepartureDate());
  }

  @Test
  public void testSearchByAllParameters() {
    List<BusServiceEntity> results = busServiceRepository.search("Vilnius", "Kaunas", LocalDate.of(2024, 12, 21));

    assertEquals(1, results.size(), "There should be 1 result for buses from Vilnius to Kaunas on 2024-12-20");
  }

  @Test
  public void testSearchWithNoParameters() {
    List<BusServiceEntity> results = busServiceRepository.search(null, null, null);

    assertEquals(2, results.size(), "There should be 2 results when no filters are applied");
  }

  @Test
  public void testSearchNoResults() {
    List<BusServiceEntity> results = busServiceRepository.search("Nonexistent City", null, null);

    assertTrue(results.isEmpty(), "There should be no results for a nonexistent city");
  }
}
