package com.example.nomo.model;

public class UserDto {
    private String username;
    private Long id;

    public UserDto(String username, Long id) {
        this.username = username;
        this.id = id;
    }

    // Геттеры
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }
}
