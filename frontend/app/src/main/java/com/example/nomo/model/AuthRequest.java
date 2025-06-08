package com.example.nomo.model;

public class AuthRequest {
    private String nickname;
    private String email;
    private String password;

    public AuthRequest(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
