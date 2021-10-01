package com.example.dagger2demo.di.main

import androidx.lifecycle.ViewModel
import com.example.dagger2demo.di.ViewModelKey
import com.example.dagger2demo.ui.main.posts.PostsViewModel
import com.example.dagger2demo.ui.main.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel::class)
    abstract fun bindPostsViewModel(viewModel: PostsViewModel): ViewModel
}