package com.example.nomo.repository

import com.example.nomo.network.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun registerUser(
        nickname: String,
        email: String,
        password: String
    ) = apiService.register(ApiService.RegisterRequest(nickname, email, password))

    suspend fun loginUser(
        nickname: String,
        password: String
    ) = apiService.login(ApiService.LoginRequest(nickname, password))
}