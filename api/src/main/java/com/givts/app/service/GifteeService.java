package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.Giftee;
import com.givts.app.model.User;
import com.givts.app.payload.Giftee.GifteeRequest;
import com.givts.app.repository.GifteeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GifteeService extends CrudService<Giftee> {

    UserService userService;

    public GifteeService(GifteeRepository gifteeRepository, UserService userService) {
        super(gifteeRepository);
        this.userService = userService;
    }

    public Giftee create(GifteeRequest gifteeRequest) throws ResourceNotFoundException {
        User user = userService.findById(gifteeRequest.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User", "id");
        }
        Giftee giftee = new Giftee();
        giftee.setName(gifteeRequest.getName());
        giftee.setUser(user);
        giftee.setCreatedDate(LocalDateTime.now());
        return repository.save(giftee);
    }

    public Giftee update(long id, GifteeRequest gifteeRequest) throws ResourceNotFoundException {
        Giftee giftee = findById(id);
        User user = userService.findById(gifteeRequest.getUserId());
        if (user == null) {
            throw new ResourceNotFoundException("User", "id");
        }

        giftee.setName(gifteeRequest.getName());
        giftee.setUser(user);
        return repository.save(giftee);
    }

    public void deleteById(long id) throws ResourceNotFoundException {
        Giftee giftee = findById(id);
        repository.delete(giftee);
    }

    @Override
    public Giftee findById(long id) {
        Optional<Giftee> optionalGiftee = repository.findById(id);
        if (optionalGiftee.isEmpty()) {
            throw new ResourceNotFoundException("Giftee", "id");
        }
        return optionalGiftee.get();
    }
}
