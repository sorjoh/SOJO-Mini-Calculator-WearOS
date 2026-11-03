package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import se.sojo.minicalculator.presentation.Calculator

@Composable
fun CalculatorScreen(
    calculator: Calculator,
    viewModel: CalculatorViewModel = CalculatorViewModel() )
{
    // Include the calculator class in the viewmodel
    viewModel.calculator = calculator

    // Needed for the display output
    LaunchedEffect(key1 = viewModel.display.value) {
       viewModel.display.value.let { Log.d("SOJO Debug:", it) }
    }

    // TODO: Fix the layout for rounded clocks
    Column(
        modifier = Modifier
    ) {
        val rowHeight = 0.20f // Weight for the rows
        val columnWidth = 0.25f // Weight for the buttons

        // Display
        Row(
            modifier = Modifier.weight(rowHeight).padding(end = 4.dp)
        ) {
            viewModel.calculator.outputValue?.let {
                val output = it.ifEmpty { "0" }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterVertically),
                    // Adjust the output according to max input length
                    text = if (output.length > calculator.maxInputLength)
                        "${output.substring(0, calculator.maxInputLength)}...${it.substring(it.length - 1)}"
                    else
                        output,
                    fontSize = 20.sp, // TODO: Adjust fontsize for different screens
                    textAlign = TextAlign.End
                )
            }
        }

        // 7, 8, 9, *
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            CalculatorButton(
                text = "7",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("7") }
            )
            CalculatorButton(
                text = "8",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("8") }
            )
            CalculatorButton(
                text = "9",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("9") }
            )
            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MULTIPLY),
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MULTIPLY) }
            )
        }

        // 4, 5, 6, /
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            CalculatorButton(
                text = "4",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("4") }
            )
            CalculatorButton(
                text = "5",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("5") }
            )
            CalculatorButton(
                text = "6",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("6") }
            )
            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.DIVIDE),
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.DIVIDE) }
            )
        }

        // 1, 2, 3, +
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            CalculatorButton(
                text = "1",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("1") }
            )
            CalculatorButton(
                text = "2",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("2") }
            )
            CalculatorButton(
                text = "3",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("3") }
            )
            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.PLUS),
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.PLUS) }
            )
        }

        // 0, ., =, -
        Row(
            modifier = Modifier.weight(rowHeight)
        ) {
            CalculatorButton(
                text = "0",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onNumberInput("0") }
            )
            CalculatorButton(
                text = calculator.decimalSeparator.toString(),
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onDecimalInput(decimal = calculator.decimalSeparator) }
            )
            CalculatorButton(
                text = "=",
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onEqual() })
            CalculatorButton(
                text = calculator.getOperationSign(operator = Calculator.Operator.MINUS),
                modifier = Modifier.weight(columnWidth),
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MINUS) })
        }
    }
}