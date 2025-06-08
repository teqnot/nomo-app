package com.example.nomo.controller;

import com.example.nomo.dto.LoginRequest;
import com.example.nomo.dto.RegisterRequest;
import com.example.nomo.model.User;
import com.example.nomo.repository.UserRepository;
import com.example.nomo.service.CustomUserDetailsService;
import com.example.nomo.service.UserService;
import com.example.nomo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNickname(), request.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getNickname());
        final String jwt = jwtUtil.generateToken(userDetails);
        User user = userRepository.findByUsername(request.getNickname())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + request.getNickname()));

        Map<String, Object> response = new HashMap<>();
        response.put("access_token", jwt);
        response.put("token_type", "Bearer");
        response.put("expires_in", jwtUtil.extractExpiration(jwt).getTime() - System.currentTimeMillis());
        response.put("user_id", user.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User user = userService.register(
                    request.getNickname(),
                    request.getEmail(),
                    request.getPassword()
            );

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getUsername());

            String jwt = jwtUtil.generateToken(userDetails);
            Map<String, Object> response = new HashMap<>();

            response.put("access_token", jwt);
            response.put("user_id", user.getId());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

