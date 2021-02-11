package com.myflight.app.service.dto;

import javax.validation.constraints.NotNull;

public class FlightDTO {

    @NotNull
    private String flightNumber;

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
            "flightNumber='" + flightNumber + '\'' +
            '}';
    }
}
