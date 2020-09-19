package com.givts.app.controller;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.payload.Giftee.GifteeRequest;
import com.givts.app.payload.Giftee.GifteeResponse;
import com.givts.app.payload.Giftee.SingleGifteeResponse;
import com.givts.app.repository.GifteeRepository;
import com.givts.app.service.GifteeService;
import com.givts.app.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {UserService.class, GifteeService.class, GifteeRepository.class})
public class GifteeControllerTest extends ControllerTestBase {

    @Autowired
    GifteeService gifteeService;

    @Autowired
    GifteeRepository gifteeRepository;

    GifteeController gifteeController;

    @BeforeEach
    public void setUp() {
        gifteeController = new GifteeController(gifteeService);
        super.setUp();
    }

    @Test
    @Override
    public void testInsert() {
        ResponseEntity<SingleGifteeResponse> gifteeResponse =
                gifteeController.insert(new GifteeRequest("Tester", 1));
        assertEquals(201, gifteeResponse.getStatusCode().value());
        assertNotNull(gifteeResponse.getBody());
        assertEquals("Tester", gifteeResponse.getBody().getName());
        assertEquals(1, gifteeResponse.getBody().getUserResponse().getId());
    }

    @Test
    public void testFailedInsert() {
        assertThrows(ResourceNotFoundException.class, () ->
                gifteeController.insert(new GifteeRequest("Tester", 404)));
    }

    @Test
    public void testGetAll() {
        ResponseEntity<GifteeResponse> gifteeResponse = gifteeController.getAll();
        assertEquals(200, gifteeResponse.getStatusCode().value());
        assertNotNull(gifteeResponse.getBody());
        assertTrue(gifteeResponse.getBody().getGifteeCount() > 0);
    }

    @Test
    @Override
    public void testGetSingle() {
        ResponseEntity<SingleGifteeResponse> gifteeResponse = gifteeController.get(1);
        assertEquals(200, gifteeResponse.getStatusCode().value());
        assertNotNull(gifteeResponse.getBody());

        // following data is from data.sql
        assertEquals("Antanas", gifteeResponse.getBody().getName());
        assertEquals(1, gifteeResponse.getBody().getUserResponse().getId());
    }

    @Test
    @Override
    public void testUpdate() {
        GifteeRequest gifteeRequest = new GifteeRequest("Zenka", 1);
        ResponseEntity<SingleGifteeResponse> gifteeResponse = gifteeController.update(1, gifteeRequest);
        assertEquals(200, gifteeResponse.getStatusCode().value());
        assertNotNull(gifteeResponse.getBody());
        assertEquals("Zenka", gifteeResponse.getBody().getName());
    }

    @Test
    @Override
    public void testDelete() {
        ResponseEntity<Object> responseEntity = gifteeController.delete(1);
        assertEquals(0, gifteeRepository.findAll().size());
        assertEquals(204, responseEntity.getStatusCodeValue());
    }

}
