package com.myflight.app.service;

import com.myflight.app.domain.Flight;
import com.myflight.app.domain.Gate;
import com.myflight.app.repository.FlightRepository;
import com.myflight.app.repository.GateRepository;
import com.myflight.app.service.dto.FlightDTO;
import com.myflight.app.web.rest.FlightResource;
import com.myflight.app.web.rest.vm.AssignedGateVM;
import com.myflight.app.web.rest.vm.FlightGateVM;
import io.undertow.util.BadRequestException;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.NoResultException;
import liquibase.exception.DatabaseException;
import liquibase.pro.packaged.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlightService {

    @Autowired GateRepository gateRepository;

    @Autowired FlightRepository flightRepository;

    private final Logger log = LoggerFactory.getLogger(FlightResource.class);

    public AssignedGateVM getAssignedGate(FlightDTO flightDTO) throws Exception {

        final List<Flight> flight = flightRepository.findByFlightNumber(flightDTO.getFlightNumber());

        if (flight.isEmpty()) {
            throw new NoResultException("Flight with number: " + flightDTO.getFlightNumber() + " does not exist!");
        }

        final List<Gate> gates = gateRepository.getGateForFlight(flightDTO.getFlightNumber().trim());

        if (gates.isEmpty()) {
            throw new NoResultException("There is no gate assigned for flight number: " + flightDTO.getFlightNumber());
        }

        if (gates.size() > 1) {
            throw new DatabaseException("Flight with number: " + flightDTO.getFlightNumber() + " assigned to multiple gates.");
        }

        final Gate gate = gates.get(0);

        if (Optional.ofNullable(gate.getId()).isPresent() && Optional.ofNullable(gate.getGateNumber()).isPresent()) {
            return new AssignedGateVM(gate.getId(), gate.getGateNumber());
        } else {
            throw new DatabaseException("Database error, gate data incomplete!");
        }
    }

    public FlightGateVM assignFlightToAvailableGate(FlightDTO flightDTO) throws DatabaseException, BadRequestException {

        final List<Gate> gates = gateRepository.getAllAvailableGates(ZonedDateTime.now());

        final Optional<Gate> availableGate = gates.stream().findFirst();

        if (availableGate.isEmpty()) {
            throw new NoResultException("There is no available gates to add the flight: " + flightDTO.getFlightNumber());
        }

        final Optional<Gate> alreadyAssigned =
            gates.stream().filter(gate -> gate.getFlightNumber() != null && gate.getFlightNumber().equals(flightDTO.getFlightNumber())).findAny();

        if (alreadyAssigned.isPresent()) {
            throw new BadRequestException(
                    "Flight with number: " + flightDTO.getFlightNumber() + " already assigned to gate number: " + alreadyAssigned.get().getGateNumber());
        }

        final List<Flight> flights = flightRepository.findByFlightNumber(flightDTO.getFlightNumber().trim());

        if (flights.isEmpty()) {
            throw new NoResultException("No flights with flight number: " + flightDTO.getFlightNumber() + " have been found!");
        }

        if (flights.size() > 1) {
            throw new DatabaseException("More than one flight with same number: " + flightDTO.getFlightNumber() + " have been found!");
        }

        final Flight flight = flights.get(0);

        final Gate gate = availableGate.get();

        gate.setFlightNumber(flight.getFlightNumber());

        gateRepository.save(gate);

        return new FlightGateVM(flight.getId(), gate.getId(), gate.getGateNumber(), gate.getFlightNumber());
    }
}
