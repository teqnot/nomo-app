package com.example.nomo.ui.addticket

import com.example.nomo.network.ApiService.Friend

data class FriendWithDebt (
    val friend: Friend,
    var amount: String = "0",
    var isSaved: Boolean = false
)

enum class FriendSelectionMode {
    SINGLE, MULTIPLE
}