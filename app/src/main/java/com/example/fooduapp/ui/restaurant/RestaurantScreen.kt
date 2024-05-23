package com.example.fooduapp.ui.restaurant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fooduapp.R
import com.example.fooduapp.ui.navigation.NavigationDestination

object RestaurantDestination : NavigationDestination {
    override val route = "Restaurant"
    override val titleRes = R.string.restaurant_title
}
@Composable
fun RestaurantScreen(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Restaurants!",
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = "This are our restaurants available for you",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Text(
                text = "Most popular",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
}
