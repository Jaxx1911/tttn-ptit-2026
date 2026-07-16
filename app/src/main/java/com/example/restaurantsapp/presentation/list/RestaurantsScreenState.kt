package com.example.restaurantsapp.presentation.list

import com.example.restaurantsapp.domain.*

data class RestaurantsScreenState(
    val restaurants: List<Restaurant>,
    val isLoading: Boolean,
    val error: String? = null
)