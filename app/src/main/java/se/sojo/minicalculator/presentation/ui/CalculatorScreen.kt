package se.sojo.minicalculator.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
    Column(
        modifier = Modifier
    ) {
        // Display
        Row(
            modifier = defaultRowModifier.padding(end = 4.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterVertically),
                text = viewModel.outputValue.value,
                fontSize = 20.sp,
                textAlign = TextAlign.End
            )
        }

        // 7, 8, 9, *
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton("7", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("7") })
            CalculatorButton("8", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("8") })
            CalculatorButton("9", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("9") })
            CalculatorButton(calculator.getOperationBySign(Calculator.Operator.MULTIPLY), modifier = defaultButtonModifier, onClick = { viewModel.onOperatorInput(Calculator.Operator.MULTIPLY) })
        }

        // 4, 5, 6, /
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton("4", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("4") })
            CalculatorButton("5", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("5") })
            CalculatorButton("6", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("6") })
            CalculatorButton(calculator.getOperationBySign(Calculator.Operator.DIVIDE), modifier = defaultButtonModifier, onClick = { viewModel.onOperatorInput(Calculator.Operator.DIVIDE) })
        }

        // 1, 2, 3, +
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton("1", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("1") })
            CalculatorButton("2", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("2") })
            CalculatorButton("3", modifier = defaultButtonModifier, onClick = { viewModel.onNumberInput("3") })
            CalculatorButton(calculator.getOperationBySign(Calculator.Operator.PLUS), modifier = defaultButtonModifier, onClick = { viewModel.onOperatorInput(Calculator.Operator.PLUS) })
        }

        // 0, ., =, -
        Row(
            modifier = defaultRowModifier
        ) {
            CalculatorButton(
                "0",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onNumberInput("0") })
            CalculatorButton(
                calculator.decimalSeparator.toString(),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onDecimalInput(calculator.decimalSeparator) })
            CalculatorButton(
                "=",
                modifier = defaultButtonModifier,
                onClick = { viewModel.onEqual() })
            CalculatorButton(
                calculator.getOperationBySign(Calculator.Operator.MINUS),
                modifier = defaultButtonModifier,
                onClick = { viewModel.onOperatorInput(Calculator.Operator.MINUS) })
        }
    }
}