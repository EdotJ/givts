package com.givts.app.controller;

import com.givts.app.exception.ResourceNotFoundException;
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
@RequestMapping("/occasions")
public class OccasionController extends BaseCrudController<OccasionRequest, OccasionResponse, SingleOccasionResponse> {

    private OccasionService occasionService;

    public OccasionController(OccasionService occasionService) {
        this.occasionService = occasionService;
    }

    @GetMapping
    @Override
    public ResponseEntity<OccasionResponse> getAll() {
        List<Occasion> occasionList = occasionService.findAll();
        OccasionResponse occasionResponse = new OccasionResponse();
        occasionResponse.setOccasions(occasionList.stream()
                .map(SingleOccasionResponse::new)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(occasionResponse);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<SingleOccasionResponse> get(@PathVariable long id) {
        Occasion occasion = occasionService.findById(id);
        return occasion == null ?
                ResponseEntity.notFound().build()
                : ResponseEntity.ok(new SingleOccasionResponse(occasion));
    }

    @PostMapping
    @Override
    public ResponseEntity<SingleOccasionResponse> insert(@RequestBody @Valid OccasionRequest occasionRequest) {
        Occasion createdOccasion = occasionService.create(occasionRequest);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{occasionId}")
                .buildAndExpand(createdOccasion.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleOccasionResponse(createdOccasion));
    }

    @PutMapping("/{id}")
    @Override
    public ResponseEntity<SingleOccasionResponse> update(
            @PathVariable long id, @RequestBody @Valid OccasionRequest occasionRequest) {
        Occasion updatedOccasion = occasionService.update(id, occasionRequest);
        return ResponseEntity.ok(new SingleOccasionResponse(updatedOccasion));
    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Object> delete(@PathVariable long id) {
        occasionService.deleteById(id);
        return ResponseEntity.status(204).build();
    }
}
