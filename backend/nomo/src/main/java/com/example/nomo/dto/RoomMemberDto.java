package com.example.nomo.dto;

import java.util.HashMap;
import java.util.Map;

public class RoomMemberDto {
    private Long id;
    private String username;

    public RoomMemberDto() {}

    public RoomMemberDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Map<String, Object> convertToRoomMemberDto() {
        Map<String, Object> response = new HashMap<>();
        response.put("id", id);
        response.put("username", username);
        return response;
    }
}
