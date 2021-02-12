package com.myflight.app.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

public class GateDTO {

    @NotNull
    private Long id;

    @NotNull
    private ZonedDateTime dateAvailableFrom;

    @NotNull
    private ZonedDateTime dateAvailableTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
