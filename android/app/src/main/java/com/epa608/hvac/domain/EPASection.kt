package com.epa608.hvac.domain

/**
 * Official EPA Section 608 Certification Sections under 40 CFR Part 82 and AIM Act (40 CFR Part 84).
 */
enum class EPASection(
    val rawValue: String,
    val title: String,
    val subtitle: String,
    val totalExamQuestions: Int
) {
    CORE(
        rawValue = "Core",
        title = "Core Section",
        subtitle = "Clean Air Act, ODP, GWP, Montreal Protocol & A2L Safety",
        totalExamQuestions = 25
    ),
    TYPE_I(
        rawValue = "Type I",
        title = "Type I: Small Appliances",
        subtitle = "<5 lbs factory-sealed appliances, piercing valves & recovery",
        totalExamQuestions = 25
    ),
    TYPE_II(
        rawValue = "Type II",
        title = "Type II: High-Pressure Systems",
        subtitle = "Commercial AC, leak repair triggers & evacuation levels",
        totalExamQuestions = 25
    ),
    TYPE_III(
        rawValue = "Type III",
        title = "Type III: Low-Pressure Chillers",
        subtitle = "Centrifugal chillers, rupture discs & 25mm Hg vacuum",
        totalExamQuestions = 25
    ),
    UNIVERSAL(
        rawValue = "Universal",
        title = "Universal Master Simulation",
        subtitle = "Full 80-question proctored exam with 70% pass threshold",
        totalExamQuestions = 80 // 20 per section
    );

    val id: String get() = rawValue

    companion object {
        // Swift-compatibility aliases
        val type1: EPASection get() = TYPE_I
        val type2: EPASection get() = TYPE_II
        val type3: EPASection get() = TYPE_III
        val core: EPASection get() = CORE
        val universal: EPASection get() = UNIVERSAL

        /**
         * Resolves enum by raw value, name, or common alias (case-insensitive, supporting Roman and Arabic numerals).
         */
        fun fromString(value: String?): EPASection? {
            if (value.isNullOrBlank()) return null
            val clean = value.trim()
            val normalized = clean.lowercase().replace("_", "").replace(" ", "")
            return when (normalized) {
                "core" -> CORE
                "type1", "typei", "1", "i" -> TYPE_I
                "type2", "typeii", "2", "ii" -> TYPE_II
                "type3", "typeiii", "3", "iii" -> TYPE_III
                "universal" -> UNIVERSAL
                else -> entries.firstOrNull {
                    it.rawValue.equals(clean, ignoreCase = true) ||
                    it.name.equals(clean, ignoreCase = true)
                }
            }
        }
    }
}
