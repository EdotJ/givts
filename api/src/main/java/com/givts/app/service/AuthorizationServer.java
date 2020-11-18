package com.givts.app.service;

import com.givts.app.payload.User.UserInfoRequest;
import com.givts.app.payload.User.UserInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class AuthorizationServer {

    @Value("${user.info.endpoint}")
    private String userInfoEndpoint;

    private final RestTemplate restTemplate;

    public AuthorizationServer() {
        this.restTemplate = new RestTemplateBuilder().build();
    }

    public AuthorizationServer(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public UserInfoResponse getUserInfo(String token) {
        String url = userInfoEndpoint;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        UserInfoRequest userInfoRequest = new UserInfoRequest();
        userInfoRequest.setToken(token);
        HttpEntity<UserInfoRequest> entity = new HttpEntity<>(userInfoRequest, headers);

        ResponseEntity<UserInfoResponse> response =
                this.restTemplate.postForEntity(url, entity, UserInfoResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            return null;
        }
    }
}
