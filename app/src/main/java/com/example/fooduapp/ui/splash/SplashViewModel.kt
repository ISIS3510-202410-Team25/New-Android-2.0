package com.example.fooduapp.ui.splash

import androidx.lifecycle.ViewModel
import com.example.fooduapp.data.service.AccountService
import com.example.fooduapp.ui.navigation.HomeNavGraph
import com.example.fooduapp.ui.sign_in.SignInDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {
    fun onAppStart(openAndPopUp: (String, String) -> Unit) {
        if (accountService.hasUser()) openAndPopUp(HomeNavGraph.route, SplashDestination.route)
        else openAndPopUp(SignInDestination.route, SplashDestination.route)
    }
}