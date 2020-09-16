package com.givts.app.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Occasion implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private LocalDate date;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Giftee giftee;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Giftee getGiftee() {
        return giftee;
    }

    public void setGiftee(Giftee giftee) {
        this.giftee = giftee;
    }

    @Override
    public String toString() {
        return "Occasion{" +
                "name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", giftee=" + giftee +
                '}';
    }
}
