package com.givts.app.security;

import com.givts.app.model.User;
import com.givts.app.payload.User.UserInfoResponse;
import com.givts.app.service.AuthorizationServer;
import com.givts.app.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.server.resource.introspection.NimbusOpaqueTokenIntrospector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomOpaqueTokenIntrospector extends NimbusOpaqueTokenIntrospector {

    private final AuthorizationServer authServer;

    private final UserService userService;

    public CustomOpaqueTokenIntrospector(String introspectionUri, String clientId, String clientSecret,
                                         UserService userService, AuthorizationServer authServer) {
        super(introspectionUri, clientId, clientSecret);
        this.authServer = authServer;
        this.userService = userService;
    }

    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2AuthenticatedPrincipal principal = super.introspect(token);
        UserInfoResponse userInfoResponse = authServer.getUserInfo(token);
        User user = userService.registerIfNotExists(userInfoResponse);
        Map<String, Object> attributes = extractAttributes(principal, userInfoResponse);
        attributes.put("user_id", user.getId());
        return new DefaultOAuth2User(extractUserAuthorities(user), attributes, "email");
    }

    private List<GrantedAuthority> extractUserAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    private Map<String, Object> extractAttributes(OAuth2AuthenticatedPrincipal principal,
                                                  UserInfoResponse userInfoResponse) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.putAll(principal.getAttributes());
        attributes.putAll(getAttributesFromUserInfo(userInfoResponse));
        return attributes;
    }

    private Map<? extends String, ?> getAttributesFromUserInfo(UserInfoResponse userInfoResponse) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("username", userInfoResponse.getUsername());
        attributes.put("email", userInfoResponse.getEmail());
        attributes.put("oauthId", userInfoResponse.getOauthId());
        return attributes;
    }
}
