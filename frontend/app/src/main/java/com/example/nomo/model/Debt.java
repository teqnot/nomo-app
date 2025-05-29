package com.example.nomo.model;

public class Debt {
    private String name;
    private String amount;
    private boolean isOwedToMe;
    private boolean isOpened;

    public Debt(String name, String amount, boolean isOwedToMe) {
        this.name = name;
        this.amount = amount;
        this.isOwedToMe = isOwedToMe;
        this.isOpened = false;
    }

    public String getName() { return name; }
    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }
    public boolean isOwedToMe() { return isOwedToMe; }
    public boolean isOpened() { return isOpened; }
    public void setOpened(boolean opened) { isOpened = opened; }
}