package com.givts.app.payload.Occasion;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class OccasionRequest {

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    @NotNull(message = "{occasion.date.notNull}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public OccasionRequest(String name, LocalDate date) {
        this.name = name;
        this.date = date;
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
}
