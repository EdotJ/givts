package com.givts.app.controller;

import com.givts.app.exception.ResourceAlreadyExistsException;
import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.User;
import com.givts.app.payload.User.UserRequest;
import com.givts.app.payload.User.SingleUserResponse;
import com.givts.app.payload.User.UserResponse;
import com.givts.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.stream.Collectors;

@RestController
public class UserController extends CrudControllerBase {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SingleUserResponse> getCurrentUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal.getAttribute("user_id") == null) {
            throw new AccessDeniedException("Access is denied");
        }
        return get(principal.getAttribute("user_id"));
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getAll() {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsers(userService.findAll().stream()
                .map(SingleUserResponse::new)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("@webSecurity.checkUserId(principal, #id) or hasRole('ADMIN')")
    public ResponseEntity<SingleUserResponse> get(@PathVariable long id) {
        User user = userService.findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", String.valueOf(id));
        }
        return ResponseEntity.ok(new SingleUserResponse(user));
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SingleUserResponse> insert(@Valid @RequestBody UserRequest request) {
        User user = userService.findById(request.getId());
        if (user != null) {
            throw new ResourceAlreadyExistsException("User", "id", String.valueOf(request.getId()));
        }
        if (userService.findByEmail(request.getEmail()) != null) {
            throw new ResourceAlreadyExistsException("User", "email", String.valueOf(request.getEmail()));
        }
        user = userService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleUserResponse(user));
    }

    @PutMapping("/users/{id}")
    @PreAuthorize("@webSecurity.checkUserId(principal, #id)")
    public ResponseEntity<SingleUserResponse> update(@PathVariable long id, @Valid @RequestBody UserRequest request) {
        User updatedUser = userService.update(id, request);
        return ResponseEntity.ok(new SingleUserResponse(updatedUser));
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or @webSecurity.checkUserId(principal, #id)")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.status(204).build();
    }

}
