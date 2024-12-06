package com.example.swimvpn.ui.components

import androidx.compose.runtime.Composable

@Composable
fun BottomBar(
    onHomeClick: () -> Unit,
    onServersClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onServersClick,
            icon = { Icon(Icons.Default.List, contentDescription = "Servers") }
        )
        BottomNavigationItem(
            selected = false,
            onClick = onSettingsClick,
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
    }
}