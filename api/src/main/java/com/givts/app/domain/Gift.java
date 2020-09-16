package com.givts.app.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String description;
    private LocalDate createdDate;
    private LocalDate modifiedDate;
    private Occasion occasion;

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", occasion=" + occasion +
                '}';
    }
}
