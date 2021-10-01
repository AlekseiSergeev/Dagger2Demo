package com.example.dagger2demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.dagger2demo.models.User
import com.example.dagger2demo.ui.auth.AuthActivity
import com.example.dagger2demo.ui.auth.AuthResource
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity: DaggerAppCompatActivity() {
    private val TAG = "BaseActivity"

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        sessionManager.getAuthUser().observe(this, object: Observer<AuthResource<User>> {
            override fun onChanged(userAuthResourse: AuthResource<User>?) {
                if(userAuthResourse != null) {
                    when(userAuthResourse) {
                        is AuthResource.Loading -> {}
                        is AuthResource.Authenticated -> {
                            Log.i(TAG, "OnChanged: LOGIN SUCCESS ${userAuthResourse.data}")
                        }
                        is AuthResource.Error -> {
                            Toast.makeText(this@BaseActivity,
                                userAuthResourse.message + "\nDid you enter a number 1 and 10?",
                                Toast.LENGTH_SHORT).show()
                        }
                        is AuthResource.Logout -> {
                            navLoginScreen()
                        }
                    }
                }
            }

        })
    }

   private fun navLoginScreen() {
       val intent = Intent(this, AuthActivity::class.java)
       startActivity(intent)
       finish()
   }
}