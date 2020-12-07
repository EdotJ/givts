package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.givts.app.model.Giftee;

import java.time.LocalDateTime;

public class SingleGifteeResponse {

    private Long id;

    private String name;

    private String likes;

    private String dislikes;

    private String hobbies;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    public SingleGifteeResponse(Giftee giftee) {
        this.id = giftee.getId();
        this.name = giftee.getName();
        this.createdDate = giftee.getCreatedDate();
        this.likes = giftee.getLikes();
        this.dislikes = giftee.getDislikes();
        this.hobbies = giftee.getHobbies();
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
