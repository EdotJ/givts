package com.givts.app.controller;

import com.givts.app.model.Gift;
import com.givts.app.payload.Gift.GiftRequest;
import com.givts.app.payload.Gift.GiftResponse;
import com.givts.app.payload.Gift.SingleGiftResponse;
import com.givts.app.service.GiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gifts")
public class GiftController extends BaseCrudController<GiftRequest, GiftResponse, SingleGiftResponse> {

    GiftService giftService;

    public GiftController(GiftService service) {
        this.giftService = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<GiftResponse> getAll() {
        GiftResponse response = new GiftResponse();
        response.setGifts(giftService.findAll().stream().map(SingleGiftResponse::new).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<SingleGiftResponse> get(@PathVariable long id) {
        Gift gift = giftService.getById(id);
        return ResponseEntity.ok(new SingleGiftResponse(gift));
    }

    @Override
    @PostMapping
    public ResponseEntity<SingleGiftResponse> insert(@Valid @RequestBody GiftRequest request) {
        Gift createdGift = giftService.create(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{giftId}")
                .buildAndExpand(createdGift.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleGiftResponse(createdGift));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<SingleGiftResponse> update(@PathVariable long id, @Valid @RequestBody GiftRequest request) {
        Gift updatedGift = giftService.update(id, request);
        return ResponseEntity.ok(new SingleGiftResponse(updatedGift));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id) {
        giftService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
