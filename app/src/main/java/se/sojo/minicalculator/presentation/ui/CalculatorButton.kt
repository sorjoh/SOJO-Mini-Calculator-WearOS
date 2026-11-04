package se.sojo.minicalculator.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material3.Text
import se.sojo.minicalculator.R

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Cyan,
    textColor: Color = Color.Black,
    font: FontFamily = FontFamily(Font(R.font.ms_pgothic_regular)),
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    shapeSize: Dp = 8.dp,
    onClick: () -> Unit,
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(0.dp)
                .clip(RoundedCornerShape(shapeSize))
                .background(backgroundColor)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier,
                text = text,
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = fontSize,
                fontFamily = font,
                fontWeight = fontWeight
            )
        }
    }