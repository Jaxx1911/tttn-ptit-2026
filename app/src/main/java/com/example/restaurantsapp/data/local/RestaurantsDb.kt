package com.example.restaurantsapp.data.local

import android.content.*
import androidx.room.*

@Database(
    entities = [LocalRestaurant::class],
    version = 3,
    exportSchema = false)
abstract class RestaurantsDb : RoomDatabase() {
    abstract val dao: RestaurantsDao
    companion object {
        @Volatile
        private var INSTANCE: RestaurantsDao? = null

        fun getDaoInstance(context: Context): RestaurantsDao
        {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = buildDatabase(context).dao
                    INSTANCE = instance
                }
                return instance
            }
        }
        private fun buildDatabase(context: Context):
                RestaurantsDb =
            Room.databaseBuilder(
                context.applicationContext,
                RestaurantsDb::class.java,
                "restaurants_database")
                .fallbackToDestructiveMigration()
                .build()
    }
}