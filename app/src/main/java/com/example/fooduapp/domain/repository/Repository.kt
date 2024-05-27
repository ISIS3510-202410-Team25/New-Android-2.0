package com.example.fooduapp.domain.repository

import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface Repository {

    // Here we can define the CRUD operation
    suspend fun deleteFood(food: Food): DataResponse<Boolean>
    suspend fun insertFood(food: Food): DataResponse<Boolean>

    fun getFoods(): Flow<DataResponse<List<Food>>>

    fun getRestaurants(): Flow<DataResponse<List<Restaurant>>>
    suspend fun getRestaurantByName(restaurantName: String): DataResponse<Restaurant>
    suspend fun getFoodsByRestaurantName(restaurantName: String): DataResponse<List<Food>>
}