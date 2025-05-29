package com.example.nomo.viewmodel;

import android.content.Context;
import android.widget.Toast;

public class AuthViewModel {
    public void loginUser(String nickname, String password, Context context) {
        Toast.makeText(context, "Вход выполнен", Toast.LENGTH_SHORT).show();
    }

    public void registerUser(String nickname, String email, String password, Context context) {
        Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show();
    }

    public void showError(String message, Context context) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void resetState() {}
}
