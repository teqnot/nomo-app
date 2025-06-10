package com.example.nomo.network;

import android.content.Context;

import com.example.nomo.api.AuthApi;
import com.example.nomo.api.DebtApi;
import com.example.nomo.api.UserApi;
import com.example.nomo.network.interceptor.AuthTokenInterceptor;
import com.example.nomo.utils.SharedPrefManager;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/";

    @Provides
    public AuthApi provideAuthApi(SharedPrefManager sharedPrefManager) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AuthApi.class);
    }

    @Provides
    public DebtApi provideDebtApi(SharedPrefManager sharedPrefManager) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthTokenInterceptor(sharedPrefManager))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(DebtApi.class);
    }

    @Provides
    public UserApi provideUserApi(SharedPrefManager sharedPrefManager) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthTokenInterceptor(sharedPrefManager))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(UserApi.class);
    }

    @Provides
    public SharedPrefManager provideSharedPrefManager(@ApplicationContext Context context) {
        return new SharedPrefManager(context);
    }
}
