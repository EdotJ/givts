package com.givts.app.payload.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRequest {
    private long id;

    @NotNull(message = "{name.notNull}")
    @Size(min = 1, message = "{name.notEmpty}")
    private String name;

    @NotNull(message = "{user.email.notNull}")
    @Email(message = "{user.email.valid}")
    private String email;

    public UserRequest(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
