package com.example.restaurantsapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.*
import com.example.restaurantsapp.presentation.*
import com.example.restaurantsapp.presentation.list.*
import com.example.restaurantsapp.ui.theme.*
import org.junit.*

class RestaurantsScreenTest {
    @get:Rule
    val testRule: ComposeContentTestRule =
        createComposeRule()
    @Test
    fun initialState_isRendered() {
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = emptyList(),
                        isLoading = true
                    ),
                    onFavoriteClick = { _: Int, _: Boolean -> },
                    onItemClick = { }
                )
            }
        }
        testRule.onNodeWithContentDescription(
                Description.RESTAURANTS_LOADING
                ).assertIsDisplayed()
    }

    @Test
    fun stateWithContent_isRendered() {
        val restaurants = DummyContent.getDomainRestaurants()
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false),
                            onFavoriteClick =
                    { _: Int, _: Boolean -> },
                    onItemClick = { }
                )
            }
        }
        testRule.onNodeWithText(restaurants[0].title).assertIsDisplayed()
        testRule.onNodeWithText(restaurants[0].description).assertIsDisplayed()
        testRule.onNodeWithContentDescription(Description.RESTAURANTS_LOADING).assertDoesNotExist()
    }
    @Test
    fun stateWithContent_ClickOnItem_isRegistered() {
        val restaurants = DummyContent.getDomainRestaurants()
        val targetRestaurant = restaurants[0]
        testRule.setContent {
            RestaurantsAppTheme {
                RestaurantsScreen(
                    state = RestaurantsScreenState(
                        restaurants = restaurants,
                        isLoading = false),
                    onFavoriteClick = { _, _ -> },
                    onItemClick = { id -> assert(id == targetRestaurant.id)})
            }
        }
        testRule.onNodeWithText(targetRestaurant.title)
            .performClick()
    }
}
