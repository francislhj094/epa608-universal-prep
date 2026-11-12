package com.epa608.hvac.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for Job-Site Field Tools:
 * - Refrigerant P-T calculations & ASHRAE Standard 34 properties
 * - 40 CFR § 82.156 Table 1 Evacuation requirements
 * - 40 CFR § 82.152 & § 82.157 Leak rate calculations and statutory thresholds
 */
class FieldToolsTest {

    // MARK: - Refrigerant P-T & Safety Tests

    @Test
    fun testRefrigerantEnumProperties() {
        val allRefrigerants = RefrigerantType.entries
        assertEquals(5, allRefrigerants.size)

        for (ref in allRefrigerants) {
            assertNotNull(ref.code)
            assertNotNull(ref.displayName)
            assertNotNull(ref.ashraeSafetyGroup)
            assertTrue(ref.gwp > 0)
            assertEquals(ref.name, ref.id)
        }
    }

    @Test
    fun testRefrigerantSaturationPressureMonotonicity() {
        // As temperature rises, saturation pressure must strictly rise
        val temperatures = listOf(-20.0, 0.0, 40.0, 70.0, 100.0, 120.0, 140.0)

        for (ref in RefrigerantType.entries) {
            var prevPressure = -1.0
            for (temp in temperatures) {
                val pressure = ref.saturationPressurePsig(temp)
                assertTrue("Pressure at ${temp}°F for ${ref.code} must be non-negative", pressure >= 0.0)
                if (prevPressure >= 0.0) {
                    assertTrue(
                        "Pressure at ${temp}°F (${pressure}) must exceed pressure at previous temp (${prevPressure}) for ${ref.code}",
                        pressure > prevPressure
                    )
                }
                prevPressure = pressure
            }
        }
    }

    @Test
    fun testRefrigerantPressureHierarchyAtTypicalEvaporatorTemp() {
        // At standard 40°F evaporator saturation:
        // R-32 operates at highest pressure, followed closely by R-410A and R-454B.
        // R-22 operates lower (~68-75 psig).
        // R-134a operates lowest (~35 psig).
        val temp = 40.0
        val pR32 = RefrigerantType.R32.saturationPressurePsig(temp)
        val pR410A = RefrigerantType.R410A.saturationPressurePsig(temp)
        val pR454B = RefrigerantType.R454B.saturationPressurePsig(temp)
        val pR22 = RefrigerantType.R22.saturationPressurePsig(temp)
        val pR134A = RefrigerantType.R134A.saturationPressurePsig(temp)

        // R-32 > R-410A > R-454B > R-22 > R-134a
        assertTrue("R-32 ($pR32) should be higher than R-410A ($pR410A)", pR32 >= pR410A)
        assertTrue("R-410A ($pR410A) should be higher than R-454B ($pR454B)", pR410A >= pR454B)
        assertTrue("R-454B ($pR454B) should be higher than R-22 ($pR22)", pR454B > pR22)
        assertTrue("R-22 ($pR22) should be higher than R-134A ($pR134A)", pR22 > pR134A)
    }

    @Test
    fun testRefrigerantA2LClassification() {
        assertTrue(RefrigerantType.R454B.isA2L)
        assertEquals("A2L", RefrigerantType.R454B.ashraeSafetyGroup)

        assertTrue(RefrigerantType.R32.isA2L)
        assertEquals("A2L", RefrigerantType.R32.ashraeSafetyGroup)

        assertFalse(RefrigerantType.R410A.isA2L)
        assertEquals("A1", RefrigerantType.R410A.ashraeSafetyGroup)

        assertFalse(RefrigerantType.R22.isA2L)
        assertEquals("A1", RefrigerantType.R22.ashraeSafetyGroup)

        assertFalse(RefrigerantType.R134A.isA2L)
        assertEquals("A1", RefrigerantType.R134A.ashraeSafetyGroup)
    }

    // MARK: - Evacuation Table 1 Tests

    @Test
    fun testEvacuationTableStructure() {
        assertEquals(4, EvacuationTable.categories.size)

        val smallAppliances = EvacuationTable.categories[0]
        assertTrue(smallAppliances.category.contains("Small Appliances"))
        assertEquals(3, smallAppliances.specs.size)

        val highPressure = EvacuationTable.categories[1]
        assertTrue(highPressure.category.contains("High-Pressure"))
        assertEquals(5, highPressure.specs.size)

        val mediumPressure = EvacuationTable.categories[2]
        assertTrue(mediumPressure.category.contains("Medium-Pressure"))
        assertEquals(4, mediumPressure.specs.size)

        val lowPressure = EvacuationTable.categories[3]
        assertTrue(lowPressure.category.contains("Low-Pressure"))
        assertEquals(4, lowPressure.specs.size)
    }

    @Test
    fun testEvacuationTableContentAccuracy() {
        // Medium pressure >= 200 lbs post-1993 must require 15 inches Hg
        val medCat = EvacuationTable.categories.first { it.category.contains("Medium-Pressure") }
        val medOver200 = medCat.specs.first { it.condition.contains(">= 200 lbs (Post-1993)") }
        assertEquals("15 inches Hg vacuum", medOver200.requiredLevel)

        // Low pressure leak test pressure must not exceed 10 psig
        val lowCat = EvacuationTable.categories.first { it.category.contains("Low-Pressure") }
        val leakTestSpec = lowCat.specs.first { it.condition.contains("Max Leak Test Pressure") }
        assertTrue(leakTestSpec.requiredLevel.contains("10 psig"))
    }

    // MARK: - Leak Rate Calculator Tests

    @Test
    fun testLeakRateZeroAndNegativeEdgeCases() {
        // Zero or negative charge should return 0.0
        assertEquals(0.0, LeakRateCalculator.calculateLeakRate(0.0, 10.0, 30.0), 0.001)
        assertEquals(0.0, LeakRateCalculator.calculateLeakRate(-50.0, 10.0, 30.0), 0.001)

        // Negative refrigerant added should return 0.0
        assertEquals(0.0, LeakRateCalculator.calculateLeakRate(100.0, -5.0, 30.0), 0.001)

        // Zero or negative days should return 0.0
        assertEquals(0.0, LeakRateCalculator.calculateLeakRate(100.0, 10.0, 0.0), 0.001)
        assertEquals(0.0, LeakRateCalculator.calculateLeakRate(100.0, 10.0, -10.0), 0.001)
    }

    @Test
    fun testLeakRateClampingAt365Days() {
        // 50 lb system, 5 lbs added: (5/50) = 10%
        // At 365 days -> 10% * (365/365) = 10%
        val rate365 = LeakRateCalculator.calculateLeakRate(50.0, 5.0, 365.0)
        assertEquals(10.0, rate365, 0.001)

        // At 730 days (2 years), effective days clamped to 365 -> still 10%
        val rate730 = LeakRateCalculator.calculateLeakRate(50.0, 5.0, 730.0)
        assertEquals(10.0, rate730, 0.001)

        // At 30 days -> 10% * (365/30) = 121.67%
        val rate30 = LeakRateCalculator.calculateLeakRate(50.0, 5.0, 30.0)
        assertEquals(121.67, rate30, 0.01)
    }

    @Test
    fun testLeakTriggerEnforcementBothThresholds() {
        // With enforce15LbThreshold = true (default):
        // 14.9 lb charge does not trigger even with 100% leak rate
        assertFalse(LeakRateCalculator.isExceedingTrigger(14.9, 100.0, ApplianceType.COMFORT_COOLING, enforce15LbThreshold = true))
        // 15.0 lb charge triggers if > 10%
        assertTrue(LeakRateCalculator.isExceedingTrigger(15.0, 10.1, ApplianceType.COMFORT_COOLING, enforce15LbThreshold = true))

        // With enforce15LbThreshold = false (ODS 50 lb rule):
        // 49.9 lb charge does not trigger
        assertFalse(LeakRateCalculator.isExceedingTrigger(49.9, 100.0, ApplianceType.COMFORT_COOLING, enforce15LbThreshold = false))
        // 50.0 lb charge triggers
        assertTrue(LeakRateCalculator.isExceedingTrigger(50.0, 10.1, ApplianceType.COMFORT_COOLING, enforce15LbThreshold = false))
    }

    @Test
    fun testApplianceTypeThresholds() {
        // Exact boundary checks (threshold is non-inclusive: must EXCEED to trigger)
        assertFalse(LeakRateCalculator.isExceedingTrigger(100.0, 10.0, ApplianceType.COMFORT_COOLING))
        assertTrue(LeakRateCalculator.isExceedingTrigger(100.0, 10.01, ApplianceType.COMFORT_COOLING))

        assertFalse(LeakRateCalculator.isExceedingTrigger(100.0, 20.0, ApplianceType.COMMERCIAL_REFRIGERATION))
        assertTrue(LeakRateCalculator.isExceedingTrigger(100.0, 20.01, ApplianceType.COMMERCIAL_REFRIGERATION))

        assertFalse(LeakRateCalculator.isExceedingTrigger(100.0, 30.0, ApplianceType.INDUSTRIAL_PROCESS))
        assertTrue(LeakRateCalculator.isExceedingTrigger(100.0, 30.01, ApplianceType.INDUSTRIAL_PROCESS))
    }
}
