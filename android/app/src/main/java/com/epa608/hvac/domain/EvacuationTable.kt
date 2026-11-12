package com.epa608.hvac.domain

/**
 * 40 CFR § 82.156 Table 1 Required Levels of Evacuation for Refrigerant Recovery.
 */
object EvacuationTable {

    data class EvacuationCategory(
        val category: String,
        val specs: List<EvacuationSpec>
    )

    data class EvacuationSpec(
        val condition: String,
        val requiredLevel: String
    )

    val categories: List<EvacuationCategory> = listOf(
        EvacuationCategory(
            category = "Small Appliances (Type I - 40 CFR § 82.156(a))",
            specs = listOf(
                EvacuationSpec("Post-1993 Equipment (Running)", "90% charge or 4\" Hg vacuum"),
                EvacuationSpec("Post-1993 Equipment (Inoperative)", "80% charge or 4\" Hg vacuum"),
                EvacuationSpec("Pre-1993 Equipment (Any)", "80% charge or 4\" Hg vacuum")
            )
        ),
        EvacuationCategory(
            category = "High-Pressure: R-22, R-410A (Type II - Table 1)",
            specs = listOf(
                EvacuationSpec("< 200 lbs (Post-1993)", "0 inches Hg (0 psig)"),
                EvacuationSpec("< 200 lbs (Pre-1993)", "0 inches Hg (0 psig)"),
                EvacuationSpec(">= 200 lbs (Post-1993)", "10 inches Hg vacuum"),
                EvacuationSpec(">= 200 lbs (Pre-1993)", "4 inches Hg vacuum"),
                EvacuationSpec("Leaking Appliance Exception", "0 psig (isolate non-leaking)")
            )
        ),
        EvacuationCategory(
            category = "Medium-Pressure: R-134a, R-12 (Type II - Table 1)",
            specs = listOf(
                EvacuationSpec("< 200 lbs (Post-1993)", "10 inches Hg vacuum"),
                EvacuationSpec("< 200 lbs (Pre-1993)", "4 inches Hg vacuum"),
                EvacuationSpec(">= 200 lbs (Post-1993)", "15 inches Hg vacuum"),
                EvacuationSpec(">= 200 lbs (Pre-1993)", "4 inches Hg vacuum")
            )
        ),
        EvacuationCategory(
            category = "Low-Pressure Chillers: R-123 (Type III - Table 1)",
            specs = listOf(
                EvacuationSpec("Post-1993 Equipment", "25 mm Hg absolute"),
                EvacuationSpec("Pre-1993 Equipment", "25 inches Hg vacuum"),
                EvacuationSpec("Rupture Disc Setting", "15 psig (ASHRAE 15)"),
                EvacuationSpec("Max Leak Test Pressure", "10 psig (Dry Nitrogen)")
            )
        )
    )
}
