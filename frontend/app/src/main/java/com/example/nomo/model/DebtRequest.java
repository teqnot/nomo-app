package com.example.nomo.model;

public class DebtRequest {
    private Long debtorId;
    private Long creditorId;
    private Double amount;
    private String name;
    private String description;

    public DebtRequest(Long debtorId, Long creditorId, Double amount, String name, String description) {
        this.debtorId = debtorId;
        this.creditorId = creditorId;
        this.amount = amount;
        this.name = name;
        this.description = description;
    }
}
