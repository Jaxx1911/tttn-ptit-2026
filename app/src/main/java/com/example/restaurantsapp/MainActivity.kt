package com.example.restaurantsapp

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsScreen()
        }
    }
}
