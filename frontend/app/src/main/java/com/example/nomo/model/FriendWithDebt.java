package com.example.nomo.model;

public class FriendWithDebt {
    private Friend friend;
    private String amount = "";
    private boolean isSaved = false;
    private String username;
    private Long id;

    public FriendWithDebt(Friend friend) {
        this.friend = friend;
    }

    public Friend getFriend() {
        return friend;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
