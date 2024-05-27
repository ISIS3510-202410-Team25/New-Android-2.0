package com.example.fooduapp.ui.restaurant

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooduapp.R
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Restaurant
import com.example.fooduapp.ui.navigation.NavigationDestination
import com.google.gson.Gson

object RestaurantDetailDestination : NavigationDestination {
    override val route = "RestaurantDetail"
    override val titleRes = R.string.restaurant_title
    const val restaurantArg = "restaurant"
}
@Composable
fun RestaurantDetailScreen(
    restaurantName: String?,
    viewModel: RestaurantViewModel = hiltViewModel()
) {
    var restaurant by remember { mutableStateOf<Restaurant?>(null) }

    LaunchedEffect(restaurantName) {
        restaurantName?.let {
            val response = viewModel.getRestaurantByName(it)
            if (response is DataResponse.Success) {
                restaurant = response.data
            } else {
                // Maneja el error
            }
        }
    }

    restaurant?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = it.img,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it.descrip,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Rating: ${it.rating}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}