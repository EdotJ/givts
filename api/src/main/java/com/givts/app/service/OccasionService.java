package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Giftee;
import com.givts.app.model.Occasion;
import com.givts.app.payload.Occasion.OccasionRequest;
import com.givts.app.repository.OccasionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OccasionService extends CrudService<Occasion> {

    GifteeService gifteeService;

    public OccasionService(OccasionRepository occasionRepository, GifteeService gifteeService) {
       super(occasionRepository);
       this.gifteeService = gifteeService;
    }

    public Occasion create(OccasionRequest request) {
        Giftee giftee = gifteeService.findById(request.getGifteeId());
        Occasion occasion = new Occasion();
        occasion.setName(request.getName());
        occasion.setDate(occasion.getDate());
        occasion.setGiftee(giftee);
        occasion.setCreatedDate(LocalDateTime.now());
        return  repository.save(occasion);
    }

    public Occasion update(long id, OccasionRequest request) {
        Occasion occasion = findById(id);
        Giftee giftee = gifteeService.findById(request.getGifteeId());

        occasion.setName(request.getName());
        occasion.setDate(request.getDate());
        occasion.setGiftee(giftee);
        occasion.setModifiedDate(LocalDateTime.now());
        return repository.save(occasion);
    }

    public void deleteById(long id) {
        Occasion occasion = findById(id);
        repository.delete(occasion);
    }

    public Occasion findById(long id) {
        Optional<Occasion> occasion = repository.findById(id);
        if (occasion.isEmpty()) {
            throw new ResourceNotFoundException("Occasion", "id");
        }
        return occasion.get();
    }
}
