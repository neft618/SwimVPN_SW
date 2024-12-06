package com.example.swimvpn.ui.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.swimvpn.viewmodel.PaymentViewModel

@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel,
    onNavigateBack: () -> Unit
) {
    val uiState by paymentViewModel.uiState.collectAsState()

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
                    paymentViewModel.selectSubscription(plan)
                }
            }
        }

        // Методы оплаты
        PaymentMethods(
            selectedMethod = uiState.selectedPaymentMethod,
            onMethodSelected = { paymentViewModel.selectPaymentMethod(it) }
        )

        // Кнопка оплаты
        Button(
            onClick = { paymentViewModel.processPayment() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)
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
        backgroundColor = if (plan.isSelected) Color.Cyan else Color.Gray
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