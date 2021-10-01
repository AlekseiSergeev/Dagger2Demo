package com.example.dagger2demo.ui.auth

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.RequestManager
import com.example.dagger2demo.R
import com.example.dagger2demo.models.User
import com.example.dagger2demo.ui.main.MainActivity
import com.example.dagger2demo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject


class AuthActivity : DaggerAppCompatActivity(), View.OnClickListener {
    private val TAG = "AuthActivity"
    private lateinit var viewModel: AuthViewModel
    private lateinit var userId: EditText
    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var logo: Drawable

    @Inject
    lateinit var requestManager: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        userId= findViewById(R.id.user_id_input)
        progressBar= findViewById(R.id.progress_bar)

        login_button.setOnClickListener(this)

        viewModel= ViewModelProvider(this, providerFactory).get(AuthViewModel::class.java)

        setLogo()
        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.observeAuthState().observe(this, object : Observer<AuthResource<User>>{
            override fun onChanged(user: AuthResource<User>?) {
                if(user != null) {
                    when(user) {
                        is AuthResource.Loading -> showProgressBar(true)
                        is AuthResource.Authenticated -> {
                            showProgressBar(false)
                            Log.i(TAG, "OnChanged: LOGIN SUCCESS ${user.data}")
                            onLoginSuccess()
                        }
                        is AuthResource.Error -> {
                            showProgressBar(false)
                            Toast.makeText(this@AuthActivity,
                                user.message + "\nDid you enter a number 1 and 10?",
                                Toast.LENGTH_SHORT).show()
                        }
                        is AuthResource.Logout -> showProgressBar(false)
                    }
                }
            }

        })
    }

    private fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showProgressBar(isVisible: Boolean) {
        if(isVisible){
            progressBar.visibility= View.VISIBLE
        } else {
            progressBar.visibility= View.GONE
        }
    }

    private fun setLogo() {
        requestManager
            .load(logo)
            .into(findViewById(R.id.login_logo))
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.login_button -> {
                attemptLogin()
            }
        }
    }

    private fun attemptLogin() {
        if (TextUtils.isEmpty(userId.text.toString())) {
            return
        }
        viewModel.authenticateWithId(Integer.parseInt(userId.text.toString()))
    }
}