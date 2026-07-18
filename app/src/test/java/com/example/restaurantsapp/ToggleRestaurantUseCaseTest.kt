package com.example.restaurantsapp

import com.example.restaurantsapp.data.*
import com.example.restaurantsapp.domain.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class ToggleRestaurantUseCaseTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    @Test
    fun toggleRestaurant_IsUpdatingFavoriteField() =
        scope.runTest {
            // Setup useCase
            val restaurantsRepository = RestaurantsRepository(
                FakeApiService(),
                FakeRoomDao(),
                dispatcher
            )
            val getSortedRestaurantsUseCase =
                GetSortedRestaurantsUseCase(restaurantsRepository)
            val useCase = ToggleRestaurantUseCase(
                restaurantsRepository,
                getSortedRestaurantsUseCase
            )
            // Preload data
            restaurantsRepository.loadRestaurants()
            advanceUntilIdle()
            // Execute useCase
            val restaurants = DummyContent.getDomainRestaurants()
            val targetItem = restaurants[0]
            val isFavorite = targetItem.isFavorite
            val updatedRestaurants = useCase(
                targetItem.id,
                isFavorite
            )
            advanceUntilIdle()
            // Assertion
            restaurants[0] = targetItem.copy(isFavorite =
                !isFavorite)
            assert(updatedRestaurants == restaurants)
        }
}