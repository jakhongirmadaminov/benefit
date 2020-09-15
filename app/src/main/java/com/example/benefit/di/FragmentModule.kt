package com.example.benefit.di
//import com.example.benefit.ui.auth.LoginFragmentFactory
import com.example.benefit.remote.PartnersRemoteImpl
import com.example.benefit.remote.UserRemoteImpl
import com.example.benefit.remote.repository.PartnersRemote
import com.example.benefit.remote.repository.UserRemote
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
abstract class FragmentModule {

//    @JvmStatic
//    @Singleton
//    @Provides
//    @Named("LoginFragmentFactory")
//    fun provideLoginFragmentFactory(): FragmentFactory {
//        return LoginFragmentFactory()
//    }

    @Binds
    abstract fun provideUserRepository(userRemoteImpl: UserRemoteImpl): UserRemote

    @Binds
    abstract fun providePartnersRepository(partnersRemoteImpl: PartnersRemoteImpl): PartnersRemote


}




