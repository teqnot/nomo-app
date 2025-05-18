package com.example.nomo.utils

import android.util.Patterns

object Validators {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun isValidNickname(nickname: String): Boolean {
        return nickname.length in 3..20
    }
}
