package com.example.nomo.dto;

public class DebtRequest {
    private Long debtorId;
    private Long creditorId;
    private Double amount;
    private String description;
    private Long roomId;

    public Long getDebtorId() { return debtorId; }
    public void setDebtorId(Long debtorId) { this.debtorId = debtorId; }

    public Long getCreditorId() { return creditorId; }
    public void setRequestCreditorId(Long creditorId) { this.creditorId = creditorId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getDescription() { return description; };
    public void setRequestDescription() { this.description = description; }

    public Long getRoomId() { return roomId; }
    public void setRequestRoomId(Long roomId) { this.roomId = roomId; }
}
