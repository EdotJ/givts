package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Gift;
import com.givts.app.model.Occasion;
import com.givts.app.payload.Gift.GiftRequest;
import com.givts.app.repository.GiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GiftService {

    GiftRepository repository;

    OccasionService occasionService;

    public GiftService(GiftRepository giftRepository, OccasionService occasionService) {
        repository = giftRepository;
        this.occasionService = occasionService;
    }

    public Gift create(long userId, long gifteeId, long occasionId, GiftRequest request) {
        Occasion occasion = occasionService.findById(userId, gifteeId, occasionId);
        Gift gift = new Gift();
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setCreatedDate(LocalDateTime.now());
        return repository.save(gift);
    }

    public Gift update(long userId, long gifteeId, long occasionId, long id, GiftRequest request) {
        Gift gift = findById(userId, gifteeId, occasionId, id);
        Occasion occasion = occasionService.findById(userId, gifteeId, occasionId);
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setModifiedDate(LocalDateTime.now());
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
        repository.delete(gift);
    }

    public List<Gift> findAll(long userId, long gifteeId, long occasionId) {
        occasionService.findById(userId, gifteeId, occasionId);
        return repository.findAllByOccasionIdAndGifteeIdAndUserId(occasionId, gifteeId, userId);
    }
}
