package com.example.nomo.ui.addticket;

import com.example.nomo.model.Friend;

public class FriendWithDebt {
    private Friend friend;
    private String amount = "";
    private boolean isSaved = false;

    public FriendWithDebt(Friend friend) {
        this.friend = friend;
    }

    public Friend getFriend() {
        return friend;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setSaved(boolean saved) {
        isSaved = saved;
    }
}
