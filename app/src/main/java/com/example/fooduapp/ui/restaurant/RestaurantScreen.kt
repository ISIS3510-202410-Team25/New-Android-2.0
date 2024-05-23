package com.example.fooduapp.ui.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.fooduapp.R
import com.example.fooduapp.ui.navigation.NavigationDestination

object RestaurantDestination : NavigationDestination {
    override val route = "Restaurant"
    override val titleRes = R.string.restaurant_title
}
@Composable
fun RestaurantScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Restaurant Screen")
    }
}