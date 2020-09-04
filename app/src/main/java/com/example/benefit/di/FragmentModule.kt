package com.example.benefit.di
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.ViewModelProvider
//import com.example.benefit.ui.auth.LoginFragmentFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Named
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(ApplicationComponent::class)
object FragmentModule {

//    @JvmStatic
//    @Singleton
//    @Provides
//    @Named("LoginFragmentFactory")
//    fun provideLoginFragmentFactory(): FragmentFactory {
//        return LoginFragmentFactory()
//    }

//    @AuthScope
//    @Provides
//    fun provideAuthFragmentFactory(viewModelFactory: ViewModelProvider.Factory): FragmentFactory {
//        return AuthFragmentFactory(
//            viewModelFactory)
//    }


}




