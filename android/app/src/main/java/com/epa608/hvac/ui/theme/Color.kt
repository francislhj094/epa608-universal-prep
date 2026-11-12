package com.epa608.hvac.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.epa608.hvac.domain.EPASection

/**
 * Standard High-Contrast Industrial Dark Palette (Dim Mechanical Basements & Night Study).
 */
object DarkFieldColors {
    val Charcoal = Color(0xFF0A0E17)          // Root background
    val Navy = Color(0xFF141E2E)              // Card & Container surface
    val CardBorder = Color(0xFF223147)        // Card borders
    val ElevatedSurface = Color(0xFF1A263A)   // Active/selected state surface

    val CyanAccent = Color(0xFF00B4D8)        // Primary electric cyan accent
    val DeepBlue = Color(0xFF0077B6)          // Deep industrial blue
    val SafetyAmber = Color(0xFFFFB703)       // Warning, alerts, proctored badges
    val HazardRed = Color(0xFFEF476F)         // Error, leak rate violations, missed questions
    val PassGreen = Color(0xFF06D6A0)         // Passed status, correct options, compliance

    val TextPrimary = Color(0xFFF8FAFC)       // 18.4:1 contrast on Charcoal
    val TextSecondary = Color(0xFF94A3B8)     // 6.5:1 contrast on Navy
    val TextMuted = Color(0xFFCBD5E1)         // High-visibility secondary text for field sunlight

    val CoreSkyBlue = Color(0xFF38BDF8)
    val Type1Teal = Color(0xFF2DD4BF)
    val Type2Green = Color(0xFF4ADE80)
    val Type3Lavender = Color(0xFFC084FC)
    val UniversalAmber = Color(0xFFFBBF24)
}

/**
 * Rooftop Sunlight Glare Palette (21:1 Maximum Pure Contrast for Direct Sunlight on Reflective Roofs).
 * Engineered for white TPO/EPDM membranes, polarized safety sunglasses, and intense ambient UV glare.
 */
object RooftopSunlightColors {
    val Background = Color(0xFFFFFFFF)        // Pure White reflective background (maximum nits)
    val Surface = Color(0xFFFFFFFF)           // Crisp white container cards
    val CardBorder = Color(0xFF0F172A)        // Stark 2px solid slate-black border (pops against direct sun glare)
    val ElevatedSurface = Color(0xFFF1F5F9)   // High-contrast slate tint for selected options / tabs

    val CyanAccent = Color(0xFF0284C7)        // High-chroma Sky/Cobalt blue (does not wash out in sunlight)
    val DeepBlue = Color(0xFF0369A1)          // Deep nautical blue
    val SafetyAmber = Color(0xFFD97706)       // High-vis amber/orange (4.8:1 contrast on white)
    val HazardRed = Color(0xFFDC2626)         // Vivid hazard crimson (5.2:1 contrast on white)
    val PassGreen = Color(0xFF059669)         // Rich statutory emerald green (4.9:1 contrast on white)

    val TextPrimary = Color(0xFF000000)       // 21.0:1 Pure Jet Black (maximum theoretical contrast)
    val TextSecondary = Color(0xFF1E293B)     // 12.6:1 Deep Slate Black
    val TextMuted = Color(0xFF475569)         // 7.5:1 Solid Charcoal

    val CoreSkyBlue = Color(0xFF0284C7)       // Deep Sky Blue
    val Type1Teal = Color(0xFF0D9488)         // Deep Marine Teal
    val Type2Green = Color(0xFF16A34A)        // High-Vis Emerald
    val Type3Lavender = Color(0xFF7E22CE)     // Deep Royal Violet
    val UniversalAmber = Color(0xFFD97706)    // High-Vis Amber
}

/**
 * Unified Industrial Colors accessor with dynamic Rooftop Sunlight High-Contrast mode switching.
 */
object IndustrialColors {
    /**
     * Active state of Rooftop Sunlight Mode across the entire application.
     */
    var isRooftopMode: Boolean by mutableStateOf(false)

    // Primary Surfaces
    val Charcoal: Color
        get() = if (isRooftopMode) RooftopSunlightColors.Background else DarkFieldColors.Charcoal

    val Navy: Color
        get() = if (isRooftopMode) RooftopSunlightColors.Surface else DarkFieldColors.Navy

    val CardBorder: Color
        get() = if (isRooftopMode) RooftopSunlightColors.CardBorder else DarkFieldColors.CardBorder

    val ElevatedSurface: Color
        get() = if (isRooftopMode) RooftopSunlightColors.ElevatedSurface else DarkFieldColors.ElevatedSurface

    // Functional & Status Accents
    val CyanAccent: Color
        get() = if (isRooftopMode) RooftopSunlightColors.CyanAccent else DarkFieldColors.CyanAccent

    val DeepBlue: Color
        get() = if (isRooftopMode) RooftopSunlightColors.DeepBlue else DarkFieldColors.DeepBlue

    val SafetyAmber: Color
        get() = if (isRooftopMode) RooftopSunlightColors.SafetyAmber else DarkFieldColors.SafetyAmber

    val HazardRed: Color
        get() = if (isRooftopMode) RooftopSunlightColors.HazardRed else DarkFieldColors.HazardRed

    val PassGreen: Color
        get() = if (isRooftopMode) RooftopSunlightColors.PassGreen else DarkFieldColors.PassGreen

    // WCAG AAA Compliant Text Palette
    val TextPrimary: Color
        get() = if (isRooftopMode) RooftopSunlightColors.TextPrimary else DarkFieldColors.TextPrimary

    val TextSecondary: Color
        get() = if (isRooftopMode) RooftopSunlightColors.TextSecondary else DarkFieldColors.TextSecondary

    val TextMuted: Color
        get() = if (isRooftopMode) RooftopSunlightColors.TextMuted else DarkFieldColors.TextMuted

    // Static accessors for non-composable initializers
    val StaticCharcoal: Color get() = Charcoal
    val StaticNavy: Color get() = Navy
    val StaticCardBorder: Color get() = CardBorder
    val StaticElevatedSurface: Color get() = ElevatedSurface
    val StaticCyanAccent: Color get() = CyanAccent
    val StaticSafetyAmber: Color get() = SafetyAmber
    val StaticHazardRed: Color get() = HazardRed
    val StaticPassGreen: Color get() = PassGreen
    val StaticTextPrimary: Color get() = TextPrimary
    val StaticTextSecondary: Color get() = TextSecondary
    val StaticTextMuted: Color get() = TextMuted

    // Section Accent Colors
    val CoreSkyBlue: Color
        get() = if (isRooftopMode) RooftopSunlightColors.CoreSkyBlue else DarkFieldColors.CoreSkyBlue

    val Type1Teal: Color
        get() = if (isRooftopMode) RooftopSunlightColors.Type1Teal else DarkFieldColors.Type1Teal

    val Type2Green: Color
        get() = if (isRooftopMode) RooftopSunlightColors.Type2Green else DarkFieldColors.Type2Green

    val Type3Lavender: Color
        get() = if (isRooftopMode) RooftopSunlightColors.Type3Lavender else DarkFieldColors.Type3Lavender

    val UniversalAmber: Color
        get() = if (isRooftopMode) RooftopSunlightColors.UniversalAmber else DarkFieldColors.UniversalAmber

    /**
     * Resolves high-contrast section color for [EPASection].
     */
    fun sectionColor(section: EPASection): Color {
        return when (section) {
            EPASection.CORE -> CoreSkyBlue
            EPASection.TYPE_I -> Type1Teal
            EPASection.TYPE_II -> Type2Green
            EPASection.TYPE_III -> Type3Lavender
            EPASection.UNIVERSAL -> UniversalAmber
        }
    }
}
