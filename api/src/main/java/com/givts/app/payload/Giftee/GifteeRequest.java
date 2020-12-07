package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class GifteeRequest {

    @JsonProperty
    @NotNull (message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    private String likes;

    private String dislikes;

    private String hobbies;

    public GifteeRequest() {

    }

    public GifteeRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
