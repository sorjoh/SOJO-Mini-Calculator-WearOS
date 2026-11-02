package se.sojo.minicalculator.presentation

class Calculator(var decimalSeparator: Char, var maxInputLength: Int, debug2: Boolean) {
    var debug = debug2

    companion object {
        val debug: Boolean = debug2
        var decimalSeparator: Char = '.'
        var maxInputLength = 0
       // var debug = false
    }

    init {
        decimalSeparator = this.decimalSeparator
        maxInputLength = this.maxInputLength
        //debug = this.debug
        debug = debug2
    }

    enum class Operator {
        PLUS,
        MINUS,
        MULTIPLY,
        DIVIDE,
        NONE
    }

    fun getOperationBySign(operator: Operator): String {
        return when (operator) {
            Operator.PLUS -> "+"
            Operator.MINUS -> "-"
            Operator.MULTIPLY -> "×"
            Operator.DIVIDE -> "÷"
            else -> ""
        }
    }
}