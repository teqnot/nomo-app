package com.example.nomo.network

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import java.time.LocalDateTime

interface ApiService {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("debts/by-user")
    suspend fun getDebts(@Body request: GetDebtsRequest): Response<List<DebtByUserResponse>>

    @POST("debts/mark-paid")
    suspend fun markDebtPaid(@Body request: MarkDebtAsPaid): Response<Unit>

    @POST("debts/create")
    suspend fun createDebt(@Body request: CreateDebt): Response<Unit>

    @POST("friendships/list")
    suspend fun getFriends(@Body request: FriendsRequest): Response<List<Friend>>

    data class RegisterRequest(
        val nickname: String,
        val email: String,
        val password: String
    )

    data class LoginRequest(
        val nickname: String,
        val password: String
    )

    data class GetDebtsRequest(
        val userId: Long
    )

    data class MarkDebtAsPaid(
        val debtId: Long
    )

    data class CreateDebt(
        val debtorId: Long,
        var creditorId: Long,
        val amount: Double,
        val description: String,
        val roomId: Long? = null
    )

    data class AuthResponse(
        @SerializedName("access_token")
        val token: String,

        @SerializedName("token_type")
        val tokenType: String = "Bearer",

        @SerializedName("expires_in")
        val expiresIn: Long? = null
    )

    data class DebtByUserResponse(
        @SerializedName("id")
        val id: Long,

        @SerializedName("creditorId")
        val creditorId: Long,

        @SerializedName("creditorUsername")
        val creditorUsername: String,

        @SerializedName("debtorId")
        val debtorId: Long,

        @SerializedName("debtorUsername")
        val debtorUsername: String,

        @SerializedName("amount")
        val amount: Double,

        @SerializedName("description")
        val description: String? = null,

        @SerializedName("isPaid")
        val isPaid: Boolean,

        @SerializedName("createdAt")
        val createdAt: LocalDateTime,

        @SerializedName("roomId")
        val roomId: Long? = null
    )

    data class CreateDebtResponse(
        @SerializedName("createdAt")
        val createdAt: LocalDateTime,

        @SerializedName("amount")
        val amount: Double,

        @SerializedName("debtor")
        val debtor: User,

        @SerializedName("description")
        val description: String? = null,

        @SerializedName("creditor")
        val creditor: User,

        @SerializedName("id")
        val id: Long
    )

    data class User(
        @SerializedName("id")
        val id: Long,

        @SerializedName("username")
        val username: String
    )

    data class FriendsRequest(
        val userId: Long
    )

    data class Friend(
        @SerializedName("id")
        val id: Long,

        @SerializedName("username")
        val username: String
    )
}