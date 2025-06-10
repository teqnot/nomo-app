package com.example.nomo.model;

public class FriendshipRequest {
    private long userId;
    private long fromId;
    private long toId;

    public FriendshipRequest(long userId) {
        this.userId = userId;
    }

    public FriendshipRequest(long fromId, long toId) {
        this.fromId = fromId;
        this.toId = toId;
    }

    public long getUserId() { return userId; }

    public long getFromId() { return fromId; }

    public long getToId() { return toId; }
}
