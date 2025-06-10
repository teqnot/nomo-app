package com.example.nomo.api;

import com.example.nomo.model.FriendshipRequest;
import com.example.nomo.model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @GET("users/search")
    Call<List<UserDto>> searchUsers(@Query("query") String query);

    @POST("/friendships/list")
    Call<List<UserDto>> getMyFriends(@Body FriendshipRequest request);

    @POST("/friendships/send")
    Call<Void> sendFriendRequest(@Body FriendshipRequest request);
}
