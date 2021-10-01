package com.example.dagger2demo.ui.auth

sealed class AuthResource<out R> {

    open val data: R? = null

    data class Authenticated<out R>(override val data: R) : AuthResource<R>()
    class Error(var message: String) : AuthResource<Nothing>()
    object Loading : AuthResource<Nothing>()
    object Logout : AuthResource<Nothing>()
}