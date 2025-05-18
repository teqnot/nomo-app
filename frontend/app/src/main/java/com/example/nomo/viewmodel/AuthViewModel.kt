package com.example.nomo.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomo.managers.AuthManager
import com.example.nomo.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        object Success : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun resetState() {
        _authState.value = AuthState.Idle
    }

    fun registerUser(nickname: String, email: String, password: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = repository.registerUser(nickname, email, password)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.token != null) {
                        AuthManager.saveToken(context, body.token)
                        _authState.value = AuthState.Success
                    } else {
                        _authState.value = AuthState.Error("Token missing in response")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    _authState.value = AuthState.Error("Registration failed: $error")
                    AuthManager.clearToken(context)
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun loginUser(nickname: String, password: String, context: Context) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val response = repository.loginUser(nickname, password)
                if (response.isSuccessful) {
                    response.body()?.token?.let { token ->
                        AuthManager.saveToken(context, token)
                        _authState.value = AuthState.Success
                    } ?: run {
                        _authState.value = AuthState.Error("Token missing from response on login")
                    }
                } else {
                    val error = response.errorBody()?.string() ?: "Unknown error"
                    _authState.value = AuthState.Error("Login error: ${error.take(200)}")
                }
            } catch (e: Exception) {
                _authState.value =
                    AuthState.Error("Network error: ${e.localizedMessage ?: "Unkown error"}")
            }
        }
    }

    fun showError(message: String) {
        _authState.value = AuthState.Error(message)
    }
}