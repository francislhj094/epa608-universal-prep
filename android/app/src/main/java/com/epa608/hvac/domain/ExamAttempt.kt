package com.epa608.hvac.domain

import java.util.Date
import java.util.UUID

/**
 * Proctored Simulation or Section Exam Attempt Record.
 *
 * @param id Unique attempt UUID string
 * @param timestamp Epoch millisecond timestamp of attempt
 * @param section Exam section evaluated ([EPASection])
 * @param score Overall percentage score achieved (0.0 to 100.0)
 * @param passed True if attempt met statutory pass criteria (>=70% modular threshold)
 * @param timeElapsedSeconds Duration taken to complete in seconds
 */
data class ExamAttempt(
    val id: String = UUID.randomUUID().toString(),
    val timestamp: Long = System.currentTimeMillis(),
    val section: EPASection,
    val score: Double,
    val passed: Boolean,
    val timeElapsedSeconds: Int
) {
    val date: Date get() = Date(timestamp)
}
