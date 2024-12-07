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

    init {
        loadServers() // Инициализация списка серверов
    }

    // Загрузка серверов. В будущем можно подключить репозиторий.
    private fun loadServers() {
        val servers = listOf(
            Server(id = "1", country = "USA"),
            Server(id = "2", country = "Germany"),
            Server(id = "3", country = "Japan"),
            Server(id = "4", country = "India"),
        )
        _uiState.update { it.copy(serverList = servers) }
    }

    // Выбор сервера
    fun selectServer(server: Server) {
        _uiState.update { currentState ->
            currentState.copy(
                serverList = currentState.serverList.map {
                    it.copy(isSelected = it.id == server.id) // Выбранный сервер помечается isSelected = true
                },
                selectedServer = server
            )
        }
    }
}