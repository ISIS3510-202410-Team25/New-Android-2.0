package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.repository.FoodRepository

class GetFood(private val foodRepository: FoodRepository) {
    operator fun invoke() = foodRepository.getFoods()
}