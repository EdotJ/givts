package com.givts.app.payload.Gift;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GiftRequest {

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    @NotNull(message = "{gift.description.notNull}")
    private String description;

    public GiftRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
