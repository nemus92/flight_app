package com.myflight.app.repository;

import com.myflight.app.domain.Flight;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Flight entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT f FROM Flight f WHERE f.flightNumber = :flightNumber")
    List<Flight> findByFlightNumber(@Param("flightNumber") String flightNumber);
}
