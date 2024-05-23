package com.example.fooduapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.usecases.FoodUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val useCase: FoodUseCase
) : ViewModel() {

    var foodResponse by mutableStateOf<DataResponse<List<Food>>?>(null)
    var deleteResponse by mutableStateOf<DataResponse<Boolean>?>(null)

    init {
        getFood()
    }

    private fun getFood() = viewModelScope.launch {
        foodResponse = DataResponse.Loading
        useCase.getFood().collect() {
            foodResponse = it
        }
    }

    fun deleteFood(food: Food) = viewModelScope.launch {
        deleteResponse = DataResponse.Loading
        deleteResponse = useCase.deleteFood(food)
    }
}