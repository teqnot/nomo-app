package com.example.nomo.model;

public class Friend {
    private String username;
    private long id;
    private boolean isSelected;
    private boolean isSaved;
    private String amount = "";

    public Friend(String username, long id) {
        this.username = username;
        this.id = id;
        this.isSelected = false;
        this.isSaved = false;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
