package com.example.swimvpn.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.R
import com.example.swimvpn.ui.theme.GeneralSans
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
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
        Text(
            text = "Secure Waters",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = GeneralSans
        )
        Spacer(modifier = Modifier.height(50.dp))

        // Метрики подключения
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ConnectionMetric(label = "Download", value = uiState.downloadSpeed, iconResId = R.drawable.ic_download)
            ConnectionMetric(label = "Upload", value = uiState.uploadSpeed, iconResId = R.drawable.ic_upload)
            ConnectionMetric(label = "Ping", value = uiState.ping, iconResId = R.drawable.ic_ping)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Таймер соединения
        val hours = (uiState.elapsedTime / 3600).toString().padStart(2, '0')
        val minutes = ((uiState.elapsedTime % 3600) / 60).toString().padStart(2, '0')
        val seconds = (uiState.elapsedTime % 60).toString().padStart(2, '0')
        Text(
            text = "$hours:$minutes:$seconds",
            color = Color.White,
            fontSize = 48.sp,
            fontFamily = GeneralSans
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Кнопка подключения
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(52.dp))
                .background(Color.Transparent)
                .clickable { homeViewModel.toggleVpnConnection() },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connect),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .alpha(if (uiState.isConnected) 1f else 0.5f),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Список серверов
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
fun ConnectionMetric(label: String, value: String, iconResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = label,
            color = Color(0xFF888889),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp,
            fontFamily = GeneralSans
        )
    }
}

@Composable
fun CountrySelector(
    country: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFF1B5D6C) else Color(0xFF151617)
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(70.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(30.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = country,
            color = Color.White,
            fontSize = 14.sp,
            fontFamily = GeneralSans
        )
    }
}