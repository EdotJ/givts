package com.givts.app.controller;

import com.givts.app.exception.ResourceAlreadyExistsException;
import com.givts.app.payload.User.SingleUserResponse;
import com.givts.app.payload.User.UserRequest;
import com.givts.app.payload.User.UserResponse;
import com.givts.app.repository.UserRepository;
import com.givts.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest extends ControllerTestBase{

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    UserController userController;

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService);
        super.setUp();
    }

    @Override
    @Test
    public void testGetAll() {
        ResponseEntity<UserResponse> userResponse = userController.getAll();
        assertEquals(200, userResponse.getStatusCode().value());
        assertNotNull(userResponse.getBody());
        assertTrue(userResponse.getBody().getUserCount() > 0);
    }

    @Override
    @Test
    public void testGetSingle() {
        ResponseEntity<SingleUserResponse> userResponse = userController.get(1);
        assertEquals(200, userResponse.getStatusCode().value());
        assertNotNull(userResponse.getBody());
        SingleUserResponse singleUserResponse = userResponse.getBody();
        assertEquals("Petras", singleUserResponse.getName());
        assertEquals("Petras@gmail.com", singleUserResponse.getEmail());
    }

    @Override
    @Test
    public void testDelete() {
        ResponseEntity<Object> responseEntity = userController.delete(1);
        assertEquals(0, userRepository.findAll().size());
        assertEquals(204, responseEntity.getStatusCodeValue());
    }

    @Override
    @Test
    public void testUpdate() {
        UserRequest userRequest = new UserRequest(1, "Petras", "Petronis@gmail.com");
        ResponseEntity<SingleUserResponse> responseEntity = userController.update(1, userRequest);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals("Petras", responseEntity.getBody().getName());
        assertEquals("Petronis@gmail.com", responseEntity.getBody().getEmail());
    }

    @Override
    @Test
    public void testInsert() {
        UserRequest userRequest = new UserRequest(2, "Antanas", "Antanas@gmail.com");
        ResponseEntity<SingleUserResponse> responseEntity = userController.insert(userRequest);
        assertEquals(201, responseEntity.getStatusCode().value());
        assertNotNull(responseEntity.getBody());
        assertEquals("Antanas", responseEntity.getBody().getName());
    }

    @Override
    @Test
    public void testFailedInsert() {
        assertThrows(ResourceAlreadyExistsException.class, () ->
                userController.insert(new UserRequest(1, "Petras", "petras@gmail.com")));
    }
}
