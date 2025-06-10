package com.example.nomo.api;

import com.example.nomo.model.UserDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserApi {
    @GET("users/search")
    Call<List<UserDto>> searchUsers(@Query("query") String query);
}
