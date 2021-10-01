package com.example.dagger2demo.ui.auth

import android.util.Log
import androidx.lifecycle.*
import com.example.dagger2demo.SessionManager
import com.example.dagger2demo.models.User
import com.example.dagger2demo.network.auth.AuthApi
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AuthViewModel @Inject constructor(private val authApi: AuthApi, private val sessionManager: SessionManager) : ViewModel() {
    private val TAG = "AuthViewModel"


    fun authenticateWithId(userId: Int){
        Log.i(TAG, "authenticateWithId: attempting to login.")
        sessionManager.authenticateWithId(queryUserId(userId))
    }

    private fun queryUserId(userId: Int): LiveData<AuthResource<User>>{
        return LiveDataReactiveStreams.fromPublisher(
            authApi.getUser(userId)
                // instead of calling onError, do this
                .onErrorReturn(object : Function<Throwable, User> {
                    override fun apply(t: Throwable): User {
                        return User(-1, null, null, null)
                    }
                })
                // wrap User object in AuthResource
                .map(object : Function<User, AuthResource<User>> {
                    override fun apply(user: User): AuthResource<User> {
                        if (user.id == -1) {
                            return AuthResource.Error("Could not auth")
                        }
                        return AuthResource.Authenticated(user)
                    }
                })
                .subscribeOn(Schedulers.io()))
    }

    fun observeAuthState(): LiveData<AuthResource<User>>{
        return sessionManager.getAuthUser()
    }
}
