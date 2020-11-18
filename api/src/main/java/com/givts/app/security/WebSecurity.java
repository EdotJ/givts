package com.givts.app.security;

import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {
    public boolean checkUserId(String principal, String id) {
        return false;
    }

    public boolean checkUserId(DefaultOAuth2User principal, long id) {
        if (principal == null) {
            return false;
        }
        Long userId = principal.getAttrib–ute("user_id");
        if (userId == null) {
            return false;
        }
        return userId == id;
    }
}
