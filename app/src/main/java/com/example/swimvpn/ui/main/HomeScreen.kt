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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.R
import com.example.swimvpn.ui.theme.GeneralSans
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onNavigateToServers: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    val uiState = homeViewModel.uiState.collectAsState().value
    var isVpnConnected by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableStateOf(0L) }

    LaunchedEffect(isVpnConnected) {
        if (isVpnConnected) {
            while (true) {
                delay(1000L)
                elapsedTime += 1
            }
        } else {
            elapsedTime = 0L
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191A1B))
            .padding(16.dp), // Отступы от краев экрана
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(18.dp))
        Text("Secure Waters", color = Color.White, fontSize = 24.sp, fontFamily = GeneralSans)
        Spacer(modifier = Modifier.height(50.dp))

        // Реальные метрики подключения
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp) // Отступы от краев экрана
             // Распределение метрик по всей ширине
        ) {
            ConnectionMetric(
                label = "Download",
                value = uiState.downloadSpeed,
                iconResId = R.drawable.ic_download
            )
            Spacer(modifier = Modifier.width(50.dp)) // Пробел между метриками
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                ConnectionMetric(
                    label = "Upload",
                    value = uiState.uploadSpeed,
                    iconResId = R.drawable.ic_upload
                )
            }
            Spacer(modifier = Modifier.width(60.dp)) // Пробел между метриками
            ConnectionMetric(
                label = "Ping",
                value = uiState.ping,
                iconResId = R.drawable.ic_ping
            )
        }

        Spacer(modifier = Modifier.height(50.dp))

        // Секундомер
        val hours = (elapsedTime / 3600).toString().padStart(2, '0')
        val minutes = ((elapsedTime % 3600) / 60).toString().padStart(2, '0')
        val seconds = (elapsedTime % 60).toString().padStart(2, '0')
        Text(
            text = "$hours:$minutes:$seconds",
            color = Color.White,
            fontSize = 48.sp,
            fontFamily = GeneralSans
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Кнопка включения VPN с изображением и закругленными краями
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(52.dp))
                .background(Color(0xFF2C96AD))
                .clickable {
                    isVpnConnected = !isVpnConnected
                    homeViewModel.toggleVpnConnection()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connect),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
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
fun ConnectionMetric(label: String, value: String, iconResId: Int) {
    Column(
        modifier = Modifier.padding(horizontal = 8.dp), // Отступы между метриками
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
            color = Color(0xFF929292),
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
    val backgroundColor = if (isSelected) Color.Cyan else Color.Gray
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(80.dp)
            .background(color = backgroundColor, shape = RoundedCornerShape(16.dp))
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
