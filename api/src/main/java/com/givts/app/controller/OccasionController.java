package com.givts.app.controller;

import com.givts.app.model.Occasion;
import com.givts.app.payload.Occasion.OccasionRequest;
import com.givts.app.payload.Occasion.OccasionResponse;
import com.givts.app.payload.Occasion.SingleOccasionResponse;
import com.givts.app.service.OccasionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/giftees/{gifteeId}/occasions")
public class OccasionController extends CrudControllerBase {

    private OccasionService occasionService;

    public OccasionController(OccasionService occasionService) {
        this.occasionService = occasionService;
    }

    @GetMapping
    public ResponseEntity<OccasionResponse> getAll(@PathVariable long userId, @PathVariable long gifteeId) {
        List<Occasion> occasionList = occasionService.findAll(userId, gifteeId);
        OccasionResponse occasionResponse = new OccasionResponse();
        occasionResponse.setOccasions(occasionList.stream()
                .map(SingleOccasionResponse::new)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(occasionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleOccasionResponse> get(@PathVariable long userId, @PathVariable long gifteeId,
                                                      @PathVariable long id) {
        Occasion occasion = occasionService.findById(userId, gifteeId, id);
        return occasion == null ?
                ResponseEntity.notFound().build()
                : ResponseEntity.ok(new SingleOccasionResponse(occasion));
    }

    @PostMapping
    public ResponseEntity<SingleOccasionResponse> insert(@PathVariable long userId, @PathVariable long gifteeId,
                                                         @RequestBody @Valid OccasionRequest occasionRequest) {
        Occasion createdOccasion = occasionService.create(userId, gifteeId, occasionRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{occasionId}")
                .buildAndExpand(createdOccasion.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleOccasionResponse(createdOccasion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleOccasionResponse> update( @PathVariable long userId,
                                                          @PathVariable long gifteeId,
                                                          @PathVariable long id,
                                                          @RequestBody @Valid OccasionRequest occasionRequest) {
        Occasion updatedOccasion = occasionService.update(userId, gifteeId, id, occasionRequest);
        return ResponseEntity.ok(new SingleOccasionResponse(updatedOccasion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long userId, @PathVariable long gifteeId,
                                         @PathVariable long id) {
        occasionService.deleteById(userId, gifteeId, id);
        return ResponseEntity.status(204).build();
    }
}
