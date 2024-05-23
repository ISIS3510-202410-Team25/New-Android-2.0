package com.example.fooduapp.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.NoteAlt
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Store
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.NoteAlt
import androidx.compose.material.icons.outlined.Percent
import androidx.compose.material.icons.outlined.Store
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fooduapp.FoodUTopAppBar
import com.example.fooduapp.ui.home.HomeDestination
import com.example.fooduapp.ui.home.HomeScreen
import com.example.fooduapp.ui.order.OrderDestination
import com.example.fooduapp.ui.order.OrderScreen
import com.example.fooduapp.ui.promotion.PromotionDestination
import com.example.fooduapp.ui.promotion.PromotionScreen
import com.example.fooduapp.ui.restaurant.RestaurantDestination
import com.example.fooduapp.ui.restaurant.RestaurantScreen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

object HomeNavGraph : NavigationDestinationLogin {
    override val route = "HomeNavGraph"
}

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
    Scaffold (
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
                RestaurantScreen()
            }
            composable(route = OrderDestination.route) {
                OrderScreen()
            }
        }
    }
}
