package se.sojo.minicalculator.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TextButton

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    ) {
    TextButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier,
            text = text,
            fontSize = 14.sp // TODO: Adjust fontsize for different screens
        )
    }
}