package com.example.swimvpn.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Определение цветовой схемы для темной темы
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1E88E5),      // Основной цвет
    onPrimary = Color.White,          // Цвет текста на основном фоне
    background = Color(0xFF121212),   // Фон приложения
    surface = Color(0xFF1E1E1E),      // Цвет поверхности
    onSurface = Color.White           // Цвет текста на поверхности
)

// Основная функция темы приложения
@Composable
fun SwimVPNTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography, // Типографика берется из Type.kt
        content = content
    )
}
