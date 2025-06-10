package com.example.nomo.repository;

import com.example.nomo.api.UserApi;
import com.example.nomo.model.FriendshipRequest;
import com.example.nomo.model.UserDto;

import java.util.List;


import javax.inject.Inject;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Callback;

@Module
@InstallIn(SingletonComponent.class)
public class UserRepository {
    private final UserApi userApi;

    @Inject
    public UserRepository(UserApi userApi) {
        this.userApi = userApi;
    }

    public void getMyFriends(long userId, Callback<List<UserDto>> callback) {
        userApi.getMyFriends(new FriendshipRequest(userId)).enqueue(callback);
    }

    public void sendFriendRequest(long fromId, long toId, Callback<Void> callback) {
        userApi.sendFriendRequest(new FriendshipRequest(fromId, toId)).enqueue(callback);
    }

    public void searchUsers(String query, Callback<List<UserDto>> callback) {
        userApi.searchUsers(query).enqueue(callback);
    }
}
