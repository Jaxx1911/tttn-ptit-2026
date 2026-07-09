package com.example.restaurantsapp

import android.os.*
import androidx.activity.*
import androidx.activity.compose.*
import androidx.compose.runtime.*
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.restaurantsapp.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantsAppTheme {
                RestaurantsApp()
            }
        }
    }
}

@Composable
private fun RestaurantsApp() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = "restaurants"
    ) {
        composable(route = "restaurants") {
            RestaurantsScreen { id ->
                navController.navigate("restaurants/$id")
            }
        }
        composable(route = "restaurants/{restaurant_id}",
            arguments =
                listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                }),
            deepLinks = listOf(navDeepLink {
                uriPattern =
                    "www.restaurantsapp.details.com/{restaurant_id}"
                })
        ){
            RestaurantDetailsScreen()
        }
    }
}