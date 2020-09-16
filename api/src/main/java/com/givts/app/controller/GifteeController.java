package com.givts.app.controller;

import com.givts.app.domain.Giftee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/giftees")
public class GifteeController {

    @GetMapping
    public ResponseEntity<List<Giftee>> getGiftees() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Giftee> getGiftee(@PathVariable long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Giftee> createGiftee(@RequestBody Giftee giftee) {
        return null;
    }

    @PutMapping
    public ResponseEntity<Giftee> updateGiftee() {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Giftee> deleteGiftee() {
        return null;
    }
}
