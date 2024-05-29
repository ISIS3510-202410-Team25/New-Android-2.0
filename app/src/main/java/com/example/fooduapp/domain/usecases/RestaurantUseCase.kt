package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.repository.Repository
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class RestaurantUseCase(private val repository: Repository) {
    fun getRestaurants(): Flow<DataResponse<List<Restaurant>>> = repository.getRestaurants()
    suspend fun getRestaurantByName(restaurantName: String): DataResponse<Restaurant> =
        repository.getRestaurantByName(restaurantName)

    suspend fun getFoodsByRestaurantName(restaurantName: String): DataResponse<List<Food>> =
        repository.getFoodsByRestaurantName(restaurantName)
}
