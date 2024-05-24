package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.repository.Repository

class RestaurantUseCase(private val repository: Repository) {
    fun getRestaurants() = repository.getRestaurants()
}
