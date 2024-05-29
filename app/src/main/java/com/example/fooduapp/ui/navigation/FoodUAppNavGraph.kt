package com.example.fooduapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fooduapp.ui.restaurant.RestaurantDetailDestination
import com.example.fooduapp.ui.restaurant.RestaurantDestination
import com.example.fooduapp.ui.restaurant.RestaurantDetailScreen
import com.example.fooduapp.ui.sign_in.SignInDestination
import com.example.fooduapp.ui.sign_in.SignInScreen
import com.example.fooduapp.ui.sign_up.SignUpDestination
import com.example.fooduapp.ui.sign_up.SignUpScreen
import com.example.fooduapp.ui.splash.SplashDestination
import com.example.fooduapp.ui.splash.SplashScreen
import com.example.fooduapp.domain.model.Restaurant
import com.example.fooduapp.ui.restaurant.RestaurantScreen

@Composable
fun FoodUAppNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        composable(route = SplashDestination.route) {
            SplashScreen(
                openAndPopUp = {
                    route, popUp -> navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(popUp) { inclusive = true }
                    }
                }
            )
        }

        composable(route = SignInDestination.route) {
            SignInScreen(
                openAndPopUp = {
                        route, popUp -> navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(popUp) { inclusive = true }
                    }
                }
            )
        }

        composable(route = SignUpDestination.route) {
            SignUpScreen(
                openAndPopUp = {
                        route, popUp -> navController.navigate(route) {
                        launchSingleTop = true
                        popUpTo(popUp) { inclusive = true }
                    }
                }
            )
        }

        composable(route = HomeNavGraph.route) {
            HomeNavScreen()
        }
        composable(route = RestaurantDestination.route) {
            RestaurantScreen(
                onRestaurantClick = { restaurant ->
                    navController.navigate(
                        "${RestaurantDetailDestination.route}/${restaurant.name}"
                    )
                }
            )
        }

        composable(
            route = "${RestaurantDetailDestination.route}/{${RestaurantDetailDestination.restaurantArg}}",
            arguments = listOf(navArgument(RestaurantDetailDestination.restaurantArg) { type = NavType.StringType })
        ) { navBackStackEntry ->
            val restaurantName = navBackStackEntry.arguments?.getString(RestaurantDetailDestination.restaurantArg)
            RestaurantDetailScreen(restaurantName = restaurantName!!)
        }

    }
}