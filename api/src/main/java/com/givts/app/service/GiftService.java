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
public class GiftService extends CrudService<Gift> {

    OccasionService occasionService;

    public GiftService(GiftRepository repository, OccasionService occasionService) {
        super(repository);
        this.occasionService = occasionService;
    }

    public Gift create(GiftRequest request) {
        Occasion occasion = occasionService.findById(request.getOccasionId());
        Gift gift = new Gift();
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setCreatedDate(LocalDateTime.now());
        return repository.save(gift);
    }

    public Gift update(long id, GiftRequest request) {
        Gift gift = getById(id);
        Occasion occasion = occasionService.findById(request.getOccasionId());
        gift.setName(request.getName());
        gift.setDescription(request.getDescription());
        gift.setOccasion(occasion);
        gift.setModifiedDate(LocalDateTime.now());
        return repository.save(gift);
    }

    public Gift getById(long id) {
        Optional<Gift> optionalGift = repository.findById(id);
        if (optionalGift.isEmpty()) {
            throw new ResourceNotFoundException("Gift", "id");
        }
        return optionalGift.get();
    }

    public void deleteById(long id) {
        Gift gift = getById(id);
        repository.delete(gift);
    }
}
