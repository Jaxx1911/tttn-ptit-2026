package com.example.restaurantsapp.data.local

import androidx.room.*

@Entity(tableName = "restaurants")
data class LocalRestaurant(
    @PrimaryKey()
    @ColumnInfo(name = "r_id")
    val id: Int,
    @ColumnInfo(name = "r_title")
    val title: String,
    @ColumnInfo(name = "r_description")
    val description: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false)