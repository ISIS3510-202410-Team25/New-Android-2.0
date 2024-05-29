package com.example.fooduapp.domain.repository

import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    // Here we can define the CRUD operation
    suspend fun deleteFood(food: Food): DataResponse<Boolean>
    suspend fun insertFood(food: Food): DataResponse<Boolean>

    fun getFoods(): Flow<DataResponse<List<Food>>>
}