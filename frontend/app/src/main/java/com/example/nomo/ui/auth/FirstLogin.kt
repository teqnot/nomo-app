package com.example.nomo.ui.auth

import BackgroundWithLogo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.nomo.ui.firstlogin.BottomButtons

@Composable
fun FirstLogin(onNavigateToRegistration: () -> Unit,
               onNavigateToLogin: () -> Unit,
               modifier: Modifier = Modifier) {
    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.systemBars)) {
        // Фон
        BackgroundWithLogo()

        // Кнопки
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            BottomButtons(
                onLoginClick = onNavigateToLogin,
                onRegisterClick = onNavigateToRegistration  // Navigate to RegistrationScreen
            )
        }
    }
}