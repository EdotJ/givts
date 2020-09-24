package com.givts.app.controller;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.payload.Occasion.*;
import com.givts.app.repository.OccasionRepository;
import com.givts.app.service.GifteeService;
import com.givts.app.service.OccasionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {GifteeService.class, OccasionService.class, OccasionRepository.class})
public class OccasionControllerTest extends ControllerTestBase {
    @Autowired
    OccasionService occasionService;

    @Autowired
    OccasionRepository occasionRepository;

    OccasionController occasionController;

    @BeforeEach
    public void setUp() {
        occasionController = new OccasionController(occasionService);
        super.setUp();
    }

    @Test
    @Override
    public void testInsert() {
        OccasionRequest occasionRequest = new OccasionRequest("TestOccasion", LocalDate.parse("2020-09-30"));
        ResponseEntity<SingleOccasionResponse> occasionResponse =
                occasionController.insert(1, 1, occasionRequest);
        assertEquals(201, occasionResponse.getStatusCode().value());
        assertNotNull(occasionResponse.getBody());
        assertEquals("TestOccasion", occasionResponse.getBody().getName());
    }

    @Test
    public void testFailedInsert() {
        assertThrows(ResourceNotFoundException.class, () ->
                occasionController.insert(404, 404,
                        new OccasionRequest("Tester", LocalDate.parse("2020-09-30"))));
    }

    @Test
    public void testGetAll() {
        ResponseEntity<OccasionResponse> occasionResponse = occasionController.getAll(1, 1);
        assertEquals(200, occasionResponse.getStatusCode().value());
        assertNotNull(occasionResponse.getBody());
        assertTrue(occasionResponse.getBody().getOccasionCount() > 0);
    }

    @Test
    @Override
    public void testGetSingle() {
        ResponseEntity<SingleOccasionResponse> occasionResponse = occasionController.get(1, 1, 1);
        assertEquals(200, occasionResponse.getStatusCode().value());
        assertNotNull(occasionResponse.getBody());

        // following data is from data.sql
        assertEquals("Bernvakaris", occasionResponse.getBody().getName());
    }

    @Test
    @Override
    public void testUpdate() {
        OccasionRequest occasionRequest = new OccasionRequest("Occasion", LocalDate.parse("2020-09-30"));
        ResponseEntity<SingleOccasionResponse> occasionResponse = occasionController.update(1, 1,1, occasionRequest);
        assertEquals(200, occasionResponse.getStatusCode().value());
        assertNotNull(occasionResponse.getBody());
        assertEquals("Occasion", occasionResponse.getBody().getName());
    }

    @Test
    @Override
    public void testDelete() {
        ResponseEntity<Object> responseEntity = occasionController.delete(1, 1, 1);
        assertEquals(0, occasionRepository.findAll().size());
        assertEquals(204, responseEntity.getStatusCodeValue());
    }
}
