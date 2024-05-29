package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.repository.FoodRepository

class DeleteFood(private val foodRepository: FoodRepository) {
    suspend operator fun invoke(food: Food) = foodRepository.deleteFood(food)
}