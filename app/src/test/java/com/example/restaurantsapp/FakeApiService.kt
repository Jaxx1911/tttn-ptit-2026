package com.example.restaurantsapp

import com.example.restaurantsapp.data.remote.*
import kotlinx.coroutines.*

class FakeApiService : RestaurantsApiService {
    override suspend fun getRestaurants()
            : List<RemoteRestaurant> {
        delay(1000)
        return DummyContent.getRemoteRestaurants()
    }
    override suspend fun getRestaurant(id: Int)
            : Map<String, RemoteRestaurant> {
        delay(1000)
        val restaurant = DummyContent.getRemoteRestaurants().first { it.id == id }
        return mapOf(id.toString() to restaurant)
    }
}