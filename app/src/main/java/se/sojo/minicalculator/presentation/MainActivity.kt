package se.sojo.minicalculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.material3.AppScaffold
import androidx.wear.compose.ui.tooling.preview.WearPreviewDevices
import androidx.wear.compose.ui.tooling.preview.WearPreviewFontScales
import se.sojo.minicalculator.presentation.theme.SOJOMiniCalculatorTheme
import se.sojo.minicalculator.presentation.ui.CalculatorScreen
import java.text.DecimalFormatSymbols

// Use debugging if needed
const val DEBUG: Boolean = true

// Max length of the display numbers
val MAX_INPUT_LENGTH: Int = 15

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WearApp()
        }
    }
}

@Composable
fun WearApp() {
    SOJOMiniCalculatorTheme {
        AppScaffold(
            timeText = { } // Hide the clock
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                val defaultRowModifier: Modifier = Modifier.weight(0.20f)
                val defaultButtonModifier: Modifier = Modifier.weight(0.25f)

                // Get decimal separator
                val dfs: DecimalFormatSymbols = DecimalFormatSymbols()
                val decimalSeparator = dfs.decimalSeparator

                val calculator = Calculator(decimalSeparator, MAX_INPUT_LENGTH, DEBUG)
                calculator.resetValues()

                CalculatorScreen(calculator, defaultRowModifier, defaultButtonModifier)
            }
        }
    }
}

/*
@Composable
fun WearApp() {
    SOJOMiniCalculatorTheme {
        AppScaffold {
            val listState = rememberTransformingLazyColumnState()
            val transformationSpec = rememberTransformationSpec()
            ScreenScaffold(
                scrollState = listState,
                edgeButton = {
                    EdgeButton(
                        onClick = { /*TODO*/ },
                        colors =
                            ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                    ) {
                        Text("More")
                    }
                },
            ) { contentPadding -> // ScreenScaffold provides default padding; adjust as needed
                TransformingLazyColumn(contentPadding = contentPadding, state = listState) {
                    item {
                        ListHeader(
                            modifier =
                                Modifier.fillMaxWidth().transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text(text = stringResource(R.string.hello_world, greetingName))
                        }
                    }
                    item {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Button A")
                        }
                    }
                    item {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Button B")
                        }
                    }
                    item {
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxWidth()
                                .transformedHeight(this, transformationSpec),
                            transformation = SurfaceTransformation(transformationSpec),
                        ) {
                            Text("Button C")
                        }
                    }

                }
            }
        }
    }
}
*/

@WearPreviewDevices
@WearPreviewFontScales
@Composable
fun DefaultPreview() {
    WearApp()
}