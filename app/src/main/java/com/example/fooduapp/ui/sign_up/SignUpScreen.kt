package com.example.fooduapp.ui.sign_up

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fooduapp.util.rememberImeState
import com.example.fooduapp.R
import com.example.fooduapp.ui.navigation.NavigationDestination
import com.example.fooduapp.ui.navigation.NavigationDestinationLogin
import com.example.fooduapp.ui.theme.FoodUAppTheme

object SignUpDestination : NavigationDestinationLogin {
    override val route = "SignUpScreen"
}

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit
) {

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val passwordVisible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    // The state of the Sign Up fields
    val signUpUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(R.drawable.salad),
            contentDescription = "Bowl Salad Banner"
        )
        TabRow(selectedTabIndex = 1, containerColor = Color.Transparent) {
            Tab(
                selected = false,
                onClick = { viewModel.onSignInClick(openAndPopUp) },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign In")
            }
            Tab(
                selected = true,
                onClick = { /*TODO*/ },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = "Sign Up")
            }
        }

        OutlinedTextField(
            value = signUpUiState.username,
            onValueChange = { viewModel.updateUsername(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            label = { Text(text = stringResource(R.string.username_label)) },
            placeholder = { Text(text = stringResource(R.string.username_placeholder)) },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Username icon"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = signUpUiState.invalidUsername
        )
        if (signUpUiState.invalidUsername) {
            Text(
                text = stringResource(R.string.invalid_username),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        } else {
            Box {

            }
        }
        OutlinedTextField(
            value = signUpUiState.email,
            onValueChange = { viewModel.updateEmail(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            label = { Text(text = stringResource(R.string.email_label)) },
            placeholder = { Text(text = stringResource(R.string.email_placeholder)) },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon"
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            isError = signUpUiState.invalidEmail
        )

        if (signUpUiState.invalidEmail) {
            Text(
                text = stringResource(R.string.invalid_email),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box{

            }
        }

        OutlinedTextField(
            value = signUpUiState.password,
            onValueChange = { viewModel.updatePassword(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 16.dp),
            label = { Text(text = stringResource(R.string.password_label)) },
            placeholder = { Text(text = stringResource(R.string.password_placeholder)) },
            singleLine = true,
            maxLines = 1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Email icon"
                )
            },
            trailingIcon = {
                val iconImage = if(passwordVisible.value) {
                    Icons.Filled.Visibility
                } else {
                    Icons.Filled.VisibilityOff
                }

                val description = if(passwordVisible.value) {
                    "Hide Password"
                } else {
                    "Show Password"
                }

                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        imageVector = iconImage,
                        contentDescription = description
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            isError = signUpUiState.invalidPassword
        )
        if (signUpUiState.invalidPassword) {
            Text(
                text = stringResource(R.string.invalid_password),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )
        } else {
            Box {

            }
        }
        Button(
            onClick = { viewModel.onSignUpClick(openAndPopUp) },
            modifier = modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
        ) {
            Text(stringResource(R.string.registration))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FoodUAppTheme {
        SignUpScreen(openAndPopUp = {_, _ ->})
    }
}