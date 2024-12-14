package com.example.swimvpn.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.R

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
            .background(Color(0xFF191A1B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text("Secure Waters", color = Color.White, fontSize = 24.sp)
        Spacer(modifier = Modifier.height(50.dp))

        // Реальные метрики подключения
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ConnectionMetric(label = "Download", value = uiState.downloadSpeed)
            ConnectionMetric(label = "Upload", value = uiState.uploadSpeed)
            ConnectionMetric(label = "Ping", value = uiState.ping)
        }

        Spacer(modifier = Modifier.height(200.dp))

        // Кнопка включения VPN с изображением и закругленными краями
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color(0xFF2C96AD))
                .clickable { homeViewModel.toggleVpnConnection() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connect),
                contentDescription = null,
                modifier = Modifier.size(200.dp).clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
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
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp)) // Закругленные края с радиусом 16.dp
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
