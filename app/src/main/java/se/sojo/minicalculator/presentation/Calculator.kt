package se.sojo.minicalculator.presentation

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode


class Calculator(var decimalSeparator: Char = '.', var maxInputLength: Int = 0, var debug: Boolean = false) {
    // Status indicator if equal button or another calculation has presented a total result
    var hasResult: Boolean? = null

    // Variables holding current operations
    var currentOperator: Operator? = Operator.NONE
    var hasOperator: Boolean? = null

    // Variables holding the result and input values
    var resultValue: BigDecimal? = null
    var firstValue: BigDecimal? = null
    var secondValue: BigDecimal? = null

    // Variables holding the input and output text
    var inputValue: String? = null
    var outputValue: String? = null

    // Variable for checking if equal has been pressed or not
    var isEqual: Boolean? = null
    enum class Operator {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        NONE
    }

    fun getOperationSign(operator: Operator?): String {
        return when (operator) {
            Operator.PLUS -> "+"
            Operator.MINUS -> "-"
            Operator.MULTIPLY -> "×"
            Operator.DIVIDE -> "÷"
            else -> ""
        }
    }

    // ========= GET OPERATOR =========
    fun getOperation(key: String): Operator {
        return when (key) {
            "+" -> Operator.PLUS
            "−", "-" -> Operator.MINUS
            "×", "*", "x", "X" -> Operator.MULTIPLY
            "÷", "/" -> Operator.DIVIDE
            else -> Operator.NONE
        }
    }

    // ========= CALCULATE RESULT =========
    fun calculate(
        firstValue: BigDecimal?,
        secondValue: BigDecimal?,
        operation: Operator?
    ): BigDecimal? {
        val mc: MathContext = MathContext(10, RoundingMode.DOWN)

        return when (operation) {
            Operator.PLUS -> firstValue?.add(secondValue)
            Operator.MINUS -> firstValue?.subtract(secondValue)
            Operator.MULTIPLY -> firstValue?.multiply(secondValue)
            Operator.DIVIDE -> firstValue?.divide(secondValue, mc)
            else -> firstValue
        }
    }
    // ========= Reset Values =========
    fun resetValues() {
        // Status indicator if equal button or another calculation has presented a total result
        hasResult = false

        // Variables holding current operations
        currentOperator = Operator.NONE
        hasOperator = false

        // Variables holding the result and input values
        resultValue = BigDecimal(0)
        firstValue = BigDecimal(0)
        secondValue = BigDecimal(0)

        // Variables holding the input and output text
        inputValue = ""
        outputValue = ""

        // Variable for checking if equal has been pressed or not
        isEqual = false
    }
}