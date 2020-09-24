package com.givts.app.payload.User;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserResponse {

    private List<SingleUserResponse> users;

    @JsonProperty("count")
    public int getUserCount() {
        return users.size();
    }

    public List<SingleUserResponse> getUsers() {
        return users;
    }

    public void setUsers(List<SingleUserResponse> users) {
        this.users = users;
    }
}
