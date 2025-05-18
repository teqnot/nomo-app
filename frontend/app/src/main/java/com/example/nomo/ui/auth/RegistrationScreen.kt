package com.example.nomo.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nomo.R
import com.example.nomo.viewmodel.AuthViewModel
import com.example.nomo.ui.common.CustomTextField
import com.example.nomo.ui.registration.AuthButtons
import com.example.nomo.ui.registration.BackgroundWithText
import com.example.nomo.ui.theme.*
import com.example.nomo.utils.Validators

@Preview(showBackground = true)
@Composable
fun RegistrationScreenPreview() {
    NomoTheme {
        RegistrationScreen(
            onBack = {},
            onRegisterSuccess = {},
            onLoginPressed = {},
            viewModel = viewModel()
        )
    }
}

@Composable
fun RegistrationScreen(
    onBack: () -> Unit,
    onRegisterSuccess: () -> Unit,
    onLoginPressed: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var nickname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                onRegisterSuccess()
                viewModel.resetState()
            }
            is AuthViewModel.AuthState.Error -> {
                val error = (authState as AuthViewModel.AuthState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    val isLoading = authState == AuthViewModel.AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            BackgroundWithText()

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.TopStart),
                enabled = !isLoading
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = "Назад"
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            RegistrationFields(
                nickname = nickname,
                onNicknameChange = { if (!isLoading) nickname = it },
                email = email,
                onEmailChange = { if (!isLoading) email = it },
                password = password,
                onPasswordChange = { if (!isLoading) password = it },
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(32.dp))

            when (authState) {
                AuthViewModel.AuthState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                is AuthViewModel.AuthState.Error -> {
                    Text(
                        text = (authState as AuthViewModel.AuthState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                else -> {}
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            AuthButtons(
                onRegister = {
                    when {
                        !Validators.isValidNickname(nickname) -> {
                            viewModel.showError("Никнейм должен быть от 3 до 20 символов")
                        }
                        !Validators.isValidEmail(email) -> {
                            viewModel.showError("Некорректный формат email")
                        }
                        !Validators.isValidPassword(password) -> {
                            viewModel.showError("Пароль должен быть не менее 6 символов")
                        }
                        else -> {
                            viewModel.registerUser(
                                nickname = nickname,
                                email = email,
                                password = password,
                                context = context
                            )
                        }
                    }
                },
                onLogin = onLoginPressed
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun RegistrationFields(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    enabled: Boolean = true
) {
    CustomTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        placeholder = "Никнейм",
        leadingIcon = R.drawable.ic_register_person
    )

    Spacer(modifier = Modifier.height(24.dp))

    CustomTextField(
        value = email,
        onValueChange = onEmailChange,
        placeholder = "Email",
        leadingIcon = R.drawable.ic_register_mail,
        keyboardType = KeyboardType.Email
    )

    Spacer(modifier = Modifier.height(24.dp))

    CustomTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = "Пароль",
        leadingIcon = R.drawable.ic_register_lock,
        isPasswordField = true
    )
}