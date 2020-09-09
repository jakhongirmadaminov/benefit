package com.example.benefit.di

import com.example.benefit.BuildConfig
import com.example.benefit.remote.ApiService
import com.example.benefit.remote.ApiServiceFactory
import com.example.benefit.remote.AuthorizedApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return ApiServiceFactory.makeApiService(BuildConfig.DEBUG)
    }


    @Singleton
    @Provides
    fun provideAuthorizedApiService(): AuthorizedApiService {
        return ApiServiceFactory.makeAuthorizedApiService(BuildConfig.DEBUG)
    }


}


