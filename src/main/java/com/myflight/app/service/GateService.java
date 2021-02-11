package com.myflight.app.service;

import com.myflight.app.domain.Gate;
import com.myflight.app.repository.GateRepository;
import com.myflight.app.web.rest.FlightResource;
import com.myflight.app.web.rest.GateResource;
import com.myflight.app.web.rest.errors.BadRequestAlertException;
import com.myflight.app.web.rest.vm.AvailableGatesVM;
import com.myflight.app.web.rest.vm.UpdatedGateVM;
import io.undertow.util.BadRequestException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import liquibase.pro.packaged.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GateService {

    @Autowired
    GateRepository gateRepository;

    private final Logger log = LoggerFactory.getLogger(FlightResource.class);

    public List<AvailableGatesVM> findAllAvailable() {

        final List<AvailableGatesVM> availableGatesVMS = new ArrayList<>();

        final List<Gate> availableGates = gateRepository.getAllAvailableGates();

        return availableGates.stream().map(gate -> new AvailableGatesVM(gate.getId(), gate.getGateNumber())).collect(Collectors.toList());
    }

    public UpdatedGateVM updateGateToAvailable(Long gateId) throws BadRequestException {

        final Optional<Gate> gate = gateRepository.findById(gateId);
        final UpdatedGateVM updatedGateVM = new UpdatedGateVM();

        if (gate.isPresent()) {
            if (gate.get().getFlightNumber() == null || gate.get().getFlightNumber().isEmpty()) {
                throw new BadRequestException("Gate with id: " + gate.get().getId().toString() + " is already available.");
            }
            gate.get().setFlightNumber(null);

            gateRepository.save(gate.get());



            log.debug("Gate with id: " + gate.get().getId() + " is now available.");
        } else {
            throw new NoResultException("Gate with id: " + gateId.toString() + " does not exist!");
        }

        return null;
    }

    public void insertGatesToDB() {

        final Gate gate = new Gate("G-11", "FL-101", null, null);
        final Gate gate2 = new Gate("G-12", null, null, null);
        final Gate gate3 = new Gate("G-13", null, null, null);
        final Gate gate4 = new Gate("G-14", null, null, null);
        final Gate gate5 = new Gate("G-15", null, null, null);

        final List<Gate> gates = new ArrayList<>();
        gates.add(gate);
        gates.add(gate2);
        gates.add(gate3);
        gates.add(gate4);
        gates.add(gate5);

        gateRepository.saveAll(gates);
    }
}