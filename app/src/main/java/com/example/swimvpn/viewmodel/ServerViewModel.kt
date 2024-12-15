package com.example.swimvpn.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.swimvpn.data.model.Server

// Определяем состояние UI для работы с серверами.
data class ServerUiState(
    val serverList: List<Server> = emptyList(),
    val selectedServer: Server? = null
)

class ServerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServerUiState())
    val uiState: StateFlow<ServerUiState> = _uiState


}