package com.example.dagger2demo.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.dagger2demo.BaseActivity
import com.example.dagger2demo.R
import com.example.dagger2demo.ui.main.posts.PostsFragment
import com.example.dagger2demo.ui.main.profile.ProfileFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout->{
                sessionManager.logOut()
                return true
            }
            android.R.id.home -> {
                if(drawer_layout.isDrawerOpen(GravityCompat.START)){
                    drawer_layout.closeDrawer(GravityCompat.START)
                    return true
                }
                else return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
        NavigationUI.setupWithNavController(nav_view, navController)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.nav_profile -> {
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.main, true).build()
                Navigation.findNavController(this, R.id.nav_host_fragment)
                    .navigate(R.id.profileFragment, null, navOptions)
            }
            R.id.nav_posts -> {
                if(isValidDestination(R.id.postsFragment)) {
                    Navigation.findNavController(this, R.id.nav_host_fragment)
                        .navigate(R.id.postsFragment)
                }
            }
        }
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun isValidDestination(destination: Int): Boolean {
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).currentDestination?.id
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawer_layout)
    }
}