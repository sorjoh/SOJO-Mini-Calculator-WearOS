package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import se.sojo.minicalculator.presentation.Calculator
import java.math.BigDecimal

class CalculatorViewModel : ViewModel() {
    var display = mutableStateOf("")

    var calculator: Calculator = Calculator()

    fun onNumberInput(inputKey: String) {
        if (calculator.debug)
            Log.d("SOJO Debug:", "Number(before) : button=" + inputKey + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(calculator.currentOperator) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator)

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
                    ?.split(calculator.getOperationSign(calculator.currentOperator))

                // If inputValue is zero then replace inputValue with the number, otherwise add the number
                calculator.inputValue = if (calculator.inputValue == "0") inputKey else calculator.inputValue + inputKey

                // Set current result initially as the first split value from the outputValue
                currentResultValue = (splitOutputValue?.get(0) ?: calculator.outputValue).toString()

                // Add minus sign in the beginning if current result value is empty and operator is minus
                if (calculator.currentOperator === Calculator.Operator.MINUS && currentResultValue.isEmpty()) {
                    currentResultValue = calculator.getOperationSign(Calculator.Operator.MINUS) + splitOutputValue?.get(1)
                }
            } catch (e: Exception) {
                if (calculator.debug)
                    Log.d("SOJO Debug:", e.message + ", outputValue=" + calculator.outputValue)

                // Error fix when splitting outputValue
                calculator.inputValue += inputKey
                currentResultValue = calculator.outputValue?.trim().toString()
            }

            // Replace output value with the temporary current result value together with current operator sign and input value
            calculator.outputValue = currentResultValue + calculator.getOperationSign(calculator.currentOperator) + calculator.inputValue
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
            Log.d("SOJO Debug:", "Number(after) : button=" + inputKey + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(calculator.currentOperator) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator)
    }

    fun onDecimalInput(decimal: Char) {
        if (calculator.debug)
            Log.d("SOJO Debug:",
                "Decimal(before) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
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
                    ?.split(calculator.getOperationSign(calculator.currentOperator))

                if (calculator.hasOperator == true) { // Calculation has operator
                    // Set output value to the first split output value plus the current operator sign
                    calculator.outputValue = splitOutputValue?.get(0) + calculator.getOperationSign(calculator.currentOperator)
                    // Set input value to the second split output value
                    calculator.inputValue = splitOutputValue?.get(1)
                } else { // Otherwise if calculation has no operator
                    // If equal has been pressed before this button then replace inputValue with an empty string, otherwise add the first split value
                    calculator.inputValue = if (calculator.isEqual == true) "" else calculator.outputValue

                    // Set output value to an empty string
                    calculator.outputValue = ""
                }
            } catch (e: Exception) {
                if (calculator.debug)
                    Log.d("SOJO Debug:", e.message + ", outputValue=" + calculator.outputValue)

                // Error fix when splitting outputValue
                if (calculator.hasOperator == false) {
                    calculator.outputValue = ""
                }
            }

            // Check if input value already contains a decimal otherwise add the decimal separator
            if (calculator.inputValue?.contains(decimal) == false) {
                calculator.inputValue += decimal
            }
        }

        // Add a zero before the decimal separator if the input value begins with a decimal
        if (calculator.inputValue?.startsWith(decimal) == true) {
            calculator.inputValue = "0" + calculator.inputValue
        }

        // Add the decimal and the result of input value to the output value
        calculator.outputValue += calculator.inputValue

        // Equal button has not been pressed
        calculator.isEqual = false

        // Display the output
        displayOutput(calculator.outputValue)

        if (calculator.debug)
            Log.d("SOJO Debug:",
                "Decimal(after) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator
        )
    }

    fun onOperatorInput(operation: Calculator.Operator) {
        if (calculator.debug)
            Log.d("SOJO Debug:",
                "Operator(before) : button=" + calculator.getOperationSign(operation) + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator
        )

        // Save current operator as previous operator
        val previousOperator: Calculator.Operator? = calculator.currentOperator

        // Variable to flag when no calculation shall be done when error happens, for example 12 * /
        var doNotCalculate = false

        if (calculator.hasOperator == true) { // Calculation has operator
            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = calculator.outputValue?.trim()
                    ?.split(calculator.getOperationSign(calculator.currentOperator))

                // Reset values
                calculator.resetValues()

                // Set output value to the first split output value
                calculator.outputValue = splitOutputValue?.get(0)
                // Set input value to the second split output value
                calculator.inputValue = splitOutputValue?.get(1)

                // Add minus sign in the beginning if current output value is empty and previous operator is minus
                if (previousOperator == Calculator.Operator.MINUS && calculator.outputValue?.isEmpty() == true) {
                    // Set output value to the second split output value
                    calculator.outputValue = calculator.getOperationSign(Calculator.Operator.MINUS) + splitOutputValue?.get(1)
                    // Set input value to the third split output value
                    calculator.inputValue = splitOutputValue?.get(2)
                }
            } catch (e: Exception) {
                if (calculator.debug)
                    Log.d("SOJO Debug:", e.message + ", outputValue=" + calculator.outputValue)

                // Set flag to not do any calculation when the error happens, for example 12 * /
                doNotCalculate = true

                // Set input value to zero
                calculator.inputValue = "0"

                // Replace the decimal from localities with a dot for the calculation
                calculator.resultValue = BigDecimal(calculator.outputValue?.replace(calculator.decimalSeparator, '.'))
            }

            // Convert and replace the decimal from localities with a dot for the calculation
            calculator.firstValue = BigDecimal(calculator.outputValue?.replace(calculator.decimalSeparator, '.'))

            // Fix for example 12 * %
            if (!doNotCalculate) {
                try {
                    // Convert and replace the decimal from localities with a dot for the calculation
                    calculator.secondValue = BigDecimal(calculator.inputValue?.replace(calculator.decimalSeparator, '.'))

                    // Calculate the result
                    calculator.resultValue = calculator.calculate(calculator.firstValue, calculator.secondValue, previousOperator)
                } catch (e: Exception) {
                    if (calculator.debug) {
                       Log.d("SOJO Debug:", e.message.toString())
                    }

                    // Convert and replace the decimal from localities with a dot for the calculation
                    calculator.resultValue = BigDecimal(calculator.outputValue?.replace(calculator.decimalSeparator, '.'))
                }
            }

            // Set current operator
            calculator.currentOperator = operation

            // Set input value to an empty string
            calculator.inputValue = ""

            // Replace output value with the result and local decimal separator, plus the current operator sign
            calculator.outputValue = calculator.resultValue.toString()
                .replace('.', calculator.decimalSeparator) + calculator.getOperationSign(calculator.currentOperator)
        } else if (calculator.outputValue?.isEmpty() == true) { // Output value is empty
            // Reset values
            calculator.resetValues()

            // Set current operator
            calculator.currentOperator = operation

            // Set input value to zero
            calculator.inputValue = "0"
            // Replace output value with the input value plus the current operator sign
            calculator.outputValue = calculator.inputValue + calculator.getOperationSign(calculator.currentOperator)
        } else { // Calculation has no operator and output value is not empty
            // Set current operator
            calculator.currentOperator = operation

            // Set input value to an empty string
            calculator.inputValue = ""

            // Add the current operator sign to output value
            calculator.outputValue += calculator.getOperationSign(calculator.currentOperator)
        }

        // Tell the app that an operator is present
        calculator.hasOperator = true

        // Equal button has not been pressed
        calculator.isEqual = false

        // Display the output
        displayOutput(calculator.outputValue)

        if (calculator.debug)
            Log.d("SOJO Debug:",
                "Operator(after) : button=" + calculator.getOperationSign(operation) + ", resultValue=" + calculator.resultValue + ", firstValue=" + calculator.firstValue + ", secondValue=" + calculator.secondValue + ", inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult + ", hasOperator=" + calculator.hasOperator
        )
    }

    fun onEqual() {
        if (calculator.debug)
            Log.d("SOJO Debug:",
            "Equal(before) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult
        )

        // Get first and second value from the output
        val splitOutputValue: List<String>? = calculator.outputValue?.trim()
            ?.split(calculator.getOperationSign(calculator.currentOperator))

        // Set output value to the first split output value
        calculator.outputValue = splitOutputValue?.get(0)

        try {
            // Add minus sign in the beginning if current output value is empty and current operator is minus
            if (calculator.currentOperator === Calculator.Operator.MINUS && calculator.outputValue?.isEmpty() == true) {
                // Set output value to the second split output value
                calculator.outputValue = calculator.getOperationSign(Calculator.Operator.MINUS) + splitOutputValue?.get(1)
                // Set input value to the third split output value
                calculator.inputValue = splitOutputValue?.get(2)
            } else { // Otherwise set input value to the second split output value
                calculator.inputValue = splitOutputValue?.get(1)
            }
        } catch (e: Exception) {
            if (calculator.debug) {
                Log.d("SOJO Debug:",e.message.toString())
            }

            // Reset all values if Equal been pressed twice, otherwise reset all values except for the output value
            val tmpOutputValue: String? = if (calculator.isEqual == true) "" else calculator.outputValue // Save outputValue if needed

            // Reset values
            calculator.resetValues()

            // Restore the output value if needed
            calculator.outputValue = tmpOutputValue
        }

        if (calculator.hasOperator == true) { // Calculation has operator
            // Set firstValue as BigDecimal value of outputValue plus convert and replace the decimal from localities with a dot for the calculation
            calculator.firstValue = BigDecimal(calculator.outputValue?.replace(calculator.decimalSeparator, '.'))

            // Set secondValue if it is not empty as BigDecimal value of inputValue plus convert and replace the decimal from localities with a dot for the calculation
            calculator.resultValue = if (calculator.inputValue?.isEmpty() == false) {
                calculator.secondValue = BigDecimal(calculator.inputValue?.replace(calculator.decimalSeparator, '.'))

                // Calculate the result
                calculator.calculate(calculator.firstValue, calculator.secondValue, calculator.currentOperator)
            } else {
                calculator.firstValue
            }
        } else {
            // Set resultValue as BigDecimal value of outputValue plus convert and replace the decimal from localities with a dot for the calculation
            calculator.resultValue = if (calculator.outputValue?.isEmpty() == false)
                BigDecimal(calculator.outputValue?.replace(calculator.decimalSeparator, '.'))
            else
                BigDecimal("0")
        }

        // Set input value to an empty string
        calculator.inputValue = ""

        // Convert result value to string plus convert and replace the decimal from localities with a dot for the calculation
        calculator.outputValue = calculator.resultValue.toString().replace('.', calculator.decimalSeparator)

        // Set current operator to none
        calculator.currentOperator = Calculator.Operator.NONE

        // No operator is used
        calculator.hasOperator = false

        // Set a flag that no result has been calculated, even if it has been done
        calculator.hasResult = false

        // Tell the app that equal has been pressed
        calculator.isEqual = true

        // Display the output
        displayOutput(calculator.outputValue)

        if (calculator.debug)
            Log.d("SOJO Debug:",
            "Equal(after) : inputValue=" + calculator.inputValue + ", outputValue=" + calculator.outputValue + ", Display=" + display.value + ", currentOperation=" + calculator.getOperationSign(
                calculator.currentOperator
            ) + ", hasResult=" + calculator.hasResult
        )
    }

    // ========= DISPLAY OUTPUT =========
    private fun displayOutput(output: String? = "0") {
        // Display the output
        display.value = output.toString()
    }
}