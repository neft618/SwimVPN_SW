package com.example.swimvpn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// Модель подписки
data class SubscriptionPlan(
    val id: String,
    val duration: Int,  // Продолжительность в месяцах
    val price: Double,  // Цена в USD
    val isSelected: Boolean = false // Выбран ли этот план
)

// Состояние UI для PaymentScreen
data class PaymentUiState(
    val subscriptionPlans: List<SubscriptionPlan> = emptyList(),
    val selectedPaymentMethod: String? = null
)

class PaymentViewModel : ViewModel() {

    // Состояние UI
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    init {
        // Инициализация списка подписок
        loadSubscriptionPlans()
    }

    private fun loadSubscriptionPlans() {
        val plans = listOf(
            SubscriptionPlan(id = "1", duration = 1, price = 9.99),
            SubscriptionPlan(id = "2", duration = 6, price = 49.99),
            SubscriptionPlan(id = "3", duration = 12, price = 89.99)
        )
        _uiState.value = _uiState.value.copy(subscriptionPlans = plans)
    }

    fun selectSubscription(plan: SubscriptionPlan) {
        _uiState.value = _uiState.value.copy(
            subscriptionPlans = _uiState.value.subscriptionPlans.map {
                it.copy(isSelected = it.id == plan.id)
            }
        )
    }

    fun selectPaymentMethod(method: String) {
        _uiState.value = _uiState.value.copy(selectedPaymentMethod = method)
    }

    fun processPayment() {
        viewModelScope.launch {
            // Имитация процесса оплаты
            val selectedPlan = _uiState.value.subscriptionPlans.find { it.isSelected }
            val selectedMethod = _uiState.value.selectedPaymentMethod

            if (selectedPlan != null && selectedMethod != null) {
                // Выполните логику оплаты здесь (например, вызов API)
                println("Processing payment for plan: ${selectedPlan.duration} months, using method: $selectedMethod")
            } else {
                println("Error: No plan or payment method selected")
            }
        }
    }
}
