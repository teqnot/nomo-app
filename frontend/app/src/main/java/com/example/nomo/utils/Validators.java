package com.example.nomo.utils;

public class Validators {

    public static boolean isValidNickname(String nickname) {
        return nickname != null && nickname.length() >= 3 && nickname.length() <= 20;
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
