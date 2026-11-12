package com.epa608.hvac.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegulatoryComplianceTest {

    @Test
    fun testEvacuationTable1Compliance() {
        // High-pressure (<200 lbs, post-1993) must be 0 inches Hg (0 psig)
        val highPressureCat = EvacuationTable.categories.first { it.category.contains("High-Pressure") }
        val hpUnder200 = highPressureCat.specs.first { it.condition.contains("< 200 lbs (Post-1993)") }
        assertEquals("0 inches Hg (0 psig)", hpUnder200.requiredLevel)

        // High-pressure (>=200 lbs, post-1993) must be 10 inches Hg vacuum
        val hpOver200 = highPressureCat.specs.first { it.condition.contains(">= 200 lbs (Post-1993)") }
        assertEquals("10 inches Hg vacuum", hpOver200.requiredLevel)

        // Low-pressure centrifugal chiller (post-1993) must be 25 mm Hg absolute
        val lowPressureCat = EvacuationTable.categories.first { it.category.contains("Low-Pressure") }
        val lpSpec = lowPressureCat.specs.first { it.condition.contains("Post-1993") }
        assertEquals("25 mm Hg absolute", lpSpec.requiredLevel)

        // Rupture disc setting must be strictly 15 psig per ASHRAE 15
        val ruptureSpec = lowPressureCat.specs.first { it.condition.contains("Rupture Disc") }
        assertTrue(ruptureSpec.requiredLevel.contains("15 psig"))
    }

    @Test
    fun testAnnualizedLeakRateFormulaWith365DayClamping() {
        // Test case 1: 120 lb commercial system, 15 lbs added, 90 days elapsed
        // (15 / 120) * (365 / 90) * 100% = 0.125 * 4.0555... * 100 = 50.69%
        val rate1 = LeakRateCalculator.calculateLeakRate(
            fullChargeLbs = 120.0,
            refrigerantAddedLbs = 15.0,
            daysSinceLastAddition = 90.0
        )
        assertEquals(50.69, rate1, 0.02)
        // Commercial trigger is 20%, so 50.69% exceeds trigger
        assertTrue(
            LeakRateCalculator.isExceedingTrigger(
                fullChargeLbs = 120.0,
                leakRatePercentage = rate1,
                applianceType = ApplianceType.COMMERCIAL_REFRIGERATION
            )
        )

        // Test case 2: Elapsed days exceed 365 (e.g. 500 days).
        // 40 CFR § 82.152 mandates clamping to min(days, 365.0) -> effectiveDays = 365.0
        // 100 lb system, 10 lbs added, 500 days
        // (10 / 100) * (365 / 365) * 100% = 10.0%
        val rateClamped = LeakRateCalculator.calculateLeakRate(
            fullChargeLbs = 100.0,
            refrigerantAddedLbs = 10.0,
            daysSinceLastAddition = 500.0
        )
        assertEquals(10.0, rateClamped, 0.001)

        // 10.0% does NOT exceed Comfort Cooling 10% threshold (must be strictly > trigger)
        assertFalse(
            LeakRateCalculator.isExceedingTrigger(
                fullChargeLbs = 100.0,
                leakRatePercentage = rateClamped,
                applianceType = ApplianceType.COMFORT_COOLING
            )
        )

        // Test case 3: Charge below 15 lbs threshold (e.g. 10 lbs) does not trigger statutory leak repair
        assertFalse(
            LeakRateCalculator.isExceedingTrigger(
                fullChargeLbs = 10.0,
                leakRatePercentage = 75.0,
                applianceType = ApplianceType.COMFORT_COOLING
            )
        )
    }

    @Test
    fun testStatutoryLeakTriggerThresholds() {
        assertEquals(10.0, ApplianceType.COMFORT_COOLING.triggerRate, 0.001)
        assertEquals(20.0, ApplianceType.COMMERCIAL_REFRIGERATION.triggerRate, 0.001)
        assertEquals(30.0, ApplianceType.INDUSTRIAL_PROCESS.triggerRate, 0.001)

        assertTrue(ApplianceType.COMFORT_COOLING.citation.contains("82.157(c)(2)(iii)"))
        assertTrue(ApplianceType.COMMERCIAL_REFRIGERATION.citation.contains("82.157(c)(2)(ii)"))
        assertTrue(ApplianceType.INDUSTRIAL_PROCESS.citation.contains("82.157(c)(2)(i)"))
    }

    @Test
    fun testAIMActGWPLimitsAndA2LRefrigerants() {
        // Under 40 CFR Part 84, residential AC limit is 700 GWP
        val r454b = RefrigerantType.R454B
        assertTrue(r454b.isA2L)
        assertEquals("A2L", r454b.ashraeSafetyGroup)
        assertTrue("R-454B GWP (${r454b.gwp}) must be <= 700", r454b.gwp <= 700)

        val r32 = RefrigerantType.R32
        assertTrue(r32.isA2L)
        assertEquals("A2L", r32.ashraeSafetyGroup)
        assertTrue("R-32 GWP (${r32.gwp}) must be <= 700", r32.gwp <= 700)

        val r410a = RefrigerantType.R410A
        assertFalse(r410a.isA2L)
        assertTrue("R-410A GWP (${r410a.gwp}) exceeds 700 and is phased down", r410a.gwp > 700)
    }
}
