package com.epa608.hvac.domain

import kotlin.math.max
import kotlin.math.pow

/**
 * ASHRAE Standard 34 Safety Group & AIM Act GWP reference.
 */
enum class RefrigerantType(
    val code: String,
    val displayName: String,
    val ashraeSafetyGroup: String, // A1, A2L, B2L, A3
    val isA2L: Boolean,
    val gwp: Int
) {
    R410A("R-410A", "R-410A (A1)", "A1", false, 2088),
    R454B("R-454B", "R-454B (A2L)", "A2L", true, 466),
    R32("R-32", "R-32 (A2L)", "A2L", true, 675),
    R22("R-22", "R-22 (HCFC)", "A1", false, 1810),
    R134A("R-134a", "R-134a (HFC)", "A1", false, 1430);

    val id: String get() = name

    /**
     * Saturated vapor pressure (PSIG) approximation across standard evaporator and condensing ranges (-20°F to 140°F).
     */
    fun saturationPressurePsig(temperatureF: Double): Double {
        return when (this) {
            R410A -> max(0.0, 0.0038 * temperatureF.pow(2) + 1.85 * temperatureF + 42.0)
            R454B -> max(0.0, 0.0036 * temperatureF.pow(2) + 1.80 * temperatureF + 39.0)
            R32 -> max(0.0, 0.0039 * temperatureF.pow(2) + 1.90 * temperatureF + 44.0)
            R22 -> max(0.0, 0.0022 * temperatureF.pow(2) + 1.15 * temperatureF + 28.0)
            R134A -> max(0.0, 0.0019 * temperatureF.pow(2) + 0.85 * temperatureF + 15.0)
        }
    }
}
