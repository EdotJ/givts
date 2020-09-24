package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Giftee;
import com.givts.app.model.Occasion;
import com.givts.app.payload.Occasion.OccasionRequest;
import com.givts.app.repository.OccasionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OccasionService {

    OccasionRepository repository;

    GifteeService gifteeService;

    public OccasionService(OccasionRepository occasionRepository, GifteeService gifteeService) {
       repository = occasionRepository;
       this.gifteeService = gifteeService;
    }

    public Occasion create(long userId, long gifteeId, OccasionRequest request) {
        Giftee giftee = gifteeService.findById(userId, gifteeId);
        Occasion occasion = new Occasion();
        occasion.setName(request.getName());
        occasion.setDate(request.getDate());
        occasion.setGiftee(giftee);
        occasion.setCreatedDate(LocalDateTime.now());
        return  repository.save(occasion);
    }

    public Occasion update(long userId, long gifteeId, long id, OccasionRequest request) {
        Occasion occasion = findById(userId, gifteeId, id);
        Giftee giftee = gifteeService.findById(userId, gifteeId);

        occasion.setName(request.getName());
        occasion.setDate(request.getDate());
        occasion.setGiftee(giftee);
        occasion.setModifiedDate(LocalDateTime.now());
        return repository.save(occasion);
    }

    public void deleteById(long userId, long gifteeId, long id) {
        Occasion occasion = findById(userId, gifteeId, id);
        repository.delete(occasion);
    }

    public Occasion findById(long userId, long gifteeId, long id) {
        gifteeService.findById(userId, gifteeId);
        Optional<Occasion> occasion = repository.findById(id);
        if (occasion.isEmpty()) {
            throw new ResourceNotFoundException("Occasion", "id", String.valueOf(id));
        }
        if (occasion.get().getGiftee().getId() != gifteeId) {
            throw new ResourceNotFoundException("Occasion", "giftee id", String.valueOf(gifteeId));
        }
        return occasion.get();
    }

    public List<Occasion> findAll(long userId, long gifteeId) {
        gifteeService.findById(userId, gifteeId);
        return repository.findAllByGifteeIdAndUserId(gifteeId, userId);
    }
}
