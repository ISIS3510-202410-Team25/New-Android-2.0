package com.example.fooduapp.domain.usecases

import com.example.fooduapp.domain.repository.Repository

class GetFood(private val repository: Repository) {
    operator fun invoke() = repository.getFoods()
}