package com.example.swimvpn.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swimvpn.ui.main.HomeScreen
import com.example.swimvpn.ui.servers.ServerScreen
import com.example.swimvpn.ui.payment.PaymentScreen
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.viewmodel.ServerViewModel
import com.example.swimvpn.viewmodel.PaymentViewModel

// Список маршрутов
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Servers : Screen("servers")
    object Payment : Screen("payment")
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Экран "Домашняя страница"
        composable(route = Screen.Home.route) {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
                onNavigateToServers = { navController.navigate(Screen.Servers.route) },
                onNavigateToPayment = { navController.navigate(Screen.Payment.route) }
            )
        }

        // Экран "Серверы"
        composable(route = Screen.Servers.route) {
            val serverViewModel: ServerViewModel = viewModel()
            ServerScreen(
                serverViewModel = serverViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // Экран "Оплата"
        composable(route = Screen.Payment.route) {
            val paymentViewModel: PaymentViewModel = viewModel()
            PaymentScreen(
                paymentViewModel = paymentViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
