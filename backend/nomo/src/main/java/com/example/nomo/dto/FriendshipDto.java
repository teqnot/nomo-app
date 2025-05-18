package com.example.nomo.dto;

import java.util.HashMap;
import java.util.Map;

public class FriendshipDto {
    private Long id;;
    private String username;

    public FriendshipDto() {}

    public FriendshipDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Map<String, Object> convertToFriendshipDto() {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("username", username);
        return response;
    }
}
