package com.example.dagger2demo.di

import androidx.lifecycle.ViewModelProvider
import com.example.dagger2demo.viewmodels.ViewModelProviderFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(modelProviderFactory: ViewModelProviderFactory): ViewModelProvider.Factory
}