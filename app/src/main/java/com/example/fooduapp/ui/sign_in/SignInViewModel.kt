package com.example.fooduapp.ui.sign_in

import com.example.fooduapp.data.SignInUiState
import com.example.fooduapp.data.service.AccountService
import com.example.fooduapp.ui.FoodUAppViewModel
import com.example.fooduapp.ui.navigation.HomeNavGraph
import com.example.fooduapp.ui.sign_up.SignUpDestination
import com.example.fooduapp.util.isValidEmail
import com.example.fooduapp.util.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {

    // State of the Sign In fields
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun updateEmail(enterEmail: String) {
        _uiState.update {currentState ->
            currentState.copy(
                email = enterEmail
            )
        }
    }

    fun updatePassword(enterPassword: String) {
        _uiState.update {currentState ->
            currentState.copy(
                password = enterPassword
            )
        }
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if(!_uiState.value.email.isValidEmail()) {
                _uiState.update {currentState ->
                    currentState.copy(
                        invalidEmail = true
                    )
                }
            } else {
                _uiState.update {currentState ->
                    currentState.copy(
                        invalidEmail = false
                    )
                }
            }

            if(!_uiState.value.password.isValidPassword()) {
                _uiState.update {currentState ->
                    currentState.copy(
                        invalidPassword = true
                    )
                }
            } else {
                _uiState.update {currentState ->
                    currentState.copy(
                        invalidPassword = false
                    )
                }
            }

            if (_uiState.value.invalidEmail || _uiState.value.invalidPassword)
                throw IllegalArgumentException("Fields are not correct")

            accountService.signIn(_uiState.value.email, _uiState.value.password)
            openAndPopUp(HomeNavGraph.route, SignInDestination.route)
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SignUpDestination.route, SignInDestination.route)
    }
}