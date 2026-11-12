package com.epa608.hvac.domain

/**
 * 40 CFR § 82.157 Appliance Maintenance & Leak Repair Triggers.
 */
enum class ApplianceType(
    val displayName: String,
    val triggerRate: Double,
    val citation: String
) {
    COMFORT_COOLING(
        displayName = "Comfort Cooling",
        triggerRate = 10.0,
        citation = "40 CFR § 82.157(c)(2)(iii) [10% Threshold]"
    ),
    COMMERCIAL_REFRIGERATION(
        displayName = "Commercial",
        triggerRate = 20.0,
        citation = "40 CFR § 82.157(c)(2)(ii) [20% Threshold]"
    ),
    INDUSTRIAL_PROCESS(
        displayName = "Industrial Process",
        triggerRate = 30.0,
        citation = "40 CFR § 82.157(c)(2)(i) [30% Threshold]"
    );

    val id: String get() = name
}

/**
 * Clean Air Act Title VI / 40 CFR § 82.152 Annualized Leak Rate Calculator.
 */
object LeakRateCalculator {

    /**
     * Calculates the annualized leak rate in accordance with 40 CFR § 82.152 (Annualizing Method).
     *
     * Formula:
     * Leak Rate (%) = (Pounds of Refrigerant Added / Full Charge) * (365 / Shorter of days or 365) * 100%
     *
     * @param fullChargeLbs Full nominal system charge in pounds
     * @param refrigerantAddedLbs Pounds of refrigerant added
     * @param daysSinceLastAddition Days elapsed since last refrigerant addition
     * @return Annualized leak rate percentage (e.g. 24.5 for 24.5%)
     */
    fun calculateLeakRate(
        fullChargeLbs: Double,
        refrigerantAddedLbs: Double,
        daysSinceLastAddition: Double
    ): Double {
        if (fullChargeLbs <= 0.0 || refrigerantAddedLbs < 0.0 || daysSinceLastAddition <= 0.0) {
            return 0.0
        }
        val effectiveDays = minOf(daysSinceLastAddition, 365.0)
        return (refrigerantAddedLbs / fullChargeLbs) * (365.0 / effectiveDays) * 100.0
    }

    /**
     * Determines whether the calculated leak rate exceeds the statutory threshold.
     * Enforces the 50-pound threshold for Title VI ODS and 15-pound threshold for AIM Act HFC rules.
     */
    fun isExceedingTrigger(
        fullChargeLbs: Double,
        leakRatePercentage: Double,
        applianceType: ApplianceType,
        enforce15LbThreshold: Boolean = true
    ): Boolean {
        val minimumCharge = if (enforce15LbThreshold) 15.0 else 50.0
        if (fullChargeLbs < minimumCharge) return false
        return leakRatePercentage > applianceType.triggerRate
    }
}
