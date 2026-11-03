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
    defaultRowModifier: Modifier,
    defaultButtonModifier: Modifier,
    viewModel: CalculatorViewModel = CalculatorViewModel() )
{
    viewModel.calculator = calculator

    LaunchedEffect(key1 = viewModel.display.value) {
        viewModel.calculator.outputValue?.let { Log.d("SOJO Debug:", it) }
    }

    Column(
        modifier = Modifier
    ) {
        // Display
        Row(
            modifier = defaultRowModifier.padding(end = 4.dp)
        ) {
            viewModel.calculator.outputValue?.let {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterVertically),
                    text = it.ifEmpty { "0" },
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
        }

        // 7, 8, 9, *
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton(
                text = "7",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("7") }
            )
            CalculatorButton(
                text = "8",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("8") }
            )
            CalculatorButton(
                text = "9",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("9") }
            )
            CalculatorButton(
                text = calculator.getOperationBySign(operator = Calculator.Operator.MULTIPLY),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MULTIPLY) }
            )
        }

        // 4, 5, 6, /
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton(
                text = "4",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("4") }
            )
            CalculatorButton(
                text = "5",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("5") }
            )
            CalculatorButton(
                text = "6",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("6") }
            )
            CalculatorButton(
                text = calculator.getOperationBySign(operator = Calculator.Operator.DIVIDE),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.DIVIDE) }
            )
        }

        // 1, 2, 3, +
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton(
                text = "1",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("1") }
            )
            CalculatorButton(
                text = "2",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("2") }
            )
            CalculatorButton(
                text = "3",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("3") }
            )
            CalculatorButton(
                text = calculator.getOperationBySign(operator = Calculator.Operator.PLUS),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.PLUS) }
            )
        }

        // 0, ., =, -
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton(
                text = "0",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("0") }
            )
            CalculatorButton(
                text = calculator.decimalSeparator.toString(),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onDecimalInput(decimal = calculator.decimalSeparator) }
            )
            CalculatorButton(
                text = "=",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onEqual() })
            CalculatorButton(
                text = calculator.getOperationBySign(operator = Calculator.Operator.MINUS),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onOperatorInput(operation = Calculator.Operator.MINUS) })
        }
    }
}