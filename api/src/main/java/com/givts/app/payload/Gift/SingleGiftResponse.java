package com.givts.app.payload.Gift;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Gift;

import java.time.LocalDateTime;

public class SingleGiftResponse {

    private long id;

    private String name;

    private String description;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("modified_date")
    private LocalDateTime modifiedDate;

    public SingleGiftResponse(Gift gift) {
        this.id = gift.getId();
        this.name = gift.getName();
        this.description = gift.getDescription();
        this.createdDate = gift.getCreatedDate();
        this.modifiedDate = gift.getModifiedDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
