package com.example.swimvpn.data.model

// База данных LiteSQL для хранения информации о серверах и устройствах.
data class Server(
    val id: String,
    val country: String,
    val isSelected: Boolean = false
)