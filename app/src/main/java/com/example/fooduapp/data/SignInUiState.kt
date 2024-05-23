package com.example.fooduapp.data

data class SignInUiState (
    val email: String = "",
    val password: String = "",
    val invalidEmail: Boolean = false,
    val invalidPassword: Boolean = false
)