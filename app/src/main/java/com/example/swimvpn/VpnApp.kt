package com.example.swimvpn


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.datastore.core.Storage
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.swimvpn.navigation.AppNavigation
import com.example.swimvpn.ui.theme.SwimVPNTheme

private val Any.Payment: ImageVector
    get() {        TODO("Not yet implemented")
    }
private val Any.Storage: ImageVector
    get() {
        TODO("Not yet implemented")
    }

@Composable
fun VpnApp() {
    SwimVPNTheme {
        // Обертка для обработки Material Design 3
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()

            Column {
                // Навигация приложения
                AppNavigation(navController = navController)

                // Нижняя панель навигации
                BottomNavigationBar(navController)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Определяем элементы нижней панели навигации
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Servers,
        BottomNavItem.Payment
    )

    NavigationBar(
        containerColor = Color(0xFF1A1A1A),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (navController.currentDestination?.route == item.route) Color.Cyan else Color.White
                    )
                },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    if (navController.currentDestination?.route != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Cyan,
                    selectedTextColor = Color.Cyan,
                    unselectedIconColor = Color.White.copy(alpha = 0.6f),
                    unselectedTextColor = Color.White.copy(alpha = 0.6f)
                )
            )
        }
    }
}

sealed class BottomNavItem(val title: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("Home", Icons.Default.Home, "home")
    object Servers : BottomNavItem("Servers", Icons.Default.Storage, "servers")
    object Payment : BottomNavItem("Payment", Icons.Default.Payment, "payment")
}
