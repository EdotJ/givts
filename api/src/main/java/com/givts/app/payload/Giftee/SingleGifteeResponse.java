package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Giftee;

import java.time.LocalDateTime;

public class SingleGifteeResponse {

    private Long id;

    private String name;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    public SingleGifteeResponse(Giftee giftee) {
        this.id = giftee.getId();
        this.name = giftee.getName();
        this.createdDate = giftee.getCreatedDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
