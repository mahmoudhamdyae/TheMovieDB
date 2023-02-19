package com.mahmoudhamdyae.themoviedb1

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb1.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigation Bottom
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    fun makeBottomNavigationViewInvisible() {
        binding.navView.visibility = View.GONE
    }
}