package com.example.fooduapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fooduapp.ui.navigation.ConnectivityObserver
import com.example.fooduapp.ui.navigation.FoodUAppNavGraph

@Composable
fun FoodUApp(
    navController: NavHostController = rememberNavController()
) {
    FoodUAppNavGraph(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodUTopAppBar(
    title: String,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        title = { Text(text = title) },
        actions = {
                  Row {
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Cart Icon")

                      }
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(imageVector = Icons.Filled.PersonPin, contentDescription = "Profile Icon")
                      }
                  }
        },
        modifier = modifier.background(Color.White)
    )
}