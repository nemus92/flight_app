package com.myflight.app.web.rest.vm;

import javax.validation.constraints.NotNull;

public class AssignedGateVM {

    @NotNull
    private Long gateId;

    @NotNull
    private String gateName;

    public AssignedGateVM() {}

    public AssignedGateVM(@NotNull Long gateId, @NotNull String gateName) {
        this.gateId = gateId;
        this.gateName = gateName;
    }

    public Long getGateId() {
        return gateId;
    }

    public void setGateId(Long gateId) {
        this.gateId = gateId;
    }

    public String getGateName() {
        return gateName;
    }

    public void setGateName(String gateName) {
        this.gateName = gateName;
    }
}
