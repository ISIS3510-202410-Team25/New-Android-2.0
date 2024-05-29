package com.example.fooduapp.ui.navigation

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    abstract fun observer(): Flow<Status>

    enum class Status {
        Available, Unavailable, Losing, Lost
    }
}