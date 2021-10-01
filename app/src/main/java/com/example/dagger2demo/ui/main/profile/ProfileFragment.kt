package com.example.dagger2demo.ui.main.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.dagger2demo.R
import com.example.dagger2demo.models.User
import com.example.dagger2demo.ui.auth.AuthResource
import com.example.dagger2demo.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment: DaggerFragment() {
    private val TAG = "ProfileFragment"
    private lateinit var viewModel: ProfileViewModel
    private lateinit var email: TextView
    private lateinit var username: TextView
    private lateinit var website: TextView

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG,"onViewCreated: ProfileFragment was created")

        email= view.findViewById(R.id.email)
        username= view.findViewById(R.id.username)
        website= view.findViewById(R.id.website)

        viewModel= ViewModelProvider(this, providerFactory).get(ProfileViewModel::class.java)

        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(viewLifecycleOwner)
        viewModel.getAuthenticatedUser().observe(viewLifecycleOwner, object: Observer<AuthResource<User>>{
            override fun onChanged(userAuthResourse: AuthResource<User>?) {
                if(userAuthResourse!= null){
                    when(userAuthResourse){
                        is AuthResource.Authenticated -> {
                            setUserDetail(userAuthResourse.data)
                        }
                        is AuthResource.Error -> {
                            setErrorDetails(userAuthResourse.message)
                        }
                    }
                }
            }
        })
    }

    private fun setErrorDetails(message: String) {
        email.text= message
        username.text= "Error"
        website.text= "Error"
    }

    private fun setUserDetail(data: User) {
        email.text= data.email
        username.text= data.username
        website.text= data.website
    }
}