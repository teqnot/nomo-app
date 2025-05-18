package com.example.nomo.managers

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.nomo.network.ApiService.Friend

object FriendManager {
    private const val PREFS_NAME = "secure_friend_prefs"
    private const val KEY_FRIENDS = "friends_list"

    fun saveFriends(context: Context, friends: List<Friend>) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val friendsString = friends.joinToString(",") { "${it.id}:${it.username}" }

        prefs.edit().putString(KEY_FRIENDS, friendsString).apply()
    }

    fun getFriends(context: Context): List<Friend> {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val prefs = EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val friendsString = prefs.getString(KEY_FRIENDS, null) ?: return emptyList()

        return friendsString.split(",")
            .mapNotNull {
                val parts = it.split(":")
                if (parts.size == 2) {
                    Friend(id = parts[0].toLong(), username = parts[1])
                } else null
            }
    }

    fun clearFriends(context: Context) {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ).edit().remove(KEY_FRIENDS).apply()
    }
}