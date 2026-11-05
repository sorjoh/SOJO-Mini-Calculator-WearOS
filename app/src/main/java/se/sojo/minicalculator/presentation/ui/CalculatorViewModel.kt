package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import se.sojo.minicalculator.presentation.Calculator
import java.math.BigDecimal

class CalculatorViewModel : ViewModel() {
    var display = mutableStateOf("")

    var c: Calculator = Calculator()

    // ========= NUMBER INPUT =========
    fun onNumberInput(inputKey: String) {
        // Show values before they are modified for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onNumberInput(before) : button=$inputKey, inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )

        if (c.hasResult == true) { // Calculation has result
            // Reset all values
            c.resetValues()
            // Add the first number to inputValue
            c.inputValue = inputKey
            // Replace outputValue with the newly and initialized inputValue
            c.outputValue = c.inputValue
        } else if (c.hasOperator == true) { // Calculation has operator
            var currentResultValue: String

            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = c.outputValue?.trim()
                    ?.split(c.getOperationSign(c.currentOperator))

                // If inputValue is zero then replace inputValue with the number, otherwise add the number
                c.inputValue = if (c.inputValue == "0") inputKey else "${c.inputValue}$inputKey"

                // Set current result initially as the first split value from the outputValue
                currentResultValue = (splitOutputValue?.get(0) ?: c.outputValue).toString()

                // Add minus sign in the beginning if current result value is empty and operator is minus
                if (c.currentOperator === Calculator.Operator.MINUS && currentResultValue.isEmpty()) {
                    currentResultValue = "${c.getOperationSign(Calculator.Operator.MINUS)}${splitOutputValue?.get(1)}"
                }
            } catch (e: Exception) {
                if (c.debug)
                    Log.d("SOJO Debug:","CalculatorViewModel -> onNumberInput(error) : ${e.message}, outputValue=${c.outputValue}")

                // Error fix when splitting outputValue
                c.inputValue += inputKey
                currentResultValue = c.outputValue?.trim().toString()
            }

            // Replace output value with the temporary current result value together with current operator sign and input value
            c.outputValue = "$currentResultValue${c.getOperationSign(c.currentOperator)}${c.inputValue}"
        } else {
            // Otherwise add the number to input value
            c.inputValue += inputKey
            // Replace output value with the new input value
            c.outputValue = c.inputValue
        }

        // Equal button has not been pressed
        c.isEqual = false //Bugfix for example 2.3 + 5 = .

        // Display the output
        displayOutput(c.outputValue)

        // Show modified values for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onNumberInput(after) : button=$inputKey, inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )
    }

    // ========= DECIMAL INPUT =========
    fun onDecimalInput(decimal: Char) {
        // Show values before they are modified for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onDecimalInput(before) : inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )

        if (c.hasResult == true) { // Calculation has result
            // Reset all values
            c.resetValues()
        } else { // Calculation has no result
            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = c.outputValue?.trim()
                    ?.split(c.getOperationSign(c.currentOperator))

                if (c.hasOperator == true) { // Calculation has operator
                    // Set output value to the first split output value plus the current operator sign
                    c.outputValue ="${splitOutputValue?.get(0)}${c.getOperationSign(c.currentOperator)}"
                    // Set input value to the second split output value
                    c.inputValue = splitOutputValue?.get(1)
                } else { // Otherwise if calculation has no operator
                    // If equal has been pressed before this button then replace inputValue with an empty string, otherwise add the first split value
                    c.inputValue = if (c.isEqual == true) "" else c.outputValue

                    // Set output value to an empty string
                    c.outputValue = ""
                }
            } catch (e: Exception) {
                if (c.debug)
                    Log.d("SOJO Debug:","CalculatorViewModel -> onDecimalInput(error) : ${e.message}, outputValue=${c.outputValue}")

                // Error fix when splitting outputValue
                if (c.hasOperator == false) {
                    c.outputValue = ""
                }
            }

            // Check if input value already contains a decimal otherwise add the decimal separator
            if (c.inputValue?.contains(decimal) == false) {
                c.inputValue += decimal
            }
        }

        // Add a zero before the decimal separator if the input value begins with a decimal
        if (c.inputValue?.startsWith(decimal) == true) {
            c.inputValue = "0${c.inputValue}"
        }

        // Add the decimal and the result of input value to the output value
        c.outputValue += c.inputValue

        // Equal button has not been pressed
        c.isEqual = false

        // Display the output
        displayOutput(c.outputValue)

        // Show modified values for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onDecimalInput(after) : inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )
    }

    // ========= OPERATOR INPUT =========
    fun onOperatorInput(operation: Calculator.Operator) {
        // Show values before they are modified for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onOperatorInput(before) : button=${
                    c.getOperationSign(operation)
                }, inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(
                        c.currentOperator
                    )
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )

        // Save current operator as previous operator
        val previousOperator: Calculator.Operator? = c.currentOperator

        // Variable to flag when no calculation shall be done when error happens, for example 12 * /
        var doNotCalculate = false

        if (c.hasOperator == true) { // Calculation has operator
            try {
                // Get first and second value from the output
                val splitOutputValue: List<String>? = c.outputValue?.trim()
                    ?.split(c.getOperationSign(c.currentOperator))

                // Reset values
                c.resetValues()

                // Set output value to the first split output value
                c.outputValue = splitOutputValue?.get(0)
                // Set input value to the second split output value
                c.inputValue = splitOutputValue?.get(1)

                // Add minus sign in the beginning if current output value is empty and previous operator is minus
                if (previousOperator == Calculator.Operator.MINUS && c.outputValue?.isEmpty() == true) {
                    // Set output value to the second split output value
                    c.outputValue =
                        "${c.getOperationSign(Calculator.Operator.MINUS)}${splitOutputValue?.get(1)}"
                    // Set input value to the third split output value
                    c.inputValue = splitOutputValue?.get(2)
                }
            } catch (e: Exception) {
                if (c.debug)
                    Log.d("SOJO Debug:",
                        "CalculatorViewModel -> onOperatorInput(error) : ${e.message}, outputValue=${c.outputValue}"
                    )

                // Set flag to not do any calculation when the error happens, for example 12 * /
                doNotCalculate = true

                // Set input value to zero
                c.inputValue = "0"

                // Replace the decimal from localities with a dot for the calculation
                c.resultValue = BigDecimal(c.outputValue?.replace(c.decimalSeparator, '.'))
            }

            // Convert and replace the decimal from localities with a dot for the calculation
            c.firstValue = BigDecimal(c.outputValue?.replace(c.decimalSeparator, '.'))

            // Fix for example 12 * %
            if (!doNotCalculate) {
                try {
                    // Convert and replace the decimal from localities with a dot for the calculation
                    c.secondValue = BigDecimal(c.inputValue?.replace(c.decimalSeparator, '.'))

                    // Calculate the result
                    c.resultValue = c.calculate(c.firstValue, c.secondValue, previousOperator)
                } catch (e: Exception) {
                    if (c.debug)
                       Log.d("SOJO Debug:","CalculatorViewModel -> onOperatorInput(error)${e.message}")

                    // Convert and replace the decimal from localities with a dot for the calculation
                    c.resultValue = BigDecimal(c.outputValue?.replace(c.decimalSeparator, '.'))
                }
            }

            // Set current operator
            c.currentOperator = operation

            // Set input value to an empty string
            c.inputValue = ""

            // Replace output value with the result and local decimal separator, plus the current operator sign
            c.outputValue = c.resultValue.toString()
                .replace('.', c.decimalSeparator) + c.getOperationSign(c.currentOperator)
        } else if (c.outputValue?.isEmpty() == true) { // Output value is empty
            // Reset values
            c.resetValues()

            // Set current operator
            c.currentOperator = operation

            // Set input value to zero
            c.inputValue = "0"
            // Replace output value with the input value plus the current operator sign
            c.outputValue = "${c.inputValue}${c.getOperationSign(c.currentOperator)}"
        } else { // Calculation has no operator and output value is not empty
            // Set current operator
            c.currentOperator = operation

            // Set input value to an empty string
            c.inputValue = ""

            // Add the current operator sign to output value
            c.outputValue += c.getOperationSign(c.currentOperator)
        }

        // Tell the app that an operator is present
        c.hasOperator = true

        // Equal button has not been pressed
        c.isEqual = false

        // Display the output
        displayOutput(c.outputValue)

        // Show modified values for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onOperatorInput(after) -> button=${
                    c.getOperationSign(operation)
                }, resultValue=${c.resultValue}, firstValue=${c.firstValue}, secondValue=${c.secondValue}, inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(
                        c.currentOperator
                    )
                }, hasResult=${c.hasResult}, hasOperator=${c.hasOperator}"
            )
    }

    // ========= EQUAL =========
    fun onEqual() {
        // Show values before they are modified for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onEqual(before) : inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}"
            )

        // Get first and second value from the output
        val splitOutputValue: List<String>? = c.outputValue?.trim()
            ?.split(c.getOperationSign(c.currentOperator))

        // Set output value to the first split output value
        c.outputValue = splitOutputValue?.get(0)

        try {
            // Add minus sign in the beginning if current output value is empty and current operator is minus
            if (c.currentOperator === Calculator.Operator.MINUS && c.outputValue?.isEmpty() == true) {
                // Set output value to the second split output value
                c.outputValue =
                    "${c.getOperationSign(Calculator.Operator.MINUS)}${splitOutputValue?.get(1)}"
                // Set input value to the third split output value
                c.inputValue = splitOutputValue?.get(2)
            } else { // Otherwise set input value to the second split output value
                c.inputValue = splitOutputValue?.get(1)
            }
        } catch (e: Exception) {
            if (c.debug) {
                Log.d("SOJO Debug:", "CalculatorViewModel -> onEqual(error) : ${e.message}")
            }

            // Reset all values if Equal been pressed twice, otherwise reset all values except for the output value
            val tmpOutputValue: String? = if (c.isEqual == true) "" else c.outputValue // Save outputValue if needed

            // Reset values
            c.resetValues()

            // Restore the output value if needed
            c.outputValue = tmpOutputValue
        }

        if (c.hasOperator == true) { // Calculation has operator
            // Set firstValue as BigDecimal value of outputValue plus convert and replace the decimal from localities with a dot for the calculation
            c.firstValue = BigDecimal(c.outputValue?.replace(c.decimalSeparator, '.'))

            // Set secondValue if it is not empty as BigDecimal value of inputValue plus convert and replace the decimal from localities with a dot for the calculation
            c.resultValue = if (c.inputValue?.isEmpty() == false) {
                c.secondValue = BigDecimal(c.inputValue?.replace(c.decimalSeparator, '.'))

                // Calculate the result
                c.calculate(c.firstValue, c.secondValue, c.currentOperator)
            } else {
                c.firstValue
            }
        } else {
            // Set resultValue as BigDecimal value of outputValue plus convert and replace the decimal from localities with a dot for the calculation
            c.resultValue = if (c.outputValue?.isEmpty() == false)
                BigDecimal(c.outputValue?.replace(c.decimalSeparator, '.'))
            else
                BigDecimal("0")
        }

        // Set input value to an empty string
        c.inputValue = ""

        // Convert result value to string plus convert and replace the decimal from localities with a dot for the calculation
        c.outputValue = c.resultValue.toString().replace('.', c.decimalSeparator)

        // Set current operator to none
        c.currentOperator = Calculator.Operator.NONE

        // No operator is used
        c.hasOperator = false

        // Set a flag that no result has been calculated, even if it has been done
        c.hasResult = false

        // Tell the app that equal has been pressed
        c.isEqual = true

        // Display the output
        displayOutput(c.outputValue)

        // Show modified values for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:",
                "CalculatorViewModel -> onEqual(after) : inputValue=${c.inputValue}, outputValue=${c.outputValue}, Display=${display.value}, currentOperation=${
                    c.getOperationSign(c.currentOperator)
                }, hasResult=${c.hasResult}"
            )
    }

    // ========= DISPLAY OUTPUT =========
    private fun displayOutput(output: String? = "0") {
        // Show values before they are modified for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:", "CalculatorViewModel -> DisplayOutput(before) : output=$output, display=${display.value}")

        // Display the output
        display.value = output.toString()

        // Show modified values for debugging in Logcat
        if (c.debug)
            Log.d("SOJO Debug:", "CalculatorViewModel -> DisplayOutput(after) : output=$output, display=${display.value}")
    }
}