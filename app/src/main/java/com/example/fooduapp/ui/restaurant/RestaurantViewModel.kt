package com.example.fooduapp.ui.restaurant

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Restaurant
import com.example.fooduapp.domain.usecases.RestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val useCase: RestaurantUseCase
) : ViewModel() {

    var restaurantResponse by mutableStateOf<DataResponse<List<Restaurant>>?>(null)

    init {
        getRestaurants()
    }

    private fun getRestaurants() = viewModelScope.launch {
        restaurantResponse = DataResponse.Loading
        useCase.getRestaurants().collect {
            restaurantResponse = it
        }
    }
    suspend fun getRestaurantByName(name: String): DataResponse<Restaurant> {
        return useCase.getRestaurantByName(name)
    }
}
