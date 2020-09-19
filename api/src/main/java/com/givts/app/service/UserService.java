package com.givts.app.service;

import com.givts.app.model.User;
import com.givts.app.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CrudService<User>{

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }

}
