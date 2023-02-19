package com.mahmoudhamdyae.themoviedb1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mahmoudhamdyae.themoviedb1.databinding.ActivityMainBinding
import com.mahmoudhamdyae.themoviedb1.utility.ConnectivityObserver
import com.mahmoudhamdyae.themoviedb1.utility.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setBottomNavigationView(R.id.navigation_explore)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        lifecycleScope.launch {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.Status.Available -> {
                    }
                    ConnectivityObserver.Status.Losing -> {
                        Toast.makeText(application, "Connection is losing", Toast.LENGTH_SHORT).show()

                    }
                    ConnectivityObserver.Status.Lost -> {
                        setBottomNavigationView(R.id.navigation_favourite)
                        Toast.makeText(application, "Connection lost", Toast.LENGTH_SHORT).show()

                    }
                    else -> {
                        setBottomNavigationView(R.id.navigation_favourite)
                        Toast.makeText(application, "Connection is not available", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun makeBottomNavigationViewInvisible() {
        binding.navView.visibility = View.GONE
    }

    private fun setBottomNavigationView(selectedItemId: Int) {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        navView.selectedItemId = selectedItemId
        navView.setupWithNavController(navController)
    }
}