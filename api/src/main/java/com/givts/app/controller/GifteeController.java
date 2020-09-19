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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/giftees")
public class GifteeController extends BaseCrudController<GifteeRequest, GifteeResponse, SingleGifteeResponse> {

    GifteeService gifteeService;

    public GifteeController(GifteeService gifteeService) {
        this.gifteeService = gifteeService;
    }

    @GetMapping
    @Override
    public ResponseEntity<GifteeResponse> getAll() {
        GifteeResponse gifteeResponse = new GifteeResponse();
        gifteeResponse.setGiftees(
                gifteeService.findAll().stream()
                        .map(SingleGifteeResponse::new)
                        .collect(Collectors.toList()));
        return ResponseEntity.ok().body(gifteeResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SingleGifteeResponse> get(@PathVariable long id) {
        Giftee giftee = gifteeService.findById(id);
        return ResponseEntity.ok().body(new SingleGifteeResponse(giftee));
    }

    @PostMapping
    @Override
    public ResponseEntity<SingleGifteeResponse> insert(@Valid @RequestBody GifteeRequest gifteeRequest) {
        Giftee createdGiftee = gifteeService.create(gifteeRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{gifteeId}")
                .buildAndExpand(createdGiftee.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleGifteeResponse(createdGiftee));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<SingleGifteeResponse> update(@PathVariable long id, @RequestBody @Valid GifteeRequest gifteeRequest) {
        Giftee updatedGiftee = gifteeService.update(id, gifteeRequest);
        return ResponseEntity.ok(new SingleGifteeResponse(updatedGiftee));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> delete(@PathVariable long id) {
        gifteeService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
