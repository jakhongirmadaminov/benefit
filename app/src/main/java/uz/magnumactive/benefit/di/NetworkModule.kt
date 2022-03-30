package uz.magnumactive.benefit.di

import uz.magnumactive.benefit.remote.ApiService
import uz.magnumactive.benefit.remote.ApiServiceFactory
import uz.magnumactive.benefit.remote.AuthApiService
import uz.magnumactive.benefit.util.SystemDateTimeManager
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
        return ApiServiceFactory.makeApiService(uz.magnumactive.benefit.BuildConfig.DEBUG)
    }

    @Singleton
    @Provides
    fun provideAuthorizedApiService(): AuthApiService {
        return ApiServiceFactory.makeAuthorizedApiService(uz.magnumactive.benefit.BuildConfig.DEBUG)
    }

    @Singleton
    @Provides
    fun provideSystemDateTimeManager() = SystemDateTimeManager()


}


