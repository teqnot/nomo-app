package com.example.nomo.dto;

public class RegisterRequest {
    private String nickname;
    private String email;
    private String password;

    // Геттеры и сеттеры
    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
