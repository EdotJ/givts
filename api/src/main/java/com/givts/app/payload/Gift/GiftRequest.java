package com.givts.app.payload.Gift;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class GiftRequest {

    @NotNull(message = "{name.notNull}")
    private String name;

    @NotNull(message = "{gift.description.notNull}")
    private String description;

    @JsonProperty("occasion_id")
    @NotNull(message = "{gift.occasionId.notNull}")
    private Long occasionId;

    public GiftRequest(String name, String description, long occasionId) {
        this.name = name;
        this.description = description;
        this.occasionId = occasionId;
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

    public Long getOccasionId() {
        return occasionId;
    }

    public void setOccasionId(Long occasionId) {
        this.occasionId = occasionId;
    }
}
