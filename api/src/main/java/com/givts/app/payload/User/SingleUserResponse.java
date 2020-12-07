package com.givts.app.payload.User;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SingleUserResponse {

    private Long id;

    private String name;

    private String email;

    private String username;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    private String role;

    public SingleUserResponse(Long id, String name, String email, LocalDateTime createdDate, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.role = role;
    }

    public SingleUserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdDate = user.getCreatedDate();
        this.username = user.getUsername();
        this.role = user.getRole().getName();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
