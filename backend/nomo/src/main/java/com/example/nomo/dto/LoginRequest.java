package com.example.nomo.dto;

public class LoginRequest {
    private String nickname;
    private String password;

    public String getNickname() { return nickname; }
    public void setEmail(String nickname) { this.nickname = nickname; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
