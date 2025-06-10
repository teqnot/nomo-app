package com.example.nomo.dto;

public class PayDebtRequest {
    private Long debtId;
    private Double amountPaid;

    public PayDebtRequest() {}

    public Long getDebtId() { return debtId; }
    public void setDebtId(Long debtId) { this.debtId = debtId; }

    public Double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(Double amountPaid) { this.amountPaid = amountPaid; }
}
