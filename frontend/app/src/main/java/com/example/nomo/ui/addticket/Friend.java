package com.example.nomo.ui.addticket;

public class Friend {
    private long id;
    private String username;

    public Friend(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
