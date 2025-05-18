package com.example.nomo.di

import android.content.Context
import com.example.nomo.repository.AuthRepository
import com.example.nomo.network.ApiService
import com.example.nomo.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(@ApplicationContext context: Context): ApiService {
        return RetrofitClient.create(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }
}