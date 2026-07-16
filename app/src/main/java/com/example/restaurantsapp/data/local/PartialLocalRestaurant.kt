package com.example.restaurantsapp.data.local

import androidx.room.*

@Entity
class PartialLocalRestaurant(
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean)