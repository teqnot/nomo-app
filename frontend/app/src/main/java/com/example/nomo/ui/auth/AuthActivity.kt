package com.example.nomo.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.nomo.viewmodel.AuthViewModel
import com.example.nomo.ui.navigation.AppNavigation
import com.example.nomo.ui.theme.NomoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomoTheme {
                AppNavigation(startDestination = "firstlogin")
            }
        }
    }
}