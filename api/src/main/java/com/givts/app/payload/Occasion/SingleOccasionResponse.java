package com.givts.app.payload.Occasion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Occasion;
import com.givts.app.payload.Giftee.SingleGifteeResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SingleOccasionResponse {

    private Long id;

    private String name;

    private LocalDate date;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("modified_date")
    private LocalDateTime modifiedDate;

    @JsonProperty("giftee")
    private SingleGifteeResponse gifteeResponse;

    public SingleOccasionResponse(Occasion occasion) {
        this.id = occasion.getId();
        this.name = occasion.getName();
        this.createdDate = occasion.getCreatedDate();
        this.modifiedDate = occasion.getModifiedDate();
        this.gifteeResponse = new SingleGifteeResponse(occasion.getGiftee());
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public SingleGifteeResponse getGifteeResponse() {
        return gifteeResponse;
    }

    public void setGifteeResponse(SingleGifteeResponse gifteeResponse) {
        this.gifteeResponse = gifteeResponse;
    }
}