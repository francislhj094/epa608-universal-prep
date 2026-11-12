package com.epa608.hvac.data

import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question

/**
 * Official 2026/2027 EPA 608 Master Question Bank.
 * Strictly compliant with Clean Air Act Title VI (40 CFR Part 82) and EPA AIM Act (40 CFR Part 84).
 */
object HVACQuestionBank {

    // MARK: - 10-Question Diagnostic Assessment (The Day-0 Onboarding Shock)
    val diagnosticQuestions: List<Question> = listOf(
        Question(
            id = "diag-1",
            section = EPASection.CORE,
            questionText = "Under the EPA AIM Act (40 CFR Part 84) and AHRI Guideline N, how are ASHRAE A2L mildly flammable refrigerants (such as R-454B and R-32) distinguished on recovery cylinders?",
            options = listOf(
                "Recovery cylinders must have a solid red top with blue lettering",
                "Recovery cylinders feature a red band/shoulder and left-handed reverse valve threads (CGA-164)",
                "A2L cylinders are identical to standard cylinders and require no physical distinction",
                "A2L refrigerants cannot be recovered into cylinders and must be vented directly"
            ),
            correctIndex = 1,
            explanation = "Per DOT and EPA guidelines under 40 CFR Part 84 and CGA-164, A2L recovery cylinders feature a red shoulder/band and left-hand (reverse) threads to prevent cross-contamination with A1 equipment.",
            citation = "40 CFR § 84.54 / CGA-164",
            isA2L = true
        ),
        Question(
            id = "diag-2",
            section = EPASection.CORE,
            questionText = "What is the primary atmospheric hazard caused by chlorine atoms released from CFC and HCFC molecules?",
            options = listOf(
                "They increase atmospheric humidity, leading to acid rain",
                "A single chlorine atom can catalytically destroy up to 100,000 ozone molecules in the stratosphere",
                "They combine with carbon dioxide to deplete ground-level oxygen",
                "They react with nitrogen to create toxic photochemical smog"
            ),
            correctIndex = 1,
            explanation = "Chlorine in CFCs and HCFCs does not rain out because it is insoluble in water. Upon reaching the stratosphere, UV rays break off chlorine atoms, each capable of breaking apart up to 100,000 ozone (O3) molecules.",
            citation = "Clean Air Act Title VI / 40 CFR Part 82",
            isA2L = false
        ),
        Question(
            id = "diag-3",
            section = EPASection.TYPE_I,
            questionText = "When using a self-contained (active) recovery unit on a small appliance with an inoperative compressor, what percentage of refrigerant must be recovered?",
            options = listOf(
                "70% of the factory nameplate charge",
                "80% of the factory nameplate charge (or 4 inches of Hg vacuum)",
                "90% of the factory nameplate charge (or 4 inches of Hg vacuum)",
                "100% of the factory nameplate charge with zero remaining vapor"
            ),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.156(a)(2), if the small appliance compressor is NOT operational, technicians must recover at least 80% of the charge (or pull a 4-inch Hg vacuum). If the compressor runs, the threshold is 90%.",
            citation = "40 CFR § 82.156(a)(2)",
            isA2L = false
        ),
        Question(
            id = "diag-4",
            section = EPASection.TYPE_I,
            questionText = "Why must temporary piercing saddle valves be removed or permanently brazed over after completing service on a small appliance?",
            options = listOf(
                "Because they alter the internal electrical resistance of the compressor motor",
                "Because piercing valve rubber gaskets degrade and develop chronic leaks over time",
                "Because EPA regulations prohibit piercing valves from ever being used in the field",
                "Because they restrict the flow of refrigerant during normal operation"
            ),
            correctIndex = 1,
            explanation = "Solderless piercing valves rely on neoprene/rubber gaskets that dry out, harden, and leak refrigerant into the atmosphere over time. EPA rules mandate they be removed or sealed after servicing.",
            citation = "40 CFR § 82.156",
            isA2L = false
        ),
        Question(
            id = "diag-5",
            section = EPASection.TYPE_II,
            questionText = "Under current EPA Section 608 rules (40 CFR § 82.157(c)(2)), what is the annual leak rate trigger threshold for Commercial Refrigeration systems containing 50 or more pounds of refrigerant?",
            options = listOf(
                "10%",
                "20%",
                "30%",
                "35%"
            ),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(c)(2)(ii), the threshold for commercial refrigeration containing 50+ lbs is 20%. Comfort cooling is 10%, and industrial process refrigeration is 30%.",
            citation = "40 CFR § 82.157(c)(2)(ii)",
            isA2L = false
        ),
        Question(
            id = "diag-6",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, prior to opening a high-pressure appliance containing LESS than 200 lbs of R-22 or R-410A for a major repair using post-1993 recovery equipment, what vacuum level must be achieved?",
            options = listOf(
                "0 inches of Hg vacuum (0 psig)",
                "4 inches of Hg vacuum",
                "10 inches of Hg vacuum",
                "15 inches of Hg vacuum"
            ),
            correctIndex = 0,
            explanation = "Table 1 in 40 CFR § 82.156 mandates 0 inches of Hg vacuum (0 psig) for high-pressure appliances containing less than 200 pounds of refrigerant serviced with post-1993 equipment. 10 inches is required only for systems containing 200+ lbs.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "diag-7",
            section = EPASection.TYPE_II,
            questionText = "Which of the following operations constitutes a 'major repair' under EPA Section 608 definitions (40 CFR § 82.152)?",
            options = listOf(
                "Replacing a Schrader valve core",
                "Replacing a thermostatic expansion valve (TXV)",
                "Replacing the compressor, condenser, evaporator coil, or auxiliary heat exchanger",
                "Replacing a liquid line filter drier"
            ),
            correctIndex = 2,
            explanation = "EPA defines a 'major repair' strictly as the removal or replacement of the compressor, condenser, evaporator coil, or auxiliary heat exchanger coil.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "diag-8",
            section = EPASection.TYPE_III,
            questionText = "At what pressure does the safety rupture disc on a low-pressure centrifugal chiller discharge to prevent vessel explosion?",
            options = listOf(
                "5 psig",
                "15 psig",
                "30 psig",
                "50 psig"
            ),
            correctIndex = 1,
            explanation = "Low-pressure chillers are designed to operate in a vacuum and have thin evaporator shell walls. The standard rupture disc setting is strictly 15 psig (piped outdoors per ASHRAE 15).",
            citation = "ASHRAE Standard 15 / 40 CFR § 82.156",
            isA2L = false
        ),
        Question(
            id = "diag-9",
            section = EPASection.TYPE_III,
            questionText = "When recharging a low-pressure chiller (e.g. R-123) after a complete evacuation, why must refrigerant vapor be charged first before introducing liquid?",
            options = listOf(
                "Liquid refrigerant will ruin the oil filter element",
                "Liquid refrigerant expanding in a deep vacuum flash boils, dropping temperature below 32°F and freezing water inside the chiller tubes",
                "Vapor refrigerant is heavier than liquid refrigerant in low-pressure systems",
                "EPA regulations strictly prohibit charging liquid refrigerant into any HVAC system"
            ),
            correctIndex = 1,
            explanation = "Low-pressure liquid refrigerant boils at low temperatures under vacuum. Introducing liquid into a deep vacuum causes rapid flash boiling that drops temperatures below 32°F, freezing and bursting the water tubes.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "diag-10",
            section = EPASection.CORE,
            questionText = "What DOT specification markings must appear on an EPA-approved refrigerant recovery cylinder?",
            options = listOf(
                "DOT-2P or DOT-2Q",
                "DOT-4BA or DOT-4BW",
                "DOT-3AL or DOT-3AA",
                "DOT-UN1978 only"
            ),
            correctIndex = 1,
            explanation = "Department of Transportation (DOT) regulations mandate that refillable refrigerant recovery cylinders meet DOT-4BA or DOT-4BW standards and undergo hydrostatic pressure recertification every 5 years.",
            citation = "49 CFR § 178.51 & 40 CFR § 82.158",
            isA2L = false
        )
    )

    // MARK: - 400+ Question Master Pool (Core, Type I, Type II, Type III)
    val coreQuestions: List<Question> get() = CoreQuestions.list
    val type1Questions: List<Question> get() = Type1Questions.list
    val type2Questions: List<Question> get() = Type2Questions.list
    val type3Questions: List<Question> get() = Type3Questions.list

    /**
     * All non-diagnostic study questions across Core, Type I, Type II, and Type III.
     */
    val studyQuestions: List<Question> by lazy {
        coreQuestions + type1Questions + type2Questions + type3Questions
    }

    /**
     * All questions in the system (diagnostic + study questions).
     */
    val allQuestions: List<Question> by lazy {
        (diagnosticQuestions + studyQuestions).distinctBy { it.id }
    }

    /**
     * Lookup a question by its unique identifier.
     */
    fun findQuestionById(id: String): Question? {
        return allQuestions.firstOrNull { it.id == id }
    }

    /**
     * Get questions for a specific EPASection.
     */
    fun getQuestionsForSection(section: EPASection): List<Question> {
        return when (section) {
            EPASection.CORE -> coreQuestions
            EPASection.TYPE_I -> type1Questions
            EPASection.TYPE_II -> type2Questions
            EPASection.TYPE_III -> type3Questions
            EPASection.UNIVERSAL -> studyQuestions
        }
    }
}
