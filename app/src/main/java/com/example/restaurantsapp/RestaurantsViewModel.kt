package com.example.restaurantsapp

import androidx.compose.runtime.*
import androidx.lifecycle.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.*
import java.net.*

class RestaurantsViewModel(): ViewModel() {
    private var restInterface: RestaurantsApiService
    val state = mutableStateOf(emptyList<Restaurant>())

    private var restaurantsDao = RestaurantsDb.getDaoInstance(
        RestaurantsApplication.getAppContext()
    )
    private val errorHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
        }

    fun toggleFavorite(id: Int, oldValue: Boolean) {
        viewModelScope.launch {
            val updatedRestaurants =
                toggleFavoriteRestaurant(id, oldValue)
            state.value = updatedRestaurants
        }
    }

    private suspend fun toggleFavoriteRestaurant(id: Int, oldValue: Boolean) =
        withContext(Dispatchers.IO) {
            restaurantsDao.update(
                PartialRestaurant(
                    id = id,
                    isFavorite = !oldValue
                )
            )
            restaurantsDao.getAll()
        }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .baseUrl(
                "https://restaurants-app-demo-default-rtdb.asia-southeast1.firebasedatabase.app/"
            )
            .build()
        restInterface = retrofit.create(
            RestaurantsApiService::class.java
        )
        getRestaurants()
    }

    private suspend fun getAllRestaurants(): List<Restaurant> {
            return withContext(Dispatchers.IO) {
                try {
                    refreshCache()
                } catch (e: Exception) {
                    when (e) {
                        is UnknownHostException,
                        is ConnectException,
                        is HttpException -> {
                            if (restaurantsDao.getAll().isEmpty())
                                throw Exception(
                                    "Something went wrong. " +
                                            "We have no data.")
                        }
                        else -> throw e
                    }
                }
                return@withContext restaurantsDao.getAll()
            }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface
            .getRestaurants()
        val favoriteRestaurants = restaurantsDao
            .getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants)
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialRestaurant(it.id, true)
            })
    }

    private fun getRestaurants() {
        viewModelScope.launch(errorHandler) {
            state.value = getAllRestaurants()
        }
    }
}



