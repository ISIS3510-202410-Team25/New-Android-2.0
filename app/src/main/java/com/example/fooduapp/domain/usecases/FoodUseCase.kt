package com.example.fooduapp.domain.usecases

data class FoodUseCase(
    val getFood: GetFood,
    val insertFood: InsertFood,
    val deleteFood: DeleteFood
)
