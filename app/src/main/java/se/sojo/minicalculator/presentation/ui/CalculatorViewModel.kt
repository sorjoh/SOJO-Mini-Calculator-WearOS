package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import se.sojo.minicalculator.presentation.Calculator
import java.util.Objects


class CalculatorViewModel() : ViewModel() {

    var display = mutableStateOf<String>("")

    var calculator: Calculator = Calculator()

    fun onNumberInput(inputKey: String) {
        if (calculator.debug)
            Log.d("SOJO Debug:", "Number(before) : button=" + inputKey + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationBySign(calculator.currentOperator) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator)

        if (calculator.hasResult == true) { // Calculation has result
            // Reset all values
            calculator.resetValues()
            // Add the first number to inputValue
            calculator.inputValue = inputKey
            // Replace outputValue with the newly and initialized inputValue
            calculator.outputValue = calculator.inputValue
        } else if (calculator.hasOperator == true) { // Calculation has operator
            var currentResultValue: String

            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = calculator.outputValue?.trim()
                    ?.split("[" + calculator.getOperationBySign(calculator.currentOperator) + "]")

                // If inputValue is zero then replace inputValue with the number, otherwise add the number
                calculator.inputValue = if (Objects.equals(
                        calculator.inputValue,
                        "0"
                    )
                ) inputKey else calculator.inputValue + inputKey

                // Set current result initially as the first split value from the outputValue
                currentResultValue = (splitOutputValue?.get(0) ?: calculator.outputValue).toString()

                // Add minus sign in the beginning if current result value is empty and operator is minus
                if (calculator.currentOperator === Calculator.Operator.MINUS && currentResultValue.isEmpty()) {
                    currentResultValue =
                        calculator.getOperationBySign(Calculator.Operator.MINUS) + splitOutputValue?.get(1)
                }
            } catch (e: Exception) {
                if (calculator.debug) println(e.message + ", outputValue=" + calculator.outputValue)

                // Error fix when splitting outputValue
                calculator.inputValue += inputKey
                currentResultValue = calculator.outputValue?.trim().toString()
            }

            // Replace output value with the temporary current result value together with current operator sign and input value
            calculator.outputValue =
                currentResultValue + calculator.getOperationBySign(calculator.currentOperator) + calculator.inputValue
        } else {
            // Otherwise add the number to input value
            calculator.inputValue += inputKey
            // Replace output value with the new input value
            calculator.outputValue = calculator.inputValue
        }


        // Equal button has not been pressed
        calculator.isEqual = false //Bugfix for example 2.3 + 5 = .


        // Display the output
        displayOutput(calculator.outputValue)

        if (calculator.debug)
            Log.d("SOJO Debug:", "Number(after) : button=" + inputKey + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationBySign(calculator.currentOperator) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator)

    }

    fun onDecimalInput(decimal: Char) {
        if (calculator.debug) println(
            "Decimal(before) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationBySign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator
        )

        if (calculator.hasResult == true) { // Calculation has result
            // Reset all values
            calculator.resetValues()
        } else { // Calculation has no result
            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = calculator.outputValue?.trim()
                    ?.split("[" + calculator.getOperationBySign(calculator.currentOperator) + "]")

                if (calculator.hasOperator == true) { // Calculation has operator
                    // Set output value to the first split output value plus the current operator sign
                    calculator.outputValue =
                        splitOutputValue?.get(0) + calculator.getOperationBySign(calculator.currentOperator)
                    // Set input value to the second split output value
                    calculator.inputValue = splitOutputValue?.get(1)
                } else { // Otherwise if calculation has no operator
                    // Set output value to an empty string
                    calculator.outputValue = ""

                    // If equal has been pressed before this button then replace inputValue with an empty string, otherwise add the first split value
                    calculator.inputValue = if (calculator.isEqual == true) "" else splitOutputValue?.get(0)
                }
            } catch (e: java.lang.Exception) {
                if (calculator.debug) println(e.message + ", outputValue=" + calculator.outputValue)

                // Error fix when splitting outputValue
                calculator.hasOperator?.let {
                    if (!it) {
                        calculator.outputValue = ""
                    }
                }
            }

            // Check if input value already contains a decimal otherwise add the decimal separator
            calculator.inputValue?.contains(decimal)?.let {
                if (!it) {
                    calculator.inputValue = calculator.inputValue + decimal
                }
            }
        }


        // Add a zero before the decimal separator if the input value begins with a decimal
        if (calculator.inputValue!!.startsWith(decimal)) {
            calculator.inputValue = "0" + calculator.inputValue
        }


        // Add the decimal and the result of input value to the output value
        calculator.outputValue = calculator.outputValue + calculator.inputValue


        // Equal button has not been pressed
        calculator.isEqual = false


        // Display the output
        displayOutput(calculator.outputValue)

        if (calculator.debug) println(
            "Decimal(after) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationBySign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator
        )

    }

    fun onOperatorInput(operation: Calculator.Operator) {

    }

    fun onEqual() {

    }

    // ========= DISPLAY OUTPUT =========
    private fun displayOutput(output: String? = "0") {
        //val space = StringBuilder()

        // Add a zero if output is empty
        //val tmp = if (output.isEmpty()) "0" else output

        // Add space so the output is aligned at right
        //space.append(" ".repeat(Math.max(0, MAX_INPUT_LENGTH - tmp.length)))

        // Display output with needed spaces
        //display.setText(space.toString() + tmp)

        display.value = output.toString()

        /*if (output != null) {
            Log.d("SOJO Debug:", "test " + output)
        }*/

        //calculator.outputValue = output

    }
}