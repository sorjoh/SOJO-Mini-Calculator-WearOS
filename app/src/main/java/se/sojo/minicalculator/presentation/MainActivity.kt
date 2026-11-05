package se.sojo.minicalculator.presentation

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.material3.MaterialTheme
import se.sojo.minicalculator.presentation.theme.AppTheme
import se.sojo.minicalculator.presentation.ui.CalculatorScreen
import java.text.DecimalFormatSymbols

// Use debugging if needed
const val DEBUG: Boolean = false

// Max length of the display numbers
const val MAX_INPUT_LENGTH: Int = 10

@Suppress("DEPRECATION")
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        if (DEBUG)
            Log.d("SOJO Debug:", "MainActivity -> onCreate()")

        super.onCreate(savedInstanceState)

        // Check if it is a rounded wearable
        val isScreenRound = resources.configuration.isScreenRound

        // Get the width and height of the wearable
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        setContent { WearApp(isScreenRound, width, height) }
    }
}

@Composable
fun WearApp(isScreenRound: Boolean, width: Int, height: Int) {
    if (DEBUG)
        Log.d("SOJO Debug:", "MainActivity -> WearApp()")

    // Calculator background color
    val backgroundColor = MaterialTheme.colorScheme.background

    AppTheme {
        AppScaffold(
            modifier = Modifier.background(backgroundColor),
            timeText = { } // Hide the clock
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Get decimal separator
                val dfs = DecimalFormatSymbols()
                val decimalSeparator = dfs.decimalSeparator

                // Initialize Calculator
                val calculator = Calculator(decimalSeparator, MAX_INPUT_LENGTH, DEBUG)
                calculator.resetValues()

                // Adjust the maximum input length for the display based on the type and size of wearable
                //      Square = 360x360
                //      Small Round = 384x384
                //      Rectangular = 402x476
                //      Large Round = 454x454
                calculator.maxInputLength += when (width) {
                    in 0 .. 401 -> if (isScreenRound) -3 else 0
                    else -> if (isScreenRound) -2 else 0
                }

                // Initialize UI
                CalculatorScreen(calculator, isScreenRound, width, height, backgroundColor)
            }
        }
    }
}