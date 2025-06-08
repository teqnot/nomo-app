package com.example.nomo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class SharedPrefManager {
    private final SharedPreferences sharedPreferences;

    @Inject
    public SharedPrefManager(@ApplicationContext Context context) {
        this.sharedPreferences = context.getSharedPreferences("auth_data", Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        sharedPreferences.edit().putString("access_token", token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString("access_token", null);
    }

    public void clearToken() {
        sharedPreferences.edit().remove("access_token").apply();
    }

    public void saveUserId(Long userId) {
        sharedPreferences.edit().putLong("user_id", userId).apply();
    }

    public Long getUserId() {
        return sharedPreferences.getLong("user_id", -1);
    }

    public void saveUsername(String username) {
        sharedPreferences.edit().putString("username", username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString("username", "пользователь");
    }

    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return getToken() != null;
    }
}
