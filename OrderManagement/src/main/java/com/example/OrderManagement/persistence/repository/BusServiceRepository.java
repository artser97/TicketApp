package com.example.OrderManagement.persistence.repository;

import com.example.OrderManagement.persistence.model.BusServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusServiceRepository extends JpaRepository<BusServiceEntity, Long> {

  @Query("SELECT b FROM BusServiceEntity b " +
      "WHERE (:startCity IS NULL OR b.startCity = :startCity) " +
      "AND (:destinationCity IS NULL OR b.destinationCity = :destinationCity) " +
      "AND (:departureDate IS NULL OR b.departureDate = :departureDate)")
  List<BusServiceEntity> search(
      @Param("startCity") String startCity,
      @Param("destinationCity") String destinationCity,
      @Param("departureDate") LocalDate departureDate);
}
