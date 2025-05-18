package com.example.nomo.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nomo.R
import com.example.nomo.viewmodel.AuthViewModel
import com.example.nomo.ui.common.CustomTextField
import com.example.nomo.ui.login.AuthButtons
import com.example.nomo.ui.login.BackgroundWithText
import com.example.nomo.ui.theme.NomoTheme
import com.example.nomo.utils.Validators

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    NomoTheme {
        LoginScreen(
            onBack = {},
            onLoginSuccess = {},
            onRegisterPressed = {},
            viewModel = viewModel()
        )
    }
}

@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    onRegisterPressed: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var nickname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthViewModel.AuthState.Success -> {
                onLoginSuccess()
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
                    .align(Alignment.TopStart)
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
            LoginFields(
                nickname = nickname,
                onNicknameChange = { if (!isLoading) nickname = it },
                password = password,
                onPasswordChange = { if (!isLoading) password = it }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            AuthButtons(
                onLogin = {
                    when {
                        !Validators.isValidNickname(nickname) -> {
                            viewModel.showError("Некорректный формат nickname")
                        }
                        !Validators.isValidPassword(password) -> {
                            viewModel.showError("Пароль должен быть не менее 6 символов")
                        }
                        else -> {
                            viewModel.loginUser(
                                nickname = nickname,
                                password = password,
                                context = context
                            )
                        }
                    }
                },
                onRegister = onRegisterPressed
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun LoginFields(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    CustomTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        placeholder = "Никнейм",
        leadingIcon = R.drawable.ic_register_person
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