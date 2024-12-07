package com.example.swimvpn.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.swimvpn.viewmodel.PaymentViewModel
import com.example.swimvpn.viewmodel.SubscriptionPlan

// Фейковый класс SubscriptionPlan (здесь нужно использовать ваш настоящий класс данных)
data class SubscriptionPlan(
    val id: String,
    val duration: Int,  // Продолжительность в месяцах
    val price: Double,  // Цена в USD
    val isSelected: Boolean = false // Выбран ли этот план
)

@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel,
    onNavigateBack: () -> Unit
) {
    // Подписка на состояние UI из PaymentViewModel
    val uiState = paymentViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Карточки подписок
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            uiState.subscriptionPlans.forEach { plan ->
                SubscriptionCard(plan = plan) {
                    paymentViewModel.selectSubscription(plan) // Выбор плана
                }
            }
        }

        // Методы оплаты (фейковая реализация, добавьте ваш код)
        PaymentMethods(
            selectedMethod = uiState.selectedPaymentMethod,
            onMethodSelected = { method -> paymentViewModel.selectPaymentMethod(method) }
        )

        // Кнопка оплаты
        Button(
            onClick = { paymentViewModel.processPayment() }, // Начать оплату
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan) // Изменён параметр backgroundColor
        ) {
            Text(text = "Connect your wallet", color = Color.Black)
        }
    }

    // Навигация назад
    BackButton(onClick = onNavigateBack)
}

@Composable
fun SubscriptionCard(plan: SubscriptionPlan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clickable(onClick = onClick),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = if (plan.isSelected) Color.Cyan else Color.Gray
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${plan.duration} months", color = Color.White)
            Text(text = "${plan.price} USD", color = Color.White)
        }
    }
}

// Фейковый метод PaymentMethods (должен быть заменён на ваш собственный)
@Composable
fun PaymentMethods(selectedMethod: String?, onMethodSelected: (String) -> Unit) {
    // Пример: добавить кнопку выбора метода
    Column {
        Text(
            text = "Payment Methods",
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
        // Здесь вы можете добавить методы оплаты (например, кнопки выбора)
    }
}

// Фейковый метод BackButton (замените на ваш собственный)
@Composable
fun BackButton(onClick: () -> Unit) {
    Text(
        text = "Back",
        color = Color.White,
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = onClick)
    )
}