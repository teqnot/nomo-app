package com.example.nomo.network.interceptor;

import android.util.Log;

import com.example.nomo.model.AuthRequest;
import com.example.nomo.utils.SharedPrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthTokenInterceptor implements Interceptor {
    private final SharedPrefManager sharedPrefManager;

    public AuthTokenInterceptor(SharedPrefManager sharedPrefManager) {
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        String token = sharedPrefManager.getToken();

        Request.Builder builder = original.newBuilder()
                .header("Authorization", token != null ? "Bearer " + token : "")
                .header("Content-type", "application/json")
                .header("Accept", "*/*");

        return chain.proceed(builder.build());
    }
}
