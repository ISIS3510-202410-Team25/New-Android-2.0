package com.example.fooduapp.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fooduapp.FoodUTopAppBar
import com.example.fooduapp.ui.home.HomeDestination
import com.example.fooduapp.ui.home.HomeScreen
import com.example.fooduapp.ui.order.OrderDestination
import com.example.fooduapp.ui.order.OrderScreen
import com.example.fooduapp.ui.promotion.PromotionDestination
import com.example.fooduapp.ui.promotion.PromotionScreen
import com.example.fooduapp.ui.restaurant.RestaurantDestination
import com.example.fooduapp.ui.restaurant.RestaurantDetailScreen
import com.example.fooduapp.ui.restaurant.RestaurantScreen
import kotlinx.coroutines.launch

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

object HomeNavGraph : NavigationDestinationLogin {
    override val route = "HomeNavGraph"
}
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeNavScreen(
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Promotion",
            selectedIcon = Icons.Filled.Percent,
            unselectedIcon = Icons.Outlined.Percent
        ),
        BottomNavigationItem(
            title = "Restaurant",
            selectedIcon = Icons.Filled.Store,
            unselectedIcon = Icons.Outlined.Store
        ),
        BottomNavigationItem(
            title = "Order",
            selectedIcon = Icons.Filled.NoteAlt,
            unselectedIcon = Icons.Outlined.NoteAlt
        )
    )
    val rootNavController = rememberNavController()
    val navBackStackEntry by rootNavController.currentBackStackEntryAsState()

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    scope.launch {
        snackbarHostState.showSnackbar("Hello world!", duration = SnackbarDuration.Indefinite)
    }

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            navBackStackEntry?.destination?.route?.let { FoodUTopAppBar(title = it) }
        },
        bottomBar = {
            NavigationBar {
                items.forEach{item ->
                    val isSelected = item.title == navBackStackEntry?.destination?.route
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            rootNavController.navigate(item.title) {
                                popUpTo(rootNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(text = item.title) },
                        icon = {
                            Icon(
                                imageVector = if(isSelected) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = rootNavController,
            startDestination = HomeDestination.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = HomeDestination.route) {
                HomeScreen()
            }
            composable(route = PromotionDestination.route) {
                PromotionScreen()
            }
            composable(route = RestaurantDestination.route) {
                RestaurantScreen(
                    onRestaurantClick = { restaurant ->

                        rootNavController.navigate("restaurantDetails/${restaurant.name}")
                    }
                )
            }
            composable(
                route = "restaurantDetails/{restaurantName}",
                arguments = listOf(navArgument("restaurantName") { type = NavType.StringType })
            ) { backStackEntry ->
                val restaurantName = backStackEntry.arguments?.getString("restaurantName")
                RestaurantDetailScreen(restaurantName)
            }
            composable(route = OrderDestination.route) {
                OrderScreen()
            }

        }
    }
}
