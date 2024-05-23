package com.example.fooduapp.ui.sign_in

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fooduapp.R
import com.example.fooduapp.ui.navigation.NavigationDestinationLogin
import com.example.fooduapp.util.rememberImeState

object SignInDestination : NavigationDestinationLogin {
    override val route = "SignInScreen"
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    openAndPopUp: (String, String) -> Unit
) {
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    // The state of the Sign In fields
    val signInUiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(R.drawable.salad),
            contentDescription = "Bowl Salad Banner"
        )

        TabRow(selectedTabIndex = 0, containerColor = Color.Transparent) {
            Tab(
                selected = true,
                onClick = { /*TODO*/ },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = stringResource(R.string.sign_in))
            }
            Tab(
                selected = false,
                onClick = { viewModel.onSignUpClick(openAndPopUp) },
                modifier = Modifier.height(56.dp)
            ) {
                Text(text = stringResource(R.string.sign_up))
            }
        }

        OutlinedTextField(
            value = signInUiState.email,
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
            isError = signInUiState.invalidEmail,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            )
        )

        if (signInUiState.invalidEmail) {
            Text(
                text = stringResource(R.string.invalid_email),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box {

            }
        }

        OutlinedTextField(
            value = signInUiState.password,
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
            isError = signInUiState.invalidPassword
        )
        if (signInUiState.invalidPassword) {
            Text(
                text = stringResource(R.string.invalid_password),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 16.dp)
            )
        } else {
            Box {

            }
        }

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = { /*TODO*/ },
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily.Default,
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.tertiary
            ),
            modifier = Modifier
                .padding(top = 8.dp, end = 16.dp)
                .align(Alignment.End)
        )

        Button(
            onClick = { viewModel.onSignInClick(openAndPopUp) },
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = stringResource(R.string.login))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp
            )
            Text(
                text = "or",
                textAlign = TextAlign.Center,
                modifier = modifier.padding(horizontal = 8.dp)
            )
            Divider(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                thickness = 1.dp
            )
        }
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            IconButton(
                onClick = { },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Google Icon",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp)
                )
            }
        }
    }
}