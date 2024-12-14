package com.example.swimvpn.navigation

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.swimvpn.R
import com.example.swimvpn.ui.main.HomeScreen
import com.example.swimvpn.ui.servers.ServerScreen
import com.example.swimvpn.ui.payment.PaymentScreen
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.viewmodel.ServerViewModel
import com.example.swimvpn.viewmodel.PaymentViewModel

sealed class Screen(val route: String, @DrawableRes val icon: Int) {
    object Servers : Screen("servers", R.drawable.ic_servers)
    object Home : Screen("home", R.drawable.ic_home)
    object Payment : Screen("payment", R.drawable.ic_payment)
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
                onNavigateToPayment = { navController.navigate(Screen.Payment.route) }
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
