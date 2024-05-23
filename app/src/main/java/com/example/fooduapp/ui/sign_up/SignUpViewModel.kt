package com.example.fooduapp.ui.sign_up

import com.example.fooduapp.data.SignUpUiState
import com.example.fooduapp.data.service.AccountService
import com.example.fooduapp.ui.FoodUAppViewModel
import com.example.fooduapp.ui.navigation.HomeNavGraph
import com.example.fooduapp.ui.sign_in.SignInDestination
import com.example.fooduapp.util.isValidEmail
import com.example.fooduapp.util.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : FoodUAppViewModel() {

    // Expose the state of Sign Up fields
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun updateUsername(newUsername: String) {
        _uiState.update {currentState ->
            currentState.copy(
                username = newUsername
            )
        }
    }

    fun updateEmail(newEmail: String) {
        _uiState.update {currentState ->
            currentState.copy(
                email = newEmail
            )
        }
    }

    fun updatePassword(newPassword: String) {
        _uiState.update {currentState ->
            currentState.copy(
                password = newPassword
            )
        }
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {

            if(_uiState.value.username.isEmpty()) {
                _uiState.update { currentState ->
                    currentState.copy(
                        invalidUsername = true
                    )
                }
            } else {
                _uiState.update {currentState ->
                    currentState.copy(
                        invalidUsername = false
                    )
                }
            }

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

            if (_uiState.value.invalidUsername || _uiState.value.invalidEmail || _uiState.value.invalidPassword)
                throw IllegalArgumentException("Fields are not correct")

            accountService.signUp(_uiState.value.email, _uiState.value.password)
            openAndPopUp(HomeNavGraph.route, SignUpDestination.route)
        }
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        openAndPopUp(SignInDestination.route, SignUpDestination.route)
    }
}