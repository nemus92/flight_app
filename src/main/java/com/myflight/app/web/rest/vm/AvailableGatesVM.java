package com.myflight.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class AvailableGatesVM {

    @NotNull
    private Long gateId;

    @NotNull
    private String gateNumber;

    public AvailableGatesVM() {}

    public AvailableGatesVM(@NotNull Long gateId, @NotNull String gateNumber) {
        this.gateId = gateId;
        this.gateNumber = gateNumber;
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
}
