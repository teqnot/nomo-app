package com.example.nomo.config;

import com.example.nomo.model.User;
import com.example.nomo.repository.DebtRepository;
import com.example.nomo.repository.FriendshipRepository;
import com.example.nomo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    @Transactional
    public CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                System.out.println("Creating test users...");

                User alice = new User("alice", "alice@example.com", "password123");
                User bob = new User("bob", "bob@example.com", "password123");
                User charlie = new User("charlie", "charlie@example.com", "password123");
                User diana = new User("diana", "diana@example.com", "password123");
                User eve = new User("eve", "eve@example.com", "password123");

                userRepository.saveAll(List.of(alice, bob, charlie, diana, eve));

                System.out.println("Test users created.");
            } else {
                System.out.println("Database already contains users - skipping initialization...");
            }
        };
    }
}
