package com.example.fooduapp.ui.order

import com.example.fooduapp.data.service.AccountService
import com.example.fooduapp.ui.FoodUAppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {
    fun onSignOutClick() {
        launchCatching {
            accountService.signOut()
        }
    }
}