package com.example.fooduapp.ui.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.fooduapp.R
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.ui.navigation.NavigationDestination
import com.example.fooduapp.ui.theme.FoodUAppTheme

object HomeDestination : NavigationDestination {
    override val route = "Home"
    override val titleRes = R.string.home_title
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Welcome User!",
            fontSize = 32.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Satisfy your cravings with just a few taps",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "Most popular",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp)
        )
        when(val response = viewModel.foodResponse) {
            is DataResponse.Failure -> {
                Toast.makeText(LocalContext.current, response.exception?.message, Toast.LENGTH_SHORT).show()
            }
            DataResponse.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is DataResponse.Success -> {
                Toast.makeText(LocalContext.current, "Datos Agregados", Toast.LENGTH_SHORT).show()
                if(response.data.isNotEmpty()) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(response.data) {
                            Card(modifier = modifier) {
                                Column {
                                    Box(modifier = Modifier.size(200.dp)) {
                                        AsyncImage(
                                            model = it.img,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                    Text(text = it.name, modifier = Modifier.padding(top = 8.dp, start = 8.dp), fontSize = 32.sp, fontWeight = FontWeight.Bold, lineHeight = 32.sp)
                                    Row(modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = "${it.price} COP", fontSize = 16.sp)
                                        Button(onClick = { /*TODO*/ }) {
                                            Text(text = "Order")
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Aun no tienes nada agregado")
                    }
                }
            }
            null -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodUAppTheme {
        HomeScreen()
    }
}