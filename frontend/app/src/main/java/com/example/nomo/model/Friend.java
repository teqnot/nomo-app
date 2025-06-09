package com.example.nomo.model;

public class Friend {
    private String username;
    private boolean isSelected;
    private boolean isSaved;
    private String amount = "";

    public Friend(String username) {
        this.username = username;
        this.isSelected = false;
        this.isSaved = false;
    }

    public String getUsername() {
        return username;
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
