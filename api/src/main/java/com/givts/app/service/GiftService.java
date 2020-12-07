package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Gift;
import com.givts.app.model.Occasion;
import com.givts.app.payload.Gift.GiftRequest;
import com.givts.app.repository.GiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.NoSuchFileException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftService {

    private GiftRepository repository;

    private OccasionService occasionService;

    private StorageService storageService;

    public GiftService(GiftRepository giftRepository, OccasionService occasionService, StorageService storageService) {
        repository = giftRepository;
        this.occasionService = occasionService;
        this.storageService = storageService;
    }

    public Gift create(long userId, long gifteeId, long occasionId, GiftRequest request, String fileName) {
        Occasion occasion = occasionService.findById(userId, gifteeId, occasionId);
        Gift gift = new Gift();
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setCreatedDate(LocalDateTime.now());
        gift.setPrice(request.getPrice());
        if (fileName != null) {
            gift.setImagePath(fileName);
        }
        return repository.save(gift);
    }

    public Gift update(long userId, long gifteeId, long occasionId, long id, GiftRequest request, MultipartFile file) {
        Gift gift = findById(userId, gifteeId, occasionId, id);
        Occasion occasion = occasionService.findById(userId, gifteeId, occasionId);
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setModifiedDate(LocalDateTime.now());
        gift.setPrice(request.getPrice());
        String fileName = storageService.tryUploadFile(userId, file);
        if (fileName != null && gift.getImagePath() != null) {
            storageService.delete(userId + "/" + gift.getImagePath());
        }
        if (fileName != null) {
            gift.setImagePath(fileName);
        }
        return repository.save(gift);
    }

    public Gift findById(long userId, long gifteeId, long occasionId, long id) {
        occasionService.findById(userId, gifteeId, occasionId);
        Optional<Gift> optionalGift = repository.findById(id);
        if (optionalGift.isEmpty()) {
            throw new ResourceNotFoundException("Gift", "id", String.valueOf(id));
        }
        if (optionalGift.get().getOccasion().getId() != occasionId) {
            throw new ResourceNotFoundException("Gift", "occasion id", String.valueOf(occasionId));
        }
        return optionalGift.get();
    }

    public void deleteById(long userId, long gifteeId, long occasionId, long id) {
        Gift gift = findById(userId, gifteeId, occasionId, id);
        if (gift.getImagePath() != null) {
            storageService.delete(userId + "/" + gift.getImagePath());
        }
        repository.delete(gift);
    }

    public List<Gift> findAll(long userId, long gifteeId, long occasionId) {
        occasionService.findById(userId, gifteeId, occasionId);
        return repository.findAllByOccasionIdAndGifteeIdAndUserId(occasionId, gifteeId, userId);
    }
}
