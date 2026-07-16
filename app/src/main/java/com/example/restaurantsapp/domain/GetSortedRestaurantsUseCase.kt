package com.example.restaurantsapp.domain

import com.example.restaurantsapp.data.*
import javax.inject.*

class GetSortedRestaurantsUseCase @Inject constructor(
    private val repository: RestaurantsRepository
) {

    suspend operator fun invoke(): List<Restaurant> {
        return repository.getRestaurants()
            .sortedBy { it.title }
    }
}
