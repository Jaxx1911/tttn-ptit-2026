package com.example.restaurantsapp

import retrofit2.*
import retrofit2.http.*

interface RestaurantsApiService {
    @GET("restaurants.json")
    fun getRestaurants(): Call<List<Restaurant>>
}