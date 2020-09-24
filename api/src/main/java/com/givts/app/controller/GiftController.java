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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/giftees/{gifteeId}/occasions/{occasionId}/gifts")
public class GiftController extends CrudControllerBase {

    GiftService giftService;

    public GiftController(GiftService service) {
        this.giftService = service;
    }

    @GetMapping
    public ResponseEntity<GiftResponse> getAll(@PathVariable long userId, @PathVariable long gifteeId,
                                               @PathVariable long occasionId) {
        GiftResponse response = new GiftResponse();
        List<Gift> giftList = giftService.findAll(userId, gifteeId, occasionId);
        response.setGifts(giftList.stream().map(SingleGiftResponse::new).collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleGiftResponse> get(@PathVariable long userId, @PathVariable long gifteeId,
                                                  @PathVariable long occasionId, @PathVariable long id) {
        Gift gift = giftService.findById(userId, gifteeId, occasionId, id);
        return ResponseEntity.ok(new SingleGiftResponse(gift));
    }

    @PostMapping
    public ResponseEntity<SingleGiftResponse> insert(@PathVariable long userId,
                                                     @PathVariable long gifteeId,
                                                     @PathVariable long occasionId,
                                                     @Valid @RequestBody GiftRequest request) {
        Gift createdGift = giftService.create(userId, gifteeId, occasionId, request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{giftId}")
                .buildAndExpand(createdGift.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleGiftResponse(createdGift));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleGiftResponse> update(@PathVariable long userId, @PathVariable long gifteeId,
                                                     @PathVariable long occasionId, @PathVariable long id,
                                                     @Valid @RequestBody GiftRequest request) {
        Gift updatedGift = giftService.update(userId, gifteeId, occasionId, id, request);
        return ResponseEntity.ok(new SingleGiftResponse(updatedGift));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long userId, @PathVariable long gifteeId,
                                         @PathVariable long occasionId, @PathVariable long id) {
        giftService.deleteById(userId, gifteeId, occasionId, id);
        return ResponseEntity.status(204).build();
    }
}
