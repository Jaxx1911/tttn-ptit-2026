package com.example.restaurantsapp

import androidx.compose.runtime.*
import androidx.lifecycle.*
import kotlinx.coroutines.*

class RestaurantsViewModel(): ViewModel() {
    private val repository = RestaurantsRepository()
    private val _state = mutableStateOf(
        RestaurantsScreenState(
            restaurants = listOf(),
            isLoading = true)
    )
    val state: State<RestaurantsScreenState>
        get() = _state

    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            _state.value = _state.value.copy(
                error = exception.message,
                isLoading = false
            )
        }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch(errorHandler) {
            val updatedRestaurants =
                repository.toggleFavoriteRestaurant(id, oldValue)
            _state.value = _state.value.copy(
                restaurants = updatedRestaurants)
        }
    }

    init {
        getRestaurants()
    }


    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            val restaurants = repository.getAllRestaurants()
            _state.value = _state.value.copy(
                restaurants = restaurants,
                isLoading = false)
        }
    }
}