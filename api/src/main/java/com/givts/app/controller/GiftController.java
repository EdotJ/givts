package com.givts.app.controller;

import com.givts.app.model.Gift;
import com.givts.app.payload.Gift.GiftRequest;
import com.givts.app.payload.Gift.GiftResponse;
import com.givts.app.payload.Gift.SingleGiftResponse;
import com.givts.app.service.GiftService;
import com.givts.app.service.StorageService;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users/{userId}/giftees/{gifteeId}/occasions/{occasionId}/gifts")
public class GiftController extends CrudControllerBase {

    private final StorageService storageService;
    Logger logger = LoggerFactory.getLogger(this.getClass());
    private GiftService giftService;

    public GiftController(GiftService service, StorageService storageService) {
        this.giftService = service;
        this.storageService = storageService;
        this.storageService.init();
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

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<SingleGiftResponse> insert(@PathVariable long userId,
                                                     @PathVariable long gifteeId,
                                                     @PathVariable long occasionId,
                                                     @Valid @RequestPart("data") GiftRequest request,
                                                     @RequestPart(value = "image", required = false) MultipartFile file) {
        String fileName = storageService.tryUploadFile(userId, file);
        Gift createdGift = giftService.create(userId, gifteeId, occasionId, request, fileName);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{giftId}")
                .buildAndExpand(createdGift.getId()).toUri();
        return ResponseEntity.created(location).body(new SingleGiftResponse(createdGift));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SingleGiftResponse> update(@PathVariable long userId, @PathVariable long gifteeId,
                                                     @PathVariable long occasionId, @PathVariable long id,
                                                     @Valid @RequestPart("data") GiftRequest request,
                                                     @RequestPart(value = "image", required = false) MultipartFile file) {
        Gift updatedGift = giftService.update(userId, gifteeId, occasionId, id, request, file);
        return ResponseEntity.ok(new SingleGiftResponse(updatedGift));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long userId, @PathVariable long gifteeId,
                                         @PathVariable long occasionId, @PathVariable long id) {
        giftService.deleteById(userId, gifteeId, occasionId, id);
        return ResponseEntity.status(204).build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public Map<String, Object> handleFileNotFound(FileNotFoundException ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("message", "File not found");
        return error;
    }
}
