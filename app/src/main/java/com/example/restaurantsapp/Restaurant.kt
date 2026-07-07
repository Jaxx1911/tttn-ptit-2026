package com.example.restaurantsapp

data class Restaurant(val id: Int,
                      val title: String,
                      val description: String,
                      var isFavorite: Boolean = false)
val dummyRestaurants = listOf(
    Restaurant(0, "Alfredo foods", "At Alfredo's …"),
    Restaurant(1, "Mike and Ben's food pub", "anc"),
    Restaurant(2, "Nollowa Chicken", ""),
    Restaurant(3, "Mc' Donald", "Big Mac's …"),
    Restaurant(4, "KFC", "Kentucky Fried Chicken"),
    Restaurant(5, "Lotteria", ""),
    Restaurant(6, "Pepper Lunch", "Japanese Pan Rice"),
    Restaurant(7, "iSushi", "Fresh Sushi"),
    Restaurant(8, "Fu Rong Hua", "Chinese Food"),
    Restaurant(9, "Soho Steak", "Steak House"),
    Restaurant(10, "Hadilao", "Hot Pot ")
)

