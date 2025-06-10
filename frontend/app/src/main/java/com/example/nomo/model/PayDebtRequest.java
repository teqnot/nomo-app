package com.example.nomo.model;

public class PayDebtRequest {
    private Long debtId;
    private Double amountPaid;

    public PayDebtRequest(Long debtId, Double amountPaid) {
        this.debtId = debtId;
        this.amountPaid = amountPaid;
    }

    public Long getDebtId() { return debtId; }

    public Double getAmountPaid() { return amountPaid; }
}