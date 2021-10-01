package com.example.dagger2demo.ui.main.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dagger2demo.SessionManager
import com.example.dagger2demo.models.User
import com.example.dagger2demo.ui.auth.AuthResource
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val sessionManager: SessionManager) : ViewModel() {
    private val TAG = "ProfileViewModel"

    fun getAuthenticatedUser(): LiveData<AuthResource<User>> {
        return sessionManager.getAuthUser()
    }
}