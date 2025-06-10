package com.example.nomo.model;

public class FriendshipDto {
    private long id;
    private String username;

    public FriendshipDto(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() { return id; }
    public String getUsername() { return username; }
}
