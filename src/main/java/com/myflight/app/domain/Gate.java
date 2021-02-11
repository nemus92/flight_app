package com.myflight.app.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Gate.
 */
@Entity
@Table(name = "gate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Gate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "gate_number")
    private String gateNumber;

    @Column(name = "flight_number")
    private String flightNumber;

    @Column(name = "date_available_from")
    private ZonedDateTime dateAvailableFrom;

    @Column(name = "date_available_to")
    private ZonedDateTime dateAvailableTo;

    public Gate() {}

    public Gate(String gateNumber, String flightNumber, ZonedDateTime dateAvailableFrom, ZonedDateTime dateAvailableTo) {
        this.gateNumber = gateNumber;
        this.flightNumber = flightNumber;
        this.dateAvailableFrom = dateAvailableFrom;
        this.dateAvailableTo = dateAvailableTo;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public Gate gateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
        return this;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public Gate flightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
        return this;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public ZonedDateTime getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public Gate dateAvailableFrom(ZonedDateTime dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
        return this;
    }

    public void setDateAvailableFrom(ZonedDateTime dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public ZonedDateTime getDateAvailableTo() {
        return dateAvailableTo;
    }

    public Gate dateAvailableTo(ZonedDateTime dateAvailableTo) {
        this.dateAvailableTo = dateAvailableTo;
        return this;
    }

    public void setDateAvailableTo(ZonedDateTime dateAvailableTo) {
        this.dateAvailableTo = dateAvailableTo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gate)) {
            return false;
        }
        return id != null && id.equals(((Gate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gate{" +
            "id=" + getId() +
            ", gateNumber='" + getGateNumber() + "'" +
            ", flightNumber='" + getFlightNumber() + "'" +
            ", dateAvailableFrom='" + getDateAvailableFrom() + "'" +
            ", dateAvailableTo='" + getDateAvailableTo() + "'" +
            "}";
    }
}
