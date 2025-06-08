package com.example.nomo.api;

import com.example.nomo.model.AuthRequest;
import com.example.nomo.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {

    @POST("auth/register")
    Call<AuthResponse> registerUser(@Body AuthRequest request);

    @POST("auth/login")
    Call<AuthResponse> loginUser(@Body AuthRequest request);
}
