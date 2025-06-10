package com.example.nomo.service;

import com.example.nomo.model.User;
import com.example.nomo.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String nickname, String email, String password) {
        if (userRepository.findByUsername(nickname).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already taken");
        }

        User user = new User();
        user.setUsername(nickname);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public List<User> searchUsers(String query, String currentUsername) {
        return userRepository.searchUsers(query)
                .stream()
                .filter(u -> !u.getUsername().equals(currentUsername))
                .toList();
    }
}