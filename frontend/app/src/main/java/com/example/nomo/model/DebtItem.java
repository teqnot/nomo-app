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

    private boolean isOpened;

    public boolean isOwedToMe(Long userId) {
        return creditorId.equals(userId);
    }

    public String getDisplayText(Long userId) {
        return name + " - " + (isOwedToMe(userId) ? debtorUsername : creditorUsername);
    }

    public Long getId() { return id; }

    public Long getCreditorId() { return creditorId; }

    public String getCreditorUsername() { return creditorUsername; }

    public Long getDebtorId() { return debtorId; }

    public String getDebtorUsername() { return debtorUsername; }

    public String getAmount() { return Double.toString(amount); }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public boolean getIsOpened() { return isOpened; }
    public void setIsOpened(boolean isOpened) { this.isOpened = isOpened; }

}
