package com.example.nomo.dto;

import com.example.nomo.model.Debt;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DebtDto {
    private Long id;
    private Long creditorId;
    private String creditorUsername;
    private Long debtorId;
    private String debtorUsername;
    private Double amount;
    private String name;
    private String description;
    private boolean isPaid;
    private LocalDateTime createdAt;
    private Long roomId;

    public DebtDto() {}

    public DebtDto(Long id, Long creditorId, String creditorUsername,
                           Long debtorId, String debtorUsername, Double amount,
                           String description, String name, Boolean isPaid,
                   Long roomId, LocalDateTime createdAt) {
        this.id = id;
        this.creditorId = creditorId;
        this.creditorUsername = creditorUsername;
        this.debtorId = debtorId;
        this.debtorUsername = debtorUsername;
        this.amount = amount;
        this.name = name;
        this.description = description;
        this.isPaid = isPaid;
        this.roomId = roomId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCreditorId() {
        return creditorId;
    }

    public void setCreditorId(Long creditorId) {
        this.creditorId = creditorId;
    }

    public String getCreditorUsername() {
        return creditorUsername;
    }

    public void setCreditorUsername(String creditorUsername) {
        this.creditorUsername = creditorUsername;
    }

    public Long getDebtorId() {
        return debtorId;
    }

    public void setDebtorId(Long debtorId) {
        this.debtorId = debtorId;
    }

    public String getDebtorUsername() {
        return debtorUsername;
    }

    public void setDebtorUsername(String debtorUsername) {
        this.debtorUsername = debtorUsername;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setName(String name) { this.name = name; }

    public String getName() { return name; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoom(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Map<String, Object> responseForCreate(Debt debt) {
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> creditor = new HashMap<>();
        Map<String, Object> debtor = new HashMap<>();
        creditor.put("id", debt.getCreditor().getId());
        creditor.put("username", debt.getCreditor().getUsername());
        debtor.put("id", debt.getDebtor().getId());
        debtor.put("username", debt.getDebtor().getUsername());
        response.put("id", debt.getId());
        response.put("creditor", creditor);
        response.put("debtor", debtor);
        response.put("amount", debt.getAmount());
        response.put("name", debt.getName());
        response.put("description", debt.getDescription());
        response.put("createdAt", debt.getCreatedAt());

        return response;
    }

}
