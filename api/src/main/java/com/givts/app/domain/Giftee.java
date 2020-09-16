package com.givts.app.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Giftee implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private LocalDate createdDate;
    private User user;

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

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Giftee{" +
                ", name='" + name + '\'' +
                ", createdDate=" + createdDate +
                ", user=" + user +
                '}';
    }
}
