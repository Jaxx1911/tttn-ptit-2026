package com.example.restaurantsapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.viewmodel.compose.*

@Composable
fun RestaurantDetailsScreen() {
    val viewModel: RestaurantDetailsViewModel =
        viewModel()
    val item = viewModel.state.value
    if (item != null) {
        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally,
            modifier =
                Modifier.fillMaxSize().padding(16.dp)
        ) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.padding(
                    top = 32.dp,
                    bottom = 32.dp
                )
            )
            RestaurantDetails(
                item.title,
                item.description,
                Modifier.padding(bottom = 32.dp),
                Alignment.CenterHorizontally)
            Text("More info coming soon!")
        }
    }
}