package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.repository.Repository

class DeleteFood(private val repository: Repository) {
    suspend operator fun invoke(food: Food) = repository.deleteFood(food)
}