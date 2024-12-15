package com.example.swimvpn.data.model

// База данных LiteSQL для хранения информации о серверах и устройствах.

data class Server(
    val id: String,
    val country: String,
    val ip: String,
    val port: Int,
    val username: String,
    val password: String,
    val isSelected: Boolean = false
)