package com.example.swimvpn.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
        }
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
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
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
                label = { Text(text = screen.route) },
                icon = {
                    // Добавьте иконки для каждого экрана (пример с Placeholder)
                    Icon(Icons.Default.Home, contentDescription = null) // Замените на нужные иконки
                }
            )
        }
    }
}