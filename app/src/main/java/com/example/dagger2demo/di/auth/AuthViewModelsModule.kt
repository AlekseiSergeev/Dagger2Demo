package com.example.dagger2demo.di.auth

import androidx.lifecycle.ViewModel
import com.example.dagger2demo.di.ViewModelKey
import com.example.dagger2demo.ui.auth.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AuthViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    abstract fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}