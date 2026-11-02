package se.sojo.minicalculator.presentation.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import se.sojo.minicalculator.presentation.Calculator

class CalculatorViewModel : ViewModel() {

    val outputValue = mutableStateOf<String>("0")

    fun onNumberInput(inputKey: String) {
        if (Calculator.debug)
            Log.d("SOJO Debug:", "test")

        Log.d("SOJO Debug:", Calculator.debug.toString())
    }

    fun onDecimalInput(decimal: Char) {

    }

    fun onOperatorInput(operation: Calculator.Operator) {

    }

    fun onEqual() {

    }
}