package com.givts.app.service;

import com.givts.app.exception.ResourceNotFoundException;
import com.givts.app.model.User;
import com.givts.app.payload.User.UserInfoResponse;
import com.givts.app.payload.User.UserRequest;
import com.givts.app.repository.RoleRepository;
import com.givts.app.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        repository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User create(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        user.setCreatedDate(LocalDateTime.now());
        return repository.saveAndFlush(user);
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
        if (!("ROLE_ADMIN").equals(user.getRole().getName())) {
            repository.delete(user);
        }
    }

    public User findById(long id) {
        Optional<User> userOptional = repository.findById(id);
        return userOptional.orElse(null);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ", " ", true, true, true, true,
                    List.of(new SimpleGrantedAuthority("ROLE_USER")));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), "", user.isEnabled(), true, true,
                true, List.of(new SimpleGrantedAuthority(user.getRole().getName())));
    }

    public User registerIfNotExists(UserInfoResponse userInfoResponse) {
        User user = repository.findByOauthId(userInfoResponse.getOauthId());
        if (user == null) {
            user = new User();
            user.setOauthId(userInfoResponse.getOauthId());
            user.setEmail(userInfoResponse.getEmail());
            user.setUsername(userInfoResponse.getUsername());
            user.setCreatedDate(LocalDateTime.now());
            user.setName("");
            user.setRole(roleRepository.findByName("ROLE_USER"));
            user.setEnabled(true);
            repository.save(user);
        }
        return user;
    }
}
