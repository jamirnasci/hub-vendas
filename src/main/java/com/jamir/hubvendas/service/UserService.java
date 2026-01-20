package com.jamir.hubvendas.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jamir.hubvendas.model.User;
import com.jamir.hubvendas.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository ur;
    @Autowired
    private PasswordEncoder pe;

    public Optional<User> create(User user) {
        user.setPassword(pe.encode(user.getPassword()));
        return Optional.of(ur.save(user)).map(u -> {
            u.setPassword(null);
            return u;
        });

    }

    public Optional<User> update(Long iduser, User userRequest) {
        return ur.findById(iduser).map(user -> {
            user.setName(userRequest.getName());
            if (userRequest.getPassword() != null && !userRequest.getPassword().isBlank()) {
                user.setPassword(pe.encode(userRequest.getPassword()));
            }
            user.setPhone(userRequest.getPhone());
            user.setEmail(userRequest.getEmail());
            return ur.save(user);
        });
    }

    public Optional<User> details(Long iduser) {
        return ur.findById(iduser);
    }
}
