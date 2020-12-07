package com.givts.app.payload.Gift;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Gift;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SingleGiftResponse {

    private long id;

    private String name;

    private String description;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("modified_date")
    private LocalDateTime modifiedDate;

    private BigDecimal price;

    @JsonProperty("image_id")
    private String imagePath;

    public SingleGiftResponse(Gift gift) {
        this.id = gift.getId();
        this.name = gift.getName();
        this.description = gift.getDescription();
        this.createdDate = gift.getCreatedDate();
        this.modifiedDate = gift.getModifiedDate();
        this.price = gift.getPrice();
        this.imagePath = gift.getImagePath();
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
