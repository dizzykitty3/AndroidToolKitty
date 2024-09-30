package me.dizzykitty3.androidtoolkitty.theme

import android.content.Context
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.unit.sp

fun typography(context: Context): Typography {
    fun getFont(weight: Int) = FontFamily(
        Font(
            "font/manrope_variable.ttf", context.assets,
            variationSettings = FontVariation.Settings(
                FontVariation.weight(weight),
            ),
        )
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = getFont(750),
            fontSize = 57.sp
        ),
        displayMedium = TextStyle(
            fontFamily = getFont(900),
            fontSize = 45.sp
        ),
        displaySmall = TextStyle(
            fontFamily = getFont(600),
            fontSize = 22.sp
        ),
        headlineLarge = TextStyle(
            fontFamily = getFont(800),
            fontSize = 36.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = getFont(700),
            fontSize = 28.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = getFont(700),
            fontSize = 24.sp
        ),
        titleLarge = TextStyle(
            fontFamily = getFont(700),
            fontSize = 22.sp
        ),
        titleMedium = TextStyle(
            fontFamily = getFont(700),
            fontSize = 16.sp
        ),
        titleSmall = TextStyle(
            fontFamily = getFont(700),
            fontSize = 14.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = getFont(700),
            fontSize = 16.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = getFont(700),
            fontSize = 14.sp
        ),
        bodySmall = TextStyle(
            fontFamily = getFont(600),
            fontSize = 14.sp
        ),
        labelLarge = TextStyle(
            fontFamily = getFont(700),
            fontSize = 14.sp
        ),
        labelMedium = TextStyle(
            fontFamily = getFont(700),
            fontSize = 12.sp
        ),
        labelSmall = TextStyle(
            fontFamily = getFont(600),
            fontSize = 11.sp
        )
    )
}