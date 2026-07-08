package com.example.restaurantsapp

import retrofit2.http.*

interface RestaurantsApiService {
    @GET("restaurants.json")
    suspend fun getRestaurants(): List<Restaurant>
}