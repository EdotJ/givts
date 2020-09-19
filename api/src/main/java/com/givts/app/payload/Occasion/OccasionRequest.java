package com.givts.app.payload.Occasion;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class OccasionRequest {

    @NotNull(message = "{name.notNull}")
    private String name;

    @NotNull(message = "{occasion.date.notNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonProperty("giftee_id")
    @NotNull(message = "{occasion.gifteeId.notNull}")
    private Long gifteeId;

    public OccasionRequest(String name, LocalDate date, long gifteeId) {
        this.name = name;
        this.date = date;
        this.gifteeId = gifteeId;
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

    public Long getGifteeId() {
        return gifteeId;
    }

    public void setGifteeId(Long gifteeId) {
        this.gifteeId = gifteeId;
    }
}
