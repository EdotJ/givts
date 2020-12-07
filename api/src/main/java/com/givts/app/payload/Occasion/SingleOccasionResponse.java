package com.givts.app.payload.Occasion;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Occasion;

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

    private int giftCount;

    public SingleOccasionResponse(Occasion occasion) {
        this.id = occasion.getId();
        this.name = occasion.getName();
        this.date = occasion.getDate();
        this.createdDate = occasion.getCreatedDate();
        this.modifiedDate = occasion.getModifiedDate();
        this.giftCount = occasion.getGifts() == null ? 0 : occasion.getGifts().size();
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

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }
}
