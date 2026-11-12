package com.epa608.hvac.domain

import java.util.Date

/**
 * Result of the 10-Question Diagnostic Assessment.
 *
 * @param scorePercentage Percentage scored (0.0 to 100.0)
 * @param correctCount Number of questions answered correctly
 * @param totalQuestions Total diagnostic questions evaluated
 * @param passed True if scorePercentage >= 70.0 (Official EPA passing threshold)
 * @param weakSections Sections where the technician scored below 70.0%
 * @param timestamp Epoch millisecond timestamp of completion
 */
data class DiagnosticResult(
    val scorePercentage: Double,
    val correctCount: Int,
    val totalQuestions: Int,
    val passed: Boolean,
    val weakSections: List<EPASection>,
    val timestamp: Long = System.currentTimeMillis()
) {
    val date: Date get() = Date(timestamp)

    companion object {
        const val PASSING_THRESHOLD_PERCENT: Double = 70.0
    }
}
