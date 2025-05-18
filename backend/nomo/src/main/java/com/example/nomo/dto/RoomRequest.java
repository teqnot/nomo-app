package com.example.nomo.dto;

public class RoomRequest {
    private String name;
    private Long creatorId;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; };

    public Long getCreatorId() { return creatorId; }
    public void setCreatorId() { this.creatorId = creatorId; }
}
