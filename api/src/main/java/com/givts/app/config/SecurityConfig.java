package com.givts.app.config;

import com.givts.app.security.CustomOpaqueTokenIntrospector;
import com.givts.app.service.AuthorizationServer;
import com.givts.app.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaquetoken.client-secret}")
    String clientSecret;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests(auth ->
                auth.antMatchers("/users/{userId}/**").access("@webSecurity.checkUserId(principal, #userId) || hasRole('ADMIN')"))
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::opaqueToken)
            .sessionManagement().
                sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public OpaqueTokenIntrospector introspector(UserService userService, AuthorizationServer authServer) {
        return new CustomOpaqueTokenIntrospector(introspectionUri, clientId, clientSecret, userService, authServer);
    }
}
