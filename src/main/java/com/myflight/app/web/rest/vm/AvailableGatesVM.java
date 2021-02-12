package com.myflight.app.web.rest.vm;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class AvailableGatesVM {

    @NotNull
    private Long gateId;

    @NotNull
    private String gateNumber;

    private ZonedDateTime dateAvailableFrom;

    private ZonedDateTime dateAvailableTo;

    public AvailableGatesVM() {}

    public AvailableGatesVM(@NotNull Long gateId, @NotNull String gateNumber, ZonedDateTime dateAvailableFrom, ZonedDateTime dateAvailableTo) {
        this.gateId = gateId;
        this.gateNumber = gateNumber;
        this.dateAvailableFrom = dateAvailableFrom;
        this.dateAvailableTo = dateAvailableTo;
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

    public ZonedDateTime getDateAvailableFrom() {
        return dateAvailableFrom;
    }

    public void setDateAvailableFrom(ZonedDateTime dateAvailableFrom) {
        this.dateAvailableFrom = dateAvailableFrom;
    }

    public ZonedDateTime getDateAvailableTo() {
        return dateAvailableTo;
    }

    public void setDateAvailableTo(ZonedDateTime dateAvailableTo) {
        this.dateAvailableTo = dateAvailableTo;
    }
}
