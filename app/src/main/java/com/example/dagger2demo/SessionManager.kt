package com.example.dagger2demo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.dagger2demo.models.User
import com.example.dagger2demo.ui.auth.AuthResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor() {
    private val TAG = "SessionManager"
    private val cachedUser = MediatorLiveData<AuthResource<User>>()

    fun authenticateWithId (source: LiveData<AuthResource<User>>){
        if(cachedUser!= null){
            cachedUser.value = AuthResource.Loading
            cachedUser.addSource(source, object : Observer<AuthResource<User>> {
                override fun onChanged(user: AuthResource<User>?) {
                    cachedUser.value = user
                    cachedUser.removeSource(source)
                }
            })
        }
    }

    fun logOut() {
        Log.i(TAG, "logOut: logging out...")
        cachedUser.value = AuthResource.Logout
    }

    fun getAuthUser(): LiveData<AuthResource<User>> {
        return cachedUser
    }
}