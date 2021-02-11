package com.myflight.app.web.rest;

import com.myflight.app.FlightAppApp;
import com.myflight.app.domain.Gate;
import com.myflight.app.repository.GateRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.myflight.app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GateResource} REST controller.
 */
@SpringBootTest(classes = FlightAppApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GateResourceIT {

    private static final String DEFAULT_GATE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GATE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_AVAILABLE_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AVAILABLE_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_AVAILABLE_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_AVAILABLE_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private GateRepository gateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGateMockMvc;

    private Gate gate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gate createEntity(EntityManager em) {
        Gate gate = new Gate()
            .gateNumber(DEFAULT_GATE_NUMBER)
            .flightNumber(DEFAULT_FLIGHT_NUMBER)
            .dateAvailableFrom(DEFAULT_DATE_AVAILABLE_FROM)
            .dateAvailableTo(DEFAULT_DATE_AVAILABLE_TO);
        return gate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gate createUpdatedEntity(EntityManager em) {
        Gate gate = new Gate()
            .gateNumber(UPDATED_GATE_NUMBER)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .dateAvailableFrom(UPDATED_DATE_AVAILABLE_FROM)
            .dateAvailableTo(UPDATED_DATE_AVAILABLE_TO);
        return gate;
    }

    @BeforeEach
    public void initTest() {
        gate = createEntity(em);
    }

    @Test
    @Transactional
    public void createGate() throws Exception {
        int databaseSizeBeforeCreate = gateRepository.findAll().size();
        // Create the Gate
        restGateMockMvc.perform(post("/api/gates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gate)))
            .andExpect(status().isCreated());

        // Validate the Gate in the database
        List<Gate> gateList = gateRepository.findAll();
        assertThat(gateList).hasSize(databaseSizeBeforeCreate + 1);
        Gate testGate = gateList.get(gateList.size() - 1);
        assertThat(testGate.getGateNumber()).isEqualTo(DEFAULT_GATE_NUMBER);
        assertThat(testGate.getFlightNumber()).isEqualTo(DEFAULT_FLIGHT_NUMBER);
        assertThat(testGate.getDateAvailableFrom()).isEqualTo(DEFAULT_DATE_AVAILABLE_FROM);
        assertThat(testGate.getDateAvailableTo()).isEqualTo(DEFAULT_DATE_AVAILABLE_TO);
    }

    @Test
    @Transactional
    public void createGateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gateRepository.findAll().size();

        // Create the Gate with an existing ID
        gate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGateMockMvc.perform(post("/api/gates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gate)))
            .andExpect(status().isBadRequest());

        // Validate the Gate in the database
        List<Gate> gateList = gateRepository.findAll();
        assertThat(gateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGates() throws Exception {
        // Initialize the database
        gateRepository.saveAndFlush(gate);

        // Get all the gateList
        restGateMockMvc.perform(get("/api/gates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gate.getId().intValue())))
            .andExpect(jsonPath("$.[*].gateNumber").value(hasItem(DEFAULT_GATE_NUMBER)))
            .andExpect(jsonPath("$.[*].flightNumber").value(hasItem(DEFAULT_FLIGHT_NUMBER)))
            .andExpect(jsonPath("$.[*].dateAvailableFrom").value(hasItem(sameInstant(DEFAULT_DATE_AVAILABLE_FROM))))
            .andExpect(jsonPath("$.[*].dateAvailableTo").value(hasItem(sameInstant(DEFAULT_DATE_AVAILABLE_TO))));
    }
    
    @Test
    @Transactional
    public void getGate() throws Exception {
        // Initialize the database
        gateRepository.saveAndFlush(gate);

        // Get the gate
        restGateMockMvc.perform(get("/api/gates/{id}", gate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gate.getId().intValue()))
            .andExpect(jsonPath("$.gateNumber").value(DEFAULT_GATE_NUMBER))
            .andExpect(jsonPath("$.flightNumber").value(DEFAULT_FLIGHT_NUMBER))
            .andExpect(jsonPath("$.dateAvailableFrom").value(sameInstant(DEFAULT_DATE_AVAILABLE_FROM)))
            .andExpect(jsonPath("$.dateAvailableTo").value(sameInstant(DEFAULT_DATE_AVAILABLE_TO)));
    }
    @Test
    @Transactional
    public void getNonExistingGate() throws Exception {
        // Get the gate
        restGateMockMvc.perform(get("/api/gates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGate() throws Exception {
        // Initialize the database
        gateRepository.saveAndFlush(gate);

        int databaseSizeBeforeUpdate = gateRepository.findAll().size();

        // Update the gate
        Gate updatedGate = gateRepository.findById(gate.getId()).get();
        // Disconnect from session so that the updates on updatedGate are not directly saved in db
        em.detach(updatedGate);
        updatedGate
            .gateNumber(UPDATED_GATE_NUMBER)
            .flightNumber(UPDATED_FLIGHT_NUMBER)
            .dateAvailableFrom(UPDATED_DATE_AVAILABLE_FROM)
            .dateAvailableTo(UPDATED_DATE_AVAILABLE_TO);

        restGateMockMvc.perform(put("/api/gates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGate)))
            .andExpect(status().isOk());

        // Validate the Gate in the database
        List<Gate> gateList = gateRepository.findAll();
        assertThat(gateList).hasSize(databaseSizeBeforeUpdate);
        Gate testGate = gateList.get(gateList.size() - 1);
        assertThat(testGate.getGateNumber()).isEqualTo(UPDATED_GATE_NUMBER);
        assertThat(testGate.getFlightNumber()).isEqualTo(UPDATED_FLIGHT_NUMBER);
        assertThat(testGate.getDateAvailableFrom()).isEqualTo(UPDATED_DATE_AVAILABLE_FROM);
        assertThat(testGate.getDateAvailableTo()).isEqualTo(UPDATED_DATE_AVAILABLE_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingGate() throws Exception {
        int databaseSizeBeforeUpdate = gateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGateMockMvc.perform(put("/api/gates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gate)))
            .andExpect(status().isBadRequest());

        // Validate the Gate in the database
        List<Gate> gateList = gateRepository.findAll();
        assertThat(gateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGate() throws Exception {
        // Initialize the database
        gateRepository.saveAndFlush(gate);

        int databaseSizeBeforeDelete = gateRepository.findAll().size();

        // Delete the gate
        restGateMockMvc.perform(delete("/api/gates/{id}", gate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gate> gateList = gateRepository.findAll();
        assertThat(gateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
