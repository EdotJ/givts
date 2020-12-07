package com.givts.app.controller;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.payload.Gift.GiftRequest;
import com.givts.app.payload.Gift.GiftResponse;
import com.givts.app.payload.Gift.SingleGiftResponse;
import com.givts.app.repository.GiftRepository;
import com.givts.app.service.GiftService;
import com.givts.app.service.GifteeService;
import com.givts.app.service.OccasionService;
import com.givts.app.service.StorageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes =
        {OccasionService.class, GiftService.class, GifteeService.class, GiftRepository.class})
public class GiftControllerTest extends ControllerTestBase {
    @Autowired
    GiftService giftService;

    @Autowired
    StorageService storageService;

    @Autowired
    GiftRepository giftRepository;

    GiftController giftController;

    @BeforeEach
    public void setUp() {
        giftController = new GiftController(giftService, storageService);
        super.setUp();
    }

    @Test
    @Override
    public void testInsert() {
        GiftRequest giftRequest = new GiftRequest("TestGift", "This is a test gift");
        ResponseEntity<SingleGiftResponse> giftResponse = giftController.insert(1, 1, 1, giftRequest, null);
        assertEquals(201, giftResponse.getStatusCode().value());
        assertNotNull(giftResponse.getBody());
        assertEquals("TestGift", giftResponse.getBody().getName());
    }

    @Test
    public void testFailedInsert() {
        assertThrows(ResourceNotFoundException.class, () ->
                giftController.insert(
                        404, 1, 1, new GiftRequest("TestGift", "This is a test gift"), null));
    }

    @Test
    public void testGetAll() {
        ResponseEntity<GiftResponse> giftResponse = giftController.getAll(1, 1, 1);
        assertEquals(200, giftResponse.getStatusCode().value());
        assertNotNull(giftResponse.getBody());
        assertTrue(giftResponse.getBody().getGiftCount() > 0);
    }

    @Test
    @Override
    public void testGetSingle() {
        ResponseEntity<SingleGiftResponse> giftResponse = giftController.get(1, 1, 1, 1);
        assertEquals(200, giftResponse.getStatusCode().value());
        assertNotNull(giftResponse.getBody());

        // following data is from data.sql
        assertEquals("Candle", giftResponse.getBody().getName());
    }

    @Test
    @Override
    public void testUpdate() {
        GiftRequest giftRequest = new GiftRequest("TestGift", "This is a test gift");
        ResponseEntity<SingleGiftResponse> giftResponse = giftController.update(1, 1, 1, 1, giftRequest);
        assertEquals(200, giftResponse.getStatusCode().value());
        assertNotNull(giftResponse.getBody());
        assertEquals("TestGift", giftResponse.getBody().getName());
    }

    @Test
    @Override
    public void testDelete() {
        ResponseEntity<Object> responseEntity = giftController.delete(1, 1, 1, 1);
        assertEquals(0, giftRepository.findAll().size());
        assertEquals(204, responseEntity.getStatusCodeValue());
    }
}