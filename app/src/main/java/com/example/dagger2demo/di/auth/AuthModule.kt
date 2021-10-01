package com.example.dagger2demo.di.auth

import com.example.dagger2demo.network.auth.AuthApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create

@Module
object AuthModule {
    @AuthScope
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}