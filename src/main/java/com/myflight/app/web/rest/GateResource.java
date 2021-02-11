package com.myflight.app.web.rest;

import com.myflight.app.domain.Gate;
import com.myflight.app.repository.GateRepository;
import com.myflight.app.service.GateService;
import com.myflight.app.web.rest.errors.BadRequestAlertException;

import com.myflight.app.web.rest.vm.AvailableGatesVM;
import com.myflight.app.web.rest.vm.UpdatedGateVM;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.undertow.util.BadRequestException;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.myflight.app.domain.Gate}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GateResource {

    private final Logger log = LoggerFactory.getLogger(GateResource.class);

    private static final String ENTITY_NAME = "gate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GateRepository gateRepository;

    @Autowired
    GateService gateService;

    public GateResource(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    /**
     * {@code POST  /gates} : Create a new gate.
     *
     * @param gate the gate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gate, or with status {@code 400 (Bad Request)} if the gate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gates")
    public ResponseEntity<Gate> createGate(@RequestBody Gate gate) throws URISyntaxException {
        log.debug("REST request to save Gate : {}", gate);
        if (gate.getId() != null) {
            throw new BadRequestAlertException("A new gate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gate result = gateRepository.save(gate);
        return ResponseEntity.created(new URI("/api/gates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gates} : Updates an existing gate.
     *
     * @param gate the gate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gate,
     * or with status {@code 400 (Bad Request)} if the gate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gates")
    public ResponseEntity<Gate> updateGate(@RequestBody Gate gate) throws URISyntaxException {
        log.debug("REST request to update Gate : {}", gate);
        if (gate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gate result = gateRepository.save(gate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, gate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /availableGates} : get all available gates.
     *
     * @return the list of gates in body.
     */
    @GetMapping("/availableGates")
    public List<AvailableGatesVM> getAllAvailableGates() {
        log.debug("REST request to get all Gates");
        return gateService.findAllAvailable();
    }

    /**
     * {@code POST  /updateGateAsAvailable/{id}} : Update the gate as available.
     *
     * @param id the gate to update.
     * @return the {@link UpdatedGateVM} with status {@code 200 (Ok)} and with body the updated gate, or with status {@code 400 (Bad Request)} if the gate is already available.
     */
    @PostMapping("/updateGateAsAvailable/{id}")
    public UpdatedGateVM updateGateToAvailable(@PathVariable @NotNull Long id) throws BadRequestException {
        log.debug("REST request to update gate with id: " + id);
        return gateService.updateGateToAvailable(id);
    }

    /**
     * {@code GET  /gates} : get all the gates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gates in body.
     */
    @GetMapping("/gates")
    public List<Gate> getAllGates() {
        log.debug("REST request to get all Gates");
        return gateRepository.findAll();
    }

    /**
     * {@code GET  /gates/:id} : get the "id" gate.
     *
     * @param id the id of the gate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gates/{id}")
    public ResponseEntity<Gate> getGate(@PathVariable Long id) {
        log.debug("REST request to get Gate : {}", id);
        Optional<Gate> gate = gateRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(gate);
    }

    /**
     * {@code DELETE  /gates/:id} : delete the "id" gate.
     *
     * @param id the id of the gate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gates/{id}")
    public ResponseEntity<Void> deleteGate(@PathVariable Long id) {
        log.debug("REST request to delete Gate : {}", id);
        gateRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
