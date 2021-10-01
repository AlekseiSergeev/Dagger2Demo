package com.example.dagger2demo.network.main

import com.example.dagger2demo.models.Post
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface MainApi {
    @GET("posts")
    fun getPostsFromUser(@Query("userId") id: Int?): Flowable<List<Post>>
}