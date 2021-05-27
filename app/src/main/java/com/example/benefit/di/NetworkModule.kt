package com.example.benefit.di

import com.example.benefit.BuildConfig
import com.example.benefit.remote.ApiService
import com.example.benefit.remote.ApiServiceFactory
import com.example.benefit.remote.AuthApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }

    @Singleton
    @Provides
    fun provideAuthorizedApiService(): AuthApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
    }


}


