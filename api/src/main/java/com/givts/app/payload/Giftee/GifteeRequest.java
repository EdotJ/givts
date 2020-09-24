package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GifteeRequest {

    @JsonProperty
    @NotNull (message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    public GifteeRequest() {

    }

    public GifteeRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
