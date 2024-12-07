package com.example.swimvpn.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swimvpn.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onNavigateToServers: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    val uiState = homeViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Реальные метрики подключения
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ConnectionMetric(label = "Download", value = uiState.downloadSpeed)
            ConnectionMetric(label = "Upload", value = uiState.uploadSpeed)
            ConnectionMetric(label = "Ping", value = uiState.ping)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка включения VPN
        Button(
            onClick = { homeViewModel.toggleVpnConnection() },
            modifier = Modifier.size(120.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
        ) {
            Text(
                text = if (uiState.isConnected) "Disconnect" else "Connect",
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Список стран
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            items(uiState.serverList.size) { index ->
                val server = uiState.serverList[index]
                CountrySelector(
                    country = server.country,
                    isSelected = server.isSelected,
                    onClick = { homeViewModel.selectServer(server) }
                )
            }
        }
    }
}

@Composable
fun ConnectionMetric(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            color = Color.Cyan,
            fontSize = 18.sp
        )
    }
}

@Composable
fun CountrySelector(
    country: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.Cyan else Color.Gray
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(80.dp)
            .background(color = backgroundColor, shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = country,
            color = Color.White,
            fontSize = 14.sp
        )
    }
}