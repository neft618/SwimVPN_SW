package com.example.swimvpn.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.swimvpn.navigation.AppNavigation
import com.example.swimvpn.navigation.Screen

@Composable
fun MainScreen(navController: NavHostController) {
    val items = listOf(
        Screen.Servers,
        Screen.Home,
        Screen.Payment
    )

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = items)
        },
        containerColor = Color.Transparent // Устанавливаем прозрачный фон для Scaffold
    ) { innerPadding ->
        AppNavigation(
            navController = navController
        )
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<Screen>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp) // Поднимаем кнопки выше
            .background(Color.Transparent) // Устанавливаем прозрачный фон для NavigationBar
    ) {
        Spacer(modifier = Modifier.height(16.dp)) // Добавляем пространство сверху для поднятия кнопок

        NavigationBar(
            containerColor = Color.Transparent, // Устанавливаем прозрачный фон для NavigationBar
            contentColor = Color.White // Цвет иконок и текста
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { screen ->
                val isSelected = currentRoute == screen.route
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                // Удаление предыдущих экранов из стека
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { /* No label */ },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .background(
                                    color = if (isSelected) Color(0xFF1A5F6F) else Color(0xFF141516),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = screen.icon),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                tint = if (isSelected) Color.White else Color.White
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = Color.White,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}
