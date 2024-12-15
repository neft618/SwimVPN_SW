package com.example.swimvpn.ui.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.swimvpn.ui.theme.GeneralSans
import com.example.swimvpn.viewmodel.PaymentViewModel
import com.example.swimvpn.viewmodel.SubscriptionPlan
import com.example.swimvpn.viewmodel.AuthViewModel

@Composable
fun PaymentScreen(
    paymentViewModel: PaymentViewModel,
    authViewModel: AuthViewModel,
    onNavigateBack: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState()
    val savedUsername by authViewModel.username.collectAsState()

    val uiState = paymentViewModel.uiState.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF191A1B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Secure Waters",
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = GeneralSans
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isAuthenticated) {
            Text(text = "Logged in as $savedUsername", color = Color.White, fontSize = 16.sp)
        } else {
            TransparentOutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = "Username"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TransparentOutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    authViewModel.authenticate(username, password) { authenticated ->
                        if (authenticated) {
                            onLoginSuccess()
                        } else {
                            errorMessage = "Invalid credentials"
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5D6C)),
                shape = RoundedCornerShape(12.dp), // Rounded corners
                modifier = Modifier.width(100.dp)

            ) {
                Text("Login", color = Color.White)
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = errorMessage, color = Color.Red, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

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

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { paymentViewModel.processPayment() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5D6C)),
            shape = RoundedCornerShape(12.dp) // Rounded corners
        ) {
            Text(text = "Connect your wallet", color = Color.White)
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransparentOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false
) {
    val visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    val borderColor = Color(0xFF1B5D6C)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color.Gray) },
        singleLine = true,
        visualTransformation = visualTransformation,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
            unfocusedBorderColor = borderColor,
            focusedBorderColor = borderColor,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    )
}

@Composable
fun SubscriptionCard(plan: SubscriptionPlan, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(120.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (plan.isSelected) Color(0xFF1B5D6C) else Color(0xFF151617)
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "${plan.duration} months", color = Color.White)
            Text(text = "${plan.price} USD", color = Color.White)
        }
    }
}