package com.example.fooduapp.domain.model

import java.lang.Exception

sealed class DataResponse<out T> {
    data object Loading: DataResponse<Nothing>()
    data class Success<out T>(val data: T): DataResponse<T>()
    data class Failure<out T>(val exception: Exception?): DataResponse<T>()
}