package com.example.dagger2demo.ui.main

sealed class Resource<out T> {

    open val data: T? = null

    data class Success<out T>(override val data: T?) : Resource<T>()
    class Error(var message: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}