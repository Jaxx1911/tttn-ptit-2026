package com.example.restaurantsapp.presentation.details

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.restaurantsapp.data.remote.*
import com.example.restaurantsapp.domain.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.*

class RestaurantDetailsViewModel(private val stateHandle: SavedStateHandle): ViewModel() {
    val state = mutableStateOf<Restaurant?>(null)
    private var restInterface: RestaurantsApiService
    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory
                .create())
            .baseUrl("https://restaurants-app-demo-default-rtdb.asia-southeast1.firebasedatabase.app/")
            .build()
        restInterface = retrofit.create(
            RestaurantsApiService::class.java)
        val id = stateHandle.get<Int>("restaurant_id") ?: 0
        viewModelScope.launch {
            val restaurant = getRemoteRestaurant(id)
            state.value = restaurant
        }
    }

    private suspend fun getRemoteRestaurant(id: Int):
            Restaurant {
        return withContext(Dispatchers.IO) {
            val responseMap = restInterface
                .getRestaurant(id)
            return@withContext responseMap.values.first().let {
                Restaurant(
                    id = it.id,
                    title = it.title,
                    description = it.description
                )
            }
        }
    }
}