package com.givts.app.controller;

import com.givts.app.model.Giftee;
import com.givts.app.payload.Giftee.GifteeRequest;
import com.givts.app.payload.Giftee.GifteeResponse;
import com.givts.app.payload.Giftee.SingleGifteeResponse;
import com.givts.app.service.GifteeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/giftees")
public class GifteeController extends CrudControllerBase {

    GifteeService gifteeService;

    public GifteeController(GifteeService gifteeService) {
        this.gifteeService = gifteeService;
    }

    @GetMapping
    public ResponseEntity<GifteeResponse> getAll(@PathVariable long userId) {
        List<Giftee> gifteeList = gifteeService.findAllByUserId(userId);
        GifteeResponse gifteeResponse = new GifteeResponse();
        gifteeResponse.setGiftees(gifteeList.stream()
                .map(SingleGifteeResponse::new)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(gifteeResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleGifteeResponse> get(@PathVariable long userId, @PathVariable long id) {
        Giftee giftee = gifteeService.findById(userId, id);
        return ResponseEntity.ok().body(new SingleGifteeResponse(giftee));
    }

    @PostMapping
    public ResponseEntity<SingleGifteeResponse> insert(@PathVariable long userId,
                                                       @Valid @RequestBody GifteeRequest gifteeRequest) {
        Giftee createdGiftee = gifteeService.create(userId, gifteeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{gifteeId}")
                .buildAndExpand(createdGiftee.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleGifteeResponse(createdGiftee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleGifteeResponse> update(@PathVariable long id,
                                                       @PathVariable long userId,
                                                       @RequestBody @Valid GifteeRequest gifteeRequest) {
        Giftee updatedGiftee = gifteeService.update(userId, id, gifteeRequest);
        return ResponseEntity.ok(new SingleGifteeResponse(updatedGiftee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long id, @PathVariable long userId) {
        gifteeService.deleteById(id, userId);
        return ResponseEntity.status(204).build();
    }
}
