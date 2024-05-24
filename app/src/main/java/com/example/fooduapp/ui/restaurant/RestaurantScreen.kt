package com.example.fooduapp.ui.restaurant

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
// import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fooduapp.R
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.ui.navigation.NavigationDestination

object RestaurantDestination : NavigationDestination {
    override val route = "Restaurant"
    override val titleRes = R.string.restaurant_title
}

@Composable
fun RestaurantScreen(
    modifier: Modifier = Modifier,
    viewModel: RestaurantViewModel = hiltViewModel(),
    // connectivityViewModel: ConnectivityViewModel = hiltViewModel()
) {
    // val isConnected by connectivityViewModel.isConnected.observeAsState(true)
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
        when(val response = viewModel.restaurantResponse) {
            is DataResponse.Failure -> {
                Toast.makeText(LocalContext.current, response.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                if (response.data.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(response.data) { restaurant ->
                            Card(modifier = modifier) {
                                Column {
                                    Box(modifier = Modifier.size(200.dp)) {
                                        AsyncImage(
                                            model = restaurant.img,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
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
            null -> {}
        }
    }
}
