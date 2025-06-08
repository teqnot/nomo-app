package com.example.nomo.model;

public class GetDebtsRequest {
    private Long userId;

    public GetDebtsRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
