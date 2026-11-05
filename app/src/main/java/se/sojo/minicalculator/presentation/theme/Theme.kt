package se.sojo.minicalculator.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material3.ColorScheme
import androidx.wear.compose.material3.MaterialTheme

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme(
            background = Color(0xFF1A1B20),
            onBackground = Color(0xFFF9F9FF),
            primary = Color(0xFF3F4759),
            onPrimary = Color(0xFFFFFFFF),
            secondary = Color(0xFF1A1B20),
            onSecondary = Color(0xFFD9D9E0),
            tertiary = Color(0xFF16512C),
            onTertiary = Color(0xFFFFFFFF),
        ),
        content = content
    )
}