package com.example.dagger2demo.di.main

import com.example.dagger2demo.network.main.MainApi
import com.example.dagger2demo.ui.main.posts.PostRecyclerAdapter
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
object MainModule {
    @MainScope
    @Provides
    fun provideAdapter(): PostRecyclerAdapter {
        return PostRecyclerAdapter()
    }

    @MainScope
    @Provides
    fun provideMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}