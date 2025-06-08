package com.example.nomo.model;

public class DebtItem {
    private Long id;
    private Long creditorId;
    private String creditorUsername;
    private Long debtorId;
    private String debtorUsername;
    private Double amount;
    private String name;
    private String description;
    private boolean isPaid;
    private String createdAt;
    private Long roomId;

    public boolean isOwedToMe(Long userId) {
        return creditorId.equals(userId);
    }

    public String getDisplayText(Long userId) {
        return name + " - " + (isOwedToMe(userId) ? debtorUsername : creditorUsername);
    }

    public String getAmount() {
        return Double.toString(amount);
    }
}
