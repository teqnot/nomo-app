package com.example.nomo.dto;

import com.example.nomo.model.Room;
import com.example.nomo.model.User;
import com.example.nomo.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

public class RoomDto {
    private Long id;
    private Long creatorId;
    private String name;
    private String description;

    public RoomDto() {}

    public RoomDto(Long id, Long creatorId, String name, String description) {
        this.id = id;
        this.creatorId = creatorId;
        this.name = name;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) {this.id = id; };

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId() {this.creatorId = creatorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Map<String, Object> convertToRoomDto(User user) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> creator = new HashMap<>();

        response.put("id", id);
        response.put("name", name);
        response.put("description", description);

        creator.put("id", user.getId());
        creator.put("username", user.getUsername());
        response.put("creator", creator);
        return response;
    }
}
