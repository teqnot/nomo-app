package com.example.nomo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nomo.repository.FriendRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import com.example.nomo.network.ApiService.Friend
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FriendViewModel @Inject constructor(
    private val repository: FriendRepository
) : ViewModel() {

    sealed class FriendState {
        object Idle : FriendState()
        object Loading : FriendState()
        data class Success(val friends : List<Friend>) : FriendState()
        data class Error(val message: String) : FriendState()
    }

    private val _friendState = MutableStateFlow<FriendState>(FriendState.Idle)
    val friendState: StateFlow<FriendState> = _friendState

    fun loadFriends(userId: Long) {
        viewModelScope.launch {
            _friendState.value = FriendState.Loading
            try {
                val response = repository.loadFriends(userId)
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _friendState.value = FriendState.Success(response.body()!!)
                } else {
                    val error = response.errorBody()?.string() ?: "Unkown error"
                    _friendState.value = FriendState.Error(error)
                }
            } catch (e: Exception) {
                _friendState.value = FriendState.Error(e.localizedMessage ?: "Network error")
            }
        }
    }

    fun resetState() {
        _friendState.value = FriendState.Idle
    }
}