package com.epa608.hvac.domain

/**
 * EPA 608 Practice & Certification Question.
 *
 * @param id Unique identifier (e.g. "core-1", "diag-1", "t2-3")
 * @param section Target certification discipline ([EPASection])
 * @param questionText Full stem of the test question
 * @param options Answer choices (typically 4 options)
 * @param correctIndex Zero-based index of the legally correct answer
 * @param explanation Technical rationale referencing thermodynamics or statutory regulations
 * @param citation Exact federal law, code of federal regulations (40 CFR), or ASHRAE standard citation
 * @param isA2L Mandated 2026/2027 AIM Act question flag indicating A2L refrigerant safety content
 */
data class Question(
    val id: String,
    val section: EPASection,
    val questionText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val citation: String,
    val isA2L: Boolean = false
) {
    init {
        require(options.isNotEmpty()) { "Options list cannot be empty for question $id" }
        require(correctIndex in options.indices) {
            "correctIndex ($correctIndex) is out of bounds [0..${options.size - 1}] for question $id"
        }
    }

    val correctAnswerText: String
        get() = options.getOrElse(correctIndex) { "" }

    fun isCorrect(selectedIndex: Int?): Boolean {
        return selectedIndex == correctIndex
    }
}
