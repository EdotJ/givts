package com.givts.app.payload.Giftee;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

public class GifteeRequest {

    @JsonProperty
    private long id;

    @JsonProperty
    @NotNull (message = "{name.notNull}")
    private String name;

    @JsonProperty("user")
    @NotNull (message = "{giftee.userId.notNull}")
    private Long userId;

    public GifteeRequest(String name, long userId) {
        this.name = name;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
