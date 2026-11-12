package com.epa608.hvac.ui.theme

import android.app.Activity
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat

/**
 * Android Accessibility Standard minimum touch target dimension (48dp).
 */
val MinimumTouchTargetDp = 48.dp

/**
 * Modifier extension ensuring all interactive controls meet Android Accessibility Guidelines (>= 48x48dp).
 */
fun Modifier.minimumTouchTarget(): Modifier = this.sizeIn(
    minWidth = MinimumTouchTargetDp,
    minHeight = MinimumTouchTargetDp
)

/**
 * Modifier ensuring minimum height of 48dp for full-width buttons.
 */
fun Modifier.minimumButtonHeight(): Modifier = this.defaultMinSize(minHeight = MinimumTouchTargetDp)

private val IndustrialDarkColorScheme = darkColorScheme(
    primary = DarkFieldColors.CyanAccent,
    onPrimary = Color.Black,
    primaryContainer = DarkFieldColors.ElevatedSurface,
    onPrimaryContainer = DarkFieldColors.CyanAccent,
    secondary = DarkFieldColors.SafetyAmber,
    onSecondary = Color.Black,
    secondaryContainer = DarkFieldColors.ElevatedSurface,
    onSecondaryContainer = DarkFieldColors.SafetyAmber,
    tertiary = DarkFieldColors.PassGreen,
    onTertiary = Color.Black,
    background = DarkFieldColors.Charcoal,
    onBackground = DarkFieldColors.TextPrimary,
    surface = DarkFieldColors.Navy,
    onSurface = DarkFieldColors.TextPrimary,
    surfaceVariant = DarkFieldColors.ElevatedSurface,
    onSurfaceVariant = DarkFieldColors.TextSecondary,
    outline = DarkFieldColors.CardBorder,
    outlineVariant = DarkFieldColors.CardBorder,
    error = DarkFieldColors.HazardRed,
    onError = Color.White
)

private val IndustrialRooftopLightColorScheme = lightColorScheme(
    primary = RooftopSunlightColors.CyanAccent,
    onPrimary = Color.White,
    primaryContainer = RooftopSunlightColors.ElevatedSurface,
    onPrimaryContainer = RooftopSunlightColors.CyanAccent,
    secondary = RooftopSunlightColors.SafetyAmber,
    onSecondary = Color.White,
    secondaryContainer = RooftopSunlightColors.ElevatedSurface,
    onSecondaryContainer = RooftopSunlightColors.SafetyAmber,
    tertiary = RooftopSunlightColors.PassGreen,
    onTertiary = Color.White,
    background = RooftopSunlightColors.Background,
    onBackground = RooftopSunlightColors.TextPrimary,
    surface = RooftopSunlightColors.Surface,
    onSurface = RooftopSunlightColors.TextPrimary,
    surfaceVariant = RooftopSunlightColors.ElevatedSurface,
    onSurfaceVariant = RooftopSunlightColors.TextSecondary,
    outline = RooftopSunlightColors.CardBorder,
    outlineVariant = RooftopSunlightColors.CardBorder,
    error = RooftopSunlightColors.HazardRed,
    onError = Color.White
)

fun getIndustrialTypography(isRooftop: Boolean = IndustrialColors.isRooftopMode): Typography {
    val primary = if (isRooftop) RooftopSunlightColors.TextPrimary else DarkFieldColors.TextPrimary
    val secondary = if (isRooftop) RooftopSunlightColors.TextSecondary else DarkFieldColors.TextSecondary
    val muted = if (isRooftop) RooftopSunlightColors.TextMuted else DarkFieldColors.TextMuted

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Black,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            color = primary
        ),
        headlineMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            lineHeight = 30.sp,
            color = primary
        ),
        headlineSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            color = primary
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            color = primary
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = primary
        ),
        titleSmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.sp,
            color = muted
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = primary
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            color = secondary
        ),
        bodySmall = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            color = secondary
        ),
        labelLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 1.sp,
            color = muted
        )
    )
}

val IndustrialTypography: Typography
    get() = getIndustrialTypography(IndustrialColors.isRooftopMode)

/**
 * Monospaced Typography styles for technical readouts, timers, gauge values, and CFR citations.
 */
object IndustrialTechnicalText {
    val GaugeValue: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = 36.sp,
            lineHeight = 42.sp,
            color = IndustrialColors.CyanAccent
        )

    val GaugeValueAmber: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = 36.sp,
            lineHeight = 42.sp,
            color = IndustrialColors.SafetyAmber
        )

    val TimerLarge: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 22.sp,
            color = IndustrialColors.TextPrimary
        )

    val PercentageDial: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            color = IndustrialColors.TextPrimary
        )

    val OptionLetter: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Black,
            fontSize = 15.sp,
            lineHeight = 20.sp
        )

    val CitationBadge: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp,
            color = IndustrialColors.TextMuted
        )
}

/**
 * Root Material 3 Industrial Theme.
 * Supports standard Industrial Dark Theme and Rooftop Sunlight High-Contrast Mode.
 */
@Composable
fun EPA608Theme(
    isRooftopMode: Boolean = IndustrialColors.isRooftopMode,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isRooftopMode) IndustrialRooftopLightColorScheme else IndustrialDarkColorScheme
    val view = LocalView.current

    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window ?: return@SideEffect
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = isRooftopMode
                isAppearanceLightNavigationBars = isRooftopMode
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getIndustrialTypography(isRooftopMode),
        content = content
    )
}
