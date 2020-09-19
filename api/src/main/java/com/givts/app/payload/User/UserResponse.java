package com.givts.app.payload.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;

    private String name;

    private String email;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    public UserResponse(Long id, String name, String email, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
