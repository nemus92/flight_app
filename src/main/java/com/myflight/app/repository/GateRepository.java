package com.myflight.app.repository;

import com.myflight.app.domain.Flight;
import com.myflight.app.domain.Gate;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Gate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GateRepository extends JpaRepository<Gate, Long> {

    @Query("SELECT g FROM Gate g WHERE g.flightNumber IS NULL AND :currentDate BETWEEN g.dateAvailableFrom AND g.dateAvailableTo")
    List<Gate> getAllAvailableGates(@Param("currentDate") ZonedDateTime currentDate);

    @Query("SELECT g FROM Gate g WHERE g.flightNumber = :flightNumber")
    List<Gate> getGateForFlight(@Param("flightNumber") String flightNumber);

    @Query(value = "SELECT g FROM Gate g WHERE g.flightNumber IS NULL")
    List<Gate> getOneAvailableGate(Pageable pageable);
}
