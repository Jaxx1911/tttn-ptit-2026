package com.example.restaurantsapp.data

import com.example.restaurantsapp.data.di.*
import com.example.restaurantsapp.data.local.*
import com.example.restaurantsapp.data.remote.*
import com.example.restaurantsapp.domain.*
import kotlinx.coroutines.*
import retrofit2.*
import java.net.*
import javax.inject.*

@Singleton
class RestaurantsRepository @Inject constructor(
    private val restInterface: RestaurantsApiService,
    private val restaurantsDao: RestaurantsDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
        suspend fun toggleFavoriteRestaurant(id: Int, value: Boolean) =
        withContext(dispatcher) {
            restaurantsDao.update(
                PartialLocalRestaurant(
                    id = id,
                    isFavorite = value
                )
            )
        }

    suspend fun loadRestaurants() {
        return withContext(dispatcher) {
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
                                        "We have no data."
                            )
                    }

                    else -> throw e
                }
            }
        }
    }

    private suspend fun refreshCache() {
        val remoteRestaurants = restInterface
            .getRestaurants()
        val favoriteRestaurants = restaurantsDao
            .getAllFavorited()
        restaurantsDao.addAll(remoteRestaurants.map {
            LocalRestaurant(
                it.id,
                it.title,
                it.description,
                false
            )
        })
        restaurantsDao.updateAll(
            favoriteRestaurants.map {
                PartialLocalRestaurant(it.id, true)
            })
    }

    suspend fun getRestaurants() : List<Restaurant> {
        return withContext(dispatcher) {
            return@withContext restaurantsDao.getAll().map {
                Restaurant(
                    it.id, it.title,
                    it.description, it.isFavorite
                )
            }
        }
    }
}
