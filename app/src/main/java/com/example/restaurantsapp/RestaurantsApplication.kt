package com.example.restaurantsapp

import android.app.*
import android.content.*

class RestaurantsApplication: Application() {
    init { app = this }
    companion object {
        private lateinit var app: RestaurantsApplication
        fun getAppContext(): Context =
            app.applicationContext
    }
}