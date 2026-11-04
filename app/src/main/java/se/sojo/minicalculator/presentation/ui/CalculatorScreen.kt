package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.graphics.toArgb

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.TextButton
import androidx.wear.compose.material3.TextButtonDefaults
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
    viewModel.calculator = calculator

    // Needed for the display output
    LaunchedEffect(key1 = viewModel.display.value) {
       viewModel.display.value.let { Log.d("SOJO Debug:", it) }
    }

    if (calculator.debug)
        Log.d("SOJO Debug:", "isScreenRound=$isScreenRound, width=$width, height=$height")

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

    val displayTextPadding = 8.dp
    val spacer = 5.dp

    // Background and text colors
    val CALCULATOR_BACKGROUND_COLOR = Color(Color(0xFF585A67).toArgb())

    val DISPLAY_BACKGROUND_COLOR = Color(Color(0xFFC1D797).toArgb())
    val DISPLAY_TEXT_COLOR = Color(Color(0xFFFDFFFE).toArgb())
    val DISPLAY_TEXT_FONT_SIZE = when (width) {
        in 0..401 -> 22.sp
        else -> 24.sp
    }

    val NUMBER_BACKGROUND_COLOR = Color(Color(0xFF3A3D46).toArgb())
    val NUMBER_TEXT_COLOR = Color(Color(0xFFFDFFFE).toArgb())
    val NUMBER_TEXT_FONT_SIZE = when (width) {
        in 0..401 -> 20.sp
        else -> 24.sp
    }

    val FUNCTION_BACKGROUND_COLOR = Color(Color(0xFFBABEC1).toArgb())
    val FUNCTION_TEXT_COLOR = Color(Color(0xFFFDFFFE).toArgb())
    val FUNCTION_TEXT_FONT_SIZE = when (width) {
        in 0..401 -> 16.sp
        else -> 20.sp
    }

    val ROUNDED_CORNER_SHAPE_SIZE = 8.dp

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

            viewModel.calculator.outputValue?.let {
                val output = it.ifEmpty { "0" }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = spacer * 1.2f)
                        .clip(RoundedCornerShape(ROUNDED_CORNER_SHAPE_SIZE))
                        .background(DISPLAY_BACKGROUND_COLOR),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = displayTextPadding),
                        // Adjust the output according to max input length
                        text = if (output.length > calculator.maxInputLength)
                            "${output.substring(0, calculator.maxInputLength)}...${it.substring(it.length - 1)}"
                        else
                            output,
                        color = DISPLAY_TEXT_COLOR,
                        textAlign = TextAlign.End,
                        fontSize = DISPLAY_TEXT_FONT_SIZE
                    )

                    Spacer(modifier = Modifier.width(spacer))
                }

                /*TextButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = spacer * 1.2f)
                        .align(alignment = Alignment.CenterVertically),
                    shapes = TextButtonDefaults.shapes(shape = RoundedCornerShape(ROUNDED_CORNER_SHAPE_SIZE)),
                    colors = TextButtonDefaults.textButtonColors(
                        contentColor = DISPLAY_BACKGROUND_COLOR,
                        containerColor = DISPLAY_BACKGROUND_COLOR
                    ),
                    border = ButtonDefaults.outlinedButtonBorder(
                        enabled = true,
                        borderColor = DISPLAY_BACKGROUND_COLOR
                    ),
                    onClick = {  }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = displayTextPadding),
                    // Adjust the output according to max input length
                    text = if (output.length > calculator.maxInputLength)
                        "${output.substring(0, calculator.maxInputLength)}...${it.substring(it.length - 1)}"
                    else
                        output,
                    fontSize = DISPLAY_TEXT_FONT_SIZE,
                    color = DISPLAY_TEXT_COLOR,
                    textAlign = TextAlign.End
                    )

                    Spacer(modifier = Modifier.width(spacer))
                }*/
            }
        }
        Spacer(modifier = Modifier.height(spacer * 1.2f))

        // 7, 8, 9, *
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "7",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("7") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "8",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("8") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "9",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("9") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MULTIPLY),
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
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
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("4") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "5",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("5") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "6",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("6") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.DIVIDE),
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
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
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("1") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "2",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("2") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "3",
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("3") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.PLUS),
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
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
                fontSize = NUMBER_TEXT_FONT_SIZE,
                backgroundColor = NUMBER_BACKGROUND_COLOR,
                textColor = NUMBER_TEXT_COLOR,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onNumberInput("0") }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.decimalSeparator.toString(),
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onDecimalInput(decimal = calculator.decimalSeparator) }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = "=",
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onEqual() }
            )
            Spacer(modifier = Modifier.width(spacer))

            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MINUS),
                fontSize = FUNCTION_TEXT_FONT_SIZE,
                backgroundColor = FUNCTION_BACKGROUND_COLOR,
                textColor = FUNCTION_TEXT_COLOR,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(columnWidth),
                shapeSize = ROUNDED_CORNER_SHAPE_SIZE,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MINUS) }
            )
            Spacer(modifier = Modifier.width(spacer))
        }
        Spacer(modifier = Modifier.height(spacer))
    }
}