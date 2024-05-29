package com.example.fooduapp.ui.restaurant

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooduapp.domain.model.DataResponse
import com.example.fooduapp.domain.model.Food
import com.example.fooduapp.domain.model.Restaurant
import com.example.fooduapp.domain.usecases.RestaurantUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantViewModel @Inject constructor(
    private val useCase: RestaurantUseCase
) : ViewModel() {

    var restaurantResponse by mutableStateOf<DataResponse<List<Restaurant>>?>(null)
    private val _foodResponse = MutableStateFlow<DataResponse<List<Food>>?>(null)
    val foodResponse: StateFlow<DataResponse<List<Food>>?> get() = _foodResponse
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

    fun getFoodsByRestaurantName(restaurantName: String) = viewModelScope.launch {
        _foodResponse.value = DataResponse.Loading
        val restaurantResponse = useCase.getRestaurantByName(restaurantName)
        if (restaurantResponse is DataResponse.Success) {
            val menu = restaurantResponse.data.menu
            // Aquí puedes implementar la lógica para obtener los detalles de los alimentos si es necesario
            // Por ahora, simplemente mapearemos los nombres de los alimentos a objetos Food con nombres
            val foods = menu.map { Food(name = it, img = "", rating = 0.0) } // Ajusta los valores según tus necesidades
            _foodResponse.value = DataResponse.Success(foods)
        } else {
            _foodResponse.value = DataResponse.Failure(Exception("Restaurant not found"))
        }
    }
}
