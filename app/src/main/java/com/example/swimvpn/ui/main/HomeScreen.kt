package com.example.swimvpn.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.swimvpn.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onNavigateToServers: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    val uiState by homeViewModel.uiState.collectAsState()

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
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)
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
            items(uiState.serverList) { server ->
                CountrySelector(
                    country = server.country,
                    isSelected = server.isSelected,
                    onClick = { homeViewModel.selectServer(server) }
                )
            }
        }
    }

    // Нижняя панель навигации
    BottomBar(
        onHomeClick = {},
        onServersClick = onNavigateToServers,
        onSettingsClick = onNavigateToPayment
    )
}

@Composable
fun ConnectionMetric(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = Color.White, fontSize = 18.sp)
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
    }
}