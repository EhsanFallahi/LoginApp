package com.ehsanfallahi.loginapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.ehsanfallahi.loginapp.R
import com.ehsanfallahi.loginapp.data.UserPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userPreferences=UserPreferences(this)
        userPreferences.fetchToke.asLiveData().observe(this, Observer {
            if(it==null)return@Observer
                Toast.makeText(this," خوش آمدید!$it",Toast.LENGTH_LONG).show()

        })

        navController = Navigation.findNavController(this,R.id.nav_host_fragment)
    }

}