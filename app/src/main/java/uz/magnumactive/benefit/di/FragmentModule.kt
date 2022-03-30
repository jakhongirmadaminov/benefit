package uz.magnumactive.benefit.di

import uz.magnumactive.benefit.remote.*
import uz.magnumactive.benefit.remote.repository.CardsRemote
import uz.magnumactive.benefit.remote.repository.PartnersRemote
import uz.magnumactive.benefit.remote.repository.UserRemote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
object FragmentModule {


    @Provides
    fun provideUserRepository(
        apiService: ApiService,
        authApiService: AuthApiService
    ): UserRemote {
        return UserRemoteImpl(apiService, authApiService)
    }

    @Provides
    fun provideCardsRepository(
        apiService: ApiService,
        authApiService: AuthApiService
    ): CardsRemote {
        return CardsRemoteImpl(apiService, authApiService)
    }

    @Provides
    fun providePartnersRepository(
        apiService: ApiService,
        authApiService: AuthApiService
    ): PartnersRemote {
        return PartnersRemoteImpl(apiService, authApiService)
    }


}




