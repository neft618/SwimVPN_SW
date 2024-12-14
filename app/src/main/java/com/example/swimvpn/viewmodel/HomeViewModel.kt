package com.example.swimvpn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimvpn.data.model.Server
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Data class to represent the UI state of the Home screen
data class HomeUiState(
    val downloadSpeed: String = "0 Mb/s",
    val uploadSpeed: String = "0 Mb/s",
    val ping: String = "0 ms",
    val serverList: List<Server> = emptyList(),
    val isConnected: Boolean = false
)

class HomeViewModel : ViewModel() {

    // Mutable state for the UI
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    // Toggle VPN connection status
    fun toggleVpnConnection() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isConnected = !_uiState.value.isConnected
            )
        }
    }

    // Select a server from the server list
    fun selectServer(selectedServer: Server) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                serverList = _uiState.value.serverList.map { server ->
                    server.copy(isSelected = server == selectedServer)
                }
            )
        }
    }

    // Update the download speed, upload speed, and ping (mock example)
    fun updateConnectionMetrics(download: String, upload: String, ping: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                downloadSpeed = download,
                uploadSpeed = upload,
                ping = ping
            )
        }
    }

    // Simulate loading server list (replace with real data source)
    fun loadServers() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                serverList = listOf(
                    Server(id = "1", country = "USA", isSelected = true),
                    Server(id = "2", country = "Germany"),
                    Server(id = "3", country = "Japan")
                )
            )
        }
    }
}
