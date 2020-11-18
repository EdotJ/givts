package com.givts.app.payload.User;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfoResponse {

    private String username;
    private String email;

    @JsonProperty("oauth_id")
    private Long oauthId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOauthId() {
        return oauthId;
    }

    public void setOauthId(Long oauthId) {
        this.oauthId = oauthId;
    }
}
