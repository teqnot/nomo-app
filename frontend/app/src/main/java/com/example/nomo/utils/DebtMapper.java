package com.example.nomo.utils;

import com.example.nomo.model.Debt;
import com.example.nomo.model.DebtItem;

public class DebtMapper {
    public static Debt fromDebtItem(DebtItem item, long userId) {
        boolean owedToMe = item.isOwedToMe(userId);

        Debt debt = new Debt(item.getDisplayText(userId), item.getAmount(), owedToMe);
        debt.setId(item.getId());

        return debt;
    }
}
