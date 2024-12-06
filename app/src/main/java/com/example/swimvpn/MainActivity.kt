package com.example.swimvpn
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.swimvpn.ui.main.HomeScreen
import com.example.swimvpn.ui.servers.ServerScreen
import com.example.swimvpn.ui.payment.PaymentScreen
import com.example.swimvpn.viewmodel.HomeViewModel
import com.example.swimvpn.viewmodel.ServerViewModel
import com.example.swimvpn.viewmodel.PaymentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Создание контроллера навигации
            val navController = rememberNavController()

            // Настройка приложения
            VpnApp(navController = navController)
        }
    }
}
@Composable
fun VpnApp(navController: NavHostController) {
    // Главный хост навигации
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable(route = "home") {
            val homeViewModel: HomeViewModel = viewModel()
            HomeScreen(
                homeViewModel = homeViewModel,
                onNavigateToServers = { navController.navigate("servers") },
                onNavigateToPayment = { navController.navigate("payment") }
            )
        }

        composable(route = "servers") {
            val serverViewModel: ServerViewModel = viewModel()
            ServerScreen(
                serverViewModel = serverViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(route = "payment") {
            val paymentViewModel: PaymentViewModel = viewModel()
            PaymentScreen(
                paymentViewModel = paymentViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
