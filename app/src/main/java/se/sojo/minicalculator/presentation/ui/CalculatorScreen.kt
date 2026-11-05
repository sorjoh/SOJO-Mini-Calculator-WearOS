package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.Text
import se.sojo.minicalculator.presentation.Calculator

@Composable
fun CalculatorScreen(
    calculator: Calculator,
    isScreenRound: Boolean = false,
    width: Int,
    height: Int,
    backgroundColor: Color,
    viewModel: CalculatorViewModel = CalculatorViewModel() )
{
    // Include the calculator class in the viewmodel
    viewModel.c = calculator

    // Needed for the display output
    LaunchedEffect(key1 = viewModel.display.value) {
        if (calculator.debug)
            viewModel.display.value.let { Log.d("SOJO Debug:", "CalculatorScreen : display=$it") }
    }

    // Show values for debugging in Logcat
    if (calculator.debug)
        Log.d("SOJO Debug:", "CalculatorScreen : isScreenRound=$isScreenRound, width=$width, height=$height")

    // Adjust the padding based on the type and size of wearable
    //      Square = 360x360
    //      Small Round = 384x384
    //      Rectangular = 402x476
    //      Large Round = 454x454
    val padding = when (width) {
        // Square (360x360)
        // Small Round (384x384)
        in 0..401 -> if (isScreenRound) 24.dp else 0.dp

        // Rectangular (402x476)
        // Large Round (454x454)
        else -> if (isScreenRound) 28.dp else 0.dp
    }

    // Padding and margin between text in the display and for buttons
    val displayTextPadding = 8.dp
    val spacer = 5.dp

    // Display background, text colors and font size
    val displayBackgroundColor = MaterialTheme.colorScheme.background
    val displayTextColor = MaterialTheme.colorScheme.onBackground
    val displayTextFontSize = when (width) {
        in 0..401 -> 22.sp
        else -> 24.sp
    }

    // Number background, text colors and font size
    val numberBackgroundColor = MaterialTheme.colorScheme.primary
    val numberTextColor = MaterialTheme.colorScheme.onPrimary
    val numberTextFontSize = when (width) {
        in 0..401 -> 20.sp
        else -> 24.sp
    }

    // Function background, text colors and font size
    val functionBackgroundColor = MaterialTheme.colorScheme.secondary
    val functionTextColor = MaterialTheme.colorScheme.onSecondary
    val functionTextFontSize = when (width) {
        in 0..401 -> 16.sp
        else -> 20.sp
    }

    // Size of the rounded corner for buttons
    val roundedCornerSize = 8.dp

    // Main
    Column(
        modifier = if (isScreenRound) {
            Modifier.padding(start = padding, end = padding, bottom = padding, top = padding)
        } else {
            Modifier
        }
            .background(backgroundColor)
    ) {
        val rowHeight = 0.18222222f // Weight for the rows
        val columnWidth = 0.25f // Weight for the buttons

        // Display
        Row(
            modifier = Modifier
                .weight(rowHeight * 1.5f)
                .padding(end = spacer)
        ) {
            Spacer(modifier = Modifier.height(spacer).width(spacer))

            viewModel.c.outputValue?.let {
                val output = it.ifEmpty { "0" }

                // Display border
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacer * 1.2f)
                        .clip(RoundedCornerShape(roundedCornerSize))
                        .background(displayBackgroundColor),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    // Display text
                    Text(
                        modifier = Modifier
                            .padding(end = displayTextPadding),
                        // Adjust the output according to max input length and add three dots if needed
                        text = if (output.length > calculator.maxInputLength)
                            "${output.substring(0, calculator.maxInputLength - 2)}\u2026${it.substring(it.length - 3)}"
                        else
                            output,
                        color = displayTextColor,
                        textAlign = TextAlign.End,
                        fontSize = displayTextFontSize
                    )

                    Spacer(modifier = Modifier.width(spacer))
                }
            }
        }
        Spacer(modifier = Modifier.height(spacer * 1.2f))

        /* ======== BUTTONS ======== */
        // 7, 8, 9, *
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "7",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("7") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "8",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("8") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "9",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("9") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MULTIPLY),
                fontSize = functionTextFontSize,
                backgroundColor = functionBackgroundColor,
                textColor = functionTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MULTIPLY) }
            )
            Spacer(modifier = Modifier.width(spacer))
        }
        Spacer(modifier = Modifier.height(spacer))

        // 4, 5, 6, /
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "4",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("4") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "5",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("5") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "6",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("6") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.DIVIDE),
                fontSize = functionTextFontSize,
                backgroundColor = functionBackgroundColor,
                textColor = functionTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.DIVIDE) }
            )
            Spacer(modifier = Modifier.width(spacer))
        }
        Spacer(modifier = Modifier.height(spacer))

        // 1, 2, 3, +
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "1",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("1") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "2",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("2") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "3",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("3") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.PLUS),
                fontSize = functionTextFontSize,
                backgroundColor = functionBackgroundColor,
                textColor = functionTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.PLUS) }
            )
            Spacer(modifier = Modifier.width(spacer))
        }
        Spacer(modifier = Modifier.height(spacer))

        // 0, ., =, -
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "0",
                fontSize = numberTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onNumberInput("0") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.decimalSeparator.toString(),
                fontSize = functionTextFontSize,
                backgroundColor = numberBackgroundColor,
                textColor = numberTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onDecimalInput(decimal = calculator.decimalSeparator) }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "=",
                fontSize = functionTextFontSize,
                backgroundColor = MaterialTheme.colorScheme.tertiary,
                textColor = MaterialTheme.colorScheme.onTertiary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onEqual() }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MINUS),
                fontSize = functionTextFontSize,
                backgroundColor = functionBackgroundColor,
                textColor = functionTextColor,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = roundedCornerSize,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MINUS) }
            )
            Spacer(modifier = Modifier.width(spacer))
        }
        Spacer(modifier = Modifier.height(spacer))
    }
}