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
import com.example.swimvpn.ui.theme.GeneralSans
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
        Spacer(modifier = Modifier.height(250.dp))
        Text(
            text = "SOON",
            color = Color.White,
            fontSize = 60.sp,
            fontFamily = GeneralSans,
            modifier = Modifier.padding(bottom = 16.dp)

        )


    }
}

