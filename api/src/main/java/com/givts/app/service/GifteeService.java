package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Giftee;
import com.givts.app.model.User;
import com.givts.app.payload.Giftee.GifteeRequest;
import com.givts.app.repository.GifteeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GifteeService {

    GifteeRepository repository;

    UserService userService;

    public GifteeService(GifteeRepository gifteeRepository, UserService userService) {
        this.repository = gifteeRepository;
        this.userService = userService;
    }

    public Giftee create(long userId, GifteeRequest gifteeRequest) throws ResourceNotFoundException {
        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", String.valueOf(userId));
        }
        Giftee giftee = new Giftee();
        giftee.setName(gifteeRequest.getName());
        giftee.setUser(user);
        giftee.setCreatedDate(LocalDateTime.now());
        return repository.save(giftee);
    }

    public Giftee update(long userId, long id, GifteeRequest gifteeRequest) throws ResourceNotFoundException {
        Giftee giftee = findById(userId, id);
        User user = userService.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", String.valueOf(id));
        }
        giftee.setName(gifteeRequest.getName());
        giftee.setUser(user);
        return repository.save(giftee);
    }

    public void deleteById(long id, long userId) throws ResourceNotFoundException {
        Giftee giftee = findById(userId, id);
        repository.delete(giftee);
    }

    public Giftee findById(long userId, long id) {
        Optional<Giftee> optionalGiftee = repository.findById(id);
        if (optionalGiftee.isEmpty()) {
            throw new ResourceNotFoundException("Giftee", "id", String.valueOf(id));
        }
        if (optionalGiftee.get().getUser().getId() != userId) {
            throw new ResourceNotFoundException("Giftee", "user id", String.valueOf(userId));
        }
        return optionalGiftee.get();
    }

    public List<Giftee> findAllByUserId(long userId) {
        List<Giftee> gifteeList = repository.findByUserId(userId);
        return gifteeList;
    }
}
