package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.User;
import com.givts.app.payload.User.UserRequest;
import com.givts.app.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository userRepository) {
        repository = userRepository;
    }

    public User create(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setCreatedDate(LocalDateTime.now());
        return repository.save(user);
    }

    public User update(long id, UserRequest userRequest) {
        User user = findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", String.valueOf(id));
        }
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        return repository.save(user);
    }

    public void deleteById(long id) {
        User user = findById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", String.valueOf(id));
        }
        repository.delete(user);
    }

    public User findById(long id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.orElse(null);
    }

    public User findByEmail(String email) {
        List<User> users = repository.findByEmail(email);
        return users.size() > 0 ? users.get(0) : null;
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
