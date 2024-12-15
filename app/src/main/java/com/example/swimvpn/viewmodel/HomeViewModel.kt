package com.example.swimvpn.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.swimvpn.data.database.ProxyDatabaseHelper
import com.example.swimvpn.data.model.Server
import com.example.swimvpn.service.ProxyVpnService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class HomeUiState(
    val downloadSpeed: String = "0 Mb/s",
    val uploadSpeed: String = "0 Mb/s",
    val ping: String = "0 ms",
    val serverList: List<Server> = emptyList(),
    val isConnected: Boolean = false,
    val elapsedTime: Long = 0L
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val proxyDatabaseHelper = ProxyDatabaseHelper(application)
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    private var selectedServer: Server? = null

    init {
        loadServers()
        startTimer()
    }

    private fun loadServers() {
        viewModelScope.launch {
            val servers = proxyDatabaseHelper.getAllServers()
            _uiState.value = _uiState.value.copy(serverList = servers)
            selectedServer = servers.firstOrNull { it.isSelected } ?: servers.firstOrNull()
        }
    }

    fun toggleVpnConnection() {
        selectedServer?.let { server ->
            val context = getApplication<Application>()
            val intent = Intent(context, ProxyVpnService::class.java).apply {
                putExtra("ip", server.ip)
                putExtra("port", server.port)
                putExtra("password", server.password)
            }

            if (_uiState.value.isConnected) {
                context.stopService(intent)
                _uiState.value = _uiState.value.copy(isConnected = false, elapsedTime = 0L)
            } else {
                context.startService(intent)
                _uiState.value = _uiState.value.copy(isConnected = true)
            }
        }
    }

    fun selectServer(server: Server) {
        selectedServer = server
        _uiState.value = _uiState.value.copy(
            serverList = _uiState.value.serverList.map { it.copy(isSelected = it == server) }
        )
    }

    fun updateConnectionMetrics(download: String, upload: String, ping: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                downloadSpeed = download,
                uploadSpeed = upload,
                ping = ping
            )
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                if (_uiState.value.isConnected) {
                    _uiState.value = _uiState.value.copy(elapsedTime = _uiState.value.elapsedTime + 1)
                }
            }
        }
    }
}