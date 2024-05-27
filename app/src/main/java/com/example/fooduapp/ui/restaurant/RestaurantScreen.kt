package com.example.fooduapp.ui.restaurant


import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.fooduapp.R
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.ui.navigation.NavigationDestination
import com.example.fooduapp.domain.model.Restaurant

object RestaurantDestination : NavigationDestination {
    override val route = "Restaurant"
    override val titleRes = R.string.restaurant_title
}

@Composable
fun RestaurantScreen(
    modifier: Modifier = Modifier,
    viewModel: RestaurantViewModel = hiltViewModel(),
    connectivityViewModel: ConnectivityViewModel = hiltViewModel(),
    onRestaurantClick: (Restaurant) -> Unit
) {
    val isConnected by connectivityViewModel.isConnected.observeAsState(true)
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        if (!isConnected) {
            Text(
                text = "WARNING: No internet connection",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        Text(
            text = "Restaurants!",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "These are our restaurants available for you",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Most popular",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        when (val response = viewModel.restaurantResponse) {
            is DataResponse.Failure -> {
                Toast.makeText(context, response.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                if (response.data.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(response.data) { restaurant ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable { onRestaurantClick(restaurant) }
                            ) {
                                Column {
                                    Box(modifier = Modifier.height(200.dp)) {
                                        AsyncImage(
                                            model = restaurant.img,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                    Text(
                                        text = restaurant.name,
                                        modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = restaurant.descrip,
                                        modifier = Modifier.padding(top = 4.dp, start = 8.dp),
                                        fontSize = 14.sp
                                    )
                                    Text(
                                        text = "Rating: ${restaurant.rating}",
                                        modifier = Modifier.padding(top = 4.dp, start = 8.dp),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No restaurants available")
                    }
                }
            }
            else -> {}
        }
    }
}
