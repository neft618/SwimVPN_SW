package com.example.swimvpn.ui.servers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import com.example.swimvpn.viewmodel.ServerViewModel

@Composable
fun ServerScreen(
    serverViewModel: ServerViewModel,
    onNavigateToPayment: () -> Unit
) {
    val uiState = serverViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Devices",
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Список серверов
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.serverList.size) { index ->
                val server = uiState.serverList[index]
                ServerItem(
                    serverName = server.country,
                    isSelected = server.isSelected,
                    onClick = { serverViewModel.selectServer(server) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onNavigateToPayment) {
            Text(text = "Go to Payment", color = Color.White)
        }
    }
}

@Composable
fun ServerItem(serverName: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color.Cyan else Color.Gray
        )
    ) {
        Text(text = serverName, color = Color.Black)
    }
}

