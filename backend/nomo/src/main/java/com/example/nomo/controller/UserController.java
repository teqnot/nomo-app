package com.example.nomo.controller;

import com.example.nomo.dto.UserDto;
import com.example.nomo.model.User;
import com.example.nomo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Поиск пользователей по никнейму или email
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(@RequestParam String query,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        List<User> users = userService.searchUsers(query, userDetails.getUsername());
        List<UserDto> userDtos = users.stream()
                .map(UserDto::new)
                .toList();
        return ResponseEntity.ok(userDtos);
    }
}
