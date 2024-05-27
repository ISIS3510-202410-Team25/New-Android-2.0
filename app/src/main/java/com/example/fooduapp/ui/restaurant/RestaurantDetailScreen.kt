package com.example.fooduapp.ui.restaurant

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooduapp.R
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
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
    connectivityViewModel: ConnectivityViewModel = hiltViewModel(),
    viewModel: RestaurantViewModel = hiltViewModel()
) {
    var restaurant by remember { mutableStateOf<Restaurant?>(null) }
    val foodResponse by viewModel.foodResponse.collectAsState()
    val isConnected by connectivityViewModel.isConnected.observeAsState(true)
    val context = LocalContext.current

    LaunchedEffect(restaurantName) {
        restaurantName?.let {
            val response = viewModel.getRestaurantByName(it)
            if (response is DataResponse.Success) {
                restaurant = response.data
                viewModel.getFoodsByRestaurantName(it)
            } else {
                // Maneja el error
            }
        }
    }

    var foods by remember { mutableStateOf<List<Food>>(emptyList()) }

    when (foodResponse) {
        is DataResponse.Success<*> -> {
            foods = (foodResponse as DataResponse.Success<List<Food>>).data
        }
        is DataResponse.Failure<*> -> {
            // Maneja el error
        }
        DataResponse.Loading -> {
            // Mostrar un indicador de carga
        }
        null -> {
            // Inicialmente no hacer nada
        }
    }

    restaurant?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            if (!isConnected) {
                Text(
                    text = "WARNING: No internet connection",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Menu: ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            LazyColumn {
                items(foods) { food ->
                    FoodCard(food)
                }
            }
        }
    }
}
    @Composable
    fun FoodCard(food: Food) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            //elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = food.img,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Crop
                )
                Column {
                    Text(
                        text = food.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Rating: ${food.rating}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }