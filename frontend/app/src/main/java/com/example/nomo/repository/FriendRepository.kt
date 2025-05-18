package com.example.nomo.repository

import com.example.nomo.network.ApiService
import com.example.nomo.network.ApiService.FriendsRequest
import javax.inject.Inject

class FriendRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun loadFriends(userId: Long) = apiService.getFriends(FriendsRequest(userId))
}