package com.example.swimvpn.ui.servers

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.swimvpn.viewmodel.ServerViewModel

@Composable
fun ServerScreen(
    serverViewModel: ServerViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by serverViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Text(
            text = "Devices",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiState.devices) { device ->
                DeviceCard(device = device, onRemove = { serverViewModel.removeDevice(device) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Private Servers",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(uiState.privateServers) { server ->
                ServerCard(server = server, onBuy = { serverViewModel.buyServer(server) })
            }
        }
    }

    // Навигация назад
    BackButton(onClick = onNavigateBack)
}