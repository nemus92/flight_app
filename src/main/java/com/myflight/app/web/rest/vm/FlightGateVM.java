package com.myflight.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class FlightGateVM {

    @NotNull private Long flightId;

    @NotNull private Long gateId;

    @NotNull private String gateNumber;

    @NotNull private String flightNumber;

    public FlightGateVM() {}

    public FlightGateVM(@NotNull Long flightID, @NotNull Long gateId, @NotNull String gateNumber, @NotNull String flightNumber) {
        this.flightId = flightID;
        this.gateId = gateId;
        this.gateNumber = gateNumber;
        this.flightNumber = flightNumber;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public String getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(String gateNumber) {
        this.gateNumber = gateNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "FlightGateVM{" +
            "flightId=" + flightId +
            ", gateId=" + gateId +
            ", gateNumber='" + gateNumber + '\'' +
            ", flightNumber='" + flightNumber + '\'' +
            '}';
    }
}
