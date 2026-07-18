package com.example.restaurantsapp

import com.example.restaurantsapp.data.*
import com.example.restaurantsapp.domain.*
import com.example.restaurantsapp.presentation.list.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.*

@ExperimentalCoroutinesApi
class RestaurantsViewModelTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)
    @Test
    fun initialState_isProduced() = scope.runTest  {
        val viewModel = getViewModel()
        val initialState = viewModel.state.value
        assert(
            initialState == RestaurantsScreenState(
                restaurants = emptyList(),
                isLoading = true,
                error = null)
        )
    }
    private fun getViewModel(): RestaurantsViewModel {
        val restaurantsRepository = RestaurantsRepository(
            FakeApiService(), FakeRoomDao(), dispatcher
        )
        val getSortedRestaurantsUseCase = GetSortedRestaurantsUseCase(restaurantsRepository)
        val getInitialRestaurantsUseCase = GetInitialRestaurantsUseCase(restaurantsRepository, getSortedRestaurantsUseCase)
        val toggleRestaurantUseCase = ToggleRestaurantUseCase(restaurantsRepository, getSortedRestaurantsUseCase)
        return RestaurantsViewModel(
            getInitialRestaurantsUseCase,
            toggleRestaurantUseCase,
            dispatcher)
    }

    @Test
    fun stateWithContent_isProduced() = scope.runTest {
        val testVM = getViewModel()
        advanceUntilIdle()
        val currentState = testVM.state.value
        assert(
            currentState == RestaurantsScreenState(
                restaurants =
                    DummyContent.getDomainRestaurants(),
                isLoading = false,
                error = null)
        )
    }
}
