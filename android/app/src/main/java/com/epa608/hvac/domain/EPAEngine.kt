package com.epa608.hvac.domain

import android.content.Context
import android.content.SharedPreferences
import com.epa608.hvac.data.HVACQuestionBank
import com.epa608.hvac.ui.theme.IndustrialColors
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * Event emissions for UI notifications, reactive triggers, and analytics.
 */
sealed class EPAEngineEvent {
    data class AnswerRecorded(val questionId: String, val isCorrect: Boolean) : EPAEngineEvent()
    data class BookmarkToggled(val questionId: String, val isBookmarked: Boolean) : EPAEngineEvent()
    data class DiagnosticCompleted(val result: DiagnosticResult) : EPAEngineEvent()
    data class ExamAttemptRecorded(val attempt: ExamAttempt) : EPAEngineEvent()
    data object ProgressReset : EPAEngineEvent()
}

/**
 * EPA 608 Core Calculation & Assessment Reactive Engine.
 *
 * Exposes Kotlin [StateFlow] and [SharedFlow] pipelines for reactive UI observation
 * across Jetpack Compose and ViewModels, while persisting progress to Android [SharedPreferences].
 */
open class EPAEngine(
    private val sharedPreferences: SharedPreferences? = null
) {
    // --- Reactive Mutable State ---
    private val _completedQuestionIds = MutableStateFlow<Set<String>>(emptySet())
    val completedQuestionIds: StateFlow<Set<String>> = _completedQuestionIds.asStateFlow()

    private val _correctQuestionIds = MutableStateFlow<Set<String>>(emptySet())
    val correctQuestionIds: StateFlow<Set<String>> = _correctQuestionIds.asStateFlow()

    private val _bookmarkedQuestionIds = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedQuestionIds: StateFlow<Set<String>> = _bookmarkedQuestionIds.asStateFlow()

    private val _recentExamAttempts = MutableStateFlow<List<ExamAttempt>>(emptyList())
    val recentExamAttempts: StateFlow<List<ExamAttempt>> = _recentExamAttempts.asStateFlow()

    private val _lastDiagnosticResult = MutableStateFlow<DiagnosticResult?>(null)
    val lastDiagnosticResult: StateFlow<DiagnosticResult?> = _lastDiagnosticResult.asStateFlow()

    private val _hasCompletedDiagnostic = MutableStateFlow(false)
    val hasCompletedDiagnostic: StateFlow<Boolean> = _hasCompletedDiagnostic.asStateFlow()

    private val _targetExamDateEpochMs = MutableStateFlow(defaultTargetDate())
    val targetExamDateEpochMs: StateFlow<Long> = _targetExamDateEpochMs.asStateFlow()

    private val _overallReadinessScore = MutableStateFlow(0.0)
    val overallReadinessScore: StateFlow<Double> = _overallReadinessScore.asStateFlow()

    private val _isRooftopMode = MutableStateFlow(false)
    val isRooftopMode: StateFlow<Boolean> = _isRooftopMode.asStateFlow()
    val currentIsRooftopMode: Boolean get() = _isRooftopMode.value

    // --- Official Universal Readiness Certificate State ---
    private val _technicianName = MutableStateFlow("Alex Miller")
    val technicianName: StateFlow<String> = _technicianName.asStateFlow()

    private val _hasUnlockedCertificate = MutableStateFlow(false)
    val hasUnlockedCertificate: StateFlow<Boolean> = _hasUnlockedCertificate.asStateFlow()

    private val _certificateId = MutableStateFlow("EPA-608-UNIV-9482-B2A")
    val certificateId: StateFlow<String> = _certificateId.asStateFlow()

    // --- Reactive Shared Event Stream ---
    private val _events = MutableSharedFlow<EPAEngineEvent>(extraBufferCapacity = 32)
    val events: SharedFlow<EPAEngineEvent> = _events.asSharedFlow()

    init {
        loadPersistedState()
        updateOverallReadiness()
    }

    // MARK: - Synchronous Snapshots
    val currentCompletedCount: Int get() = _completedQuestionIds.value.size
    val currentCorrectCount: Int get() = _correctQuestionIds.value.size
    val currentBookmarkedCount: Int get() = _bookmarkedQuestionIds.value.size
    val currentOverallReadiness: Double get() = _overallReadinessScore.value

    fun isBookmarked(questionId: String): Boolean = _bookmarkedQuestionIds.value.contains(questionId)
    fun isCompleted(questionId: String): Boolean = _completedQuestionIds.value.contains(questionId)
    fun isCorrect(questionId: String): Boolean = _correctQuestionIds.value.contains(questionId)

    // MARK: - Diagnostic Test Evaluation
    /**
     * Evaluates the 10-Question Diagnostic Assessment under 40 CFR Part 82 and AIM Act (Part 84).
     * Passing threshold is strictly 70.0%.
     *
     * @param answers Map of Question ID to selected option index
     */
    fun evaluateDiagnostic(answers: Map<String, Int>): DiagnosticResult {
        var correctCount = 0
        val sectionScores = mutableMapOf<EPASection, Pair<Int, Int>>() // Pair(correct, total)

        for (q in HVACQuestionBank.diagnosticQuestions) {
            val selected = answers[q.id]
            val isCorrect = selected != null && q.isCorrect(selected)
            if (isCorrect) {
                correctCount++
            }

            val current = sectionScores[q.section] ?: Pair(0, 0)
            val updated = Pair(
                if (isCorrect) current.first + 1 else current.first,
                current.second + 1
            )
            sectionScores[q.section] = updated
        }

        val total = HVACQuestionBank.diagnosticQuestions.size
        val percentage = if (total > 0) (correctCount.toDouble() / total.toDouble()) * 100.0 else 0.0
        val passed = percentage >= DiagnosticResult.PASSING_THRESHOLD_PERCENT

        val weakSections = mutableListOf<EPASection>()
        for ((sec, stat) in sectionScores) {
            val secPct = if (stat.second > 0) (stat.first.toDouble() / stat.second.toDouble()) * 100.0 else 0.0
            if (secPct < DiagnosticResult.PASSING_THRESHOLD_PERCENT) {
                weakSections.add(sec)
            }
        }

        val result = DiagnosticResult(
            scorePercentage = percentage,
            correctCount = correctCount,
            totalQuestions = total,
            passed = passed,
            weakSections = weakSections,
            timestamp = System.currentTimeMillis()
        )

        _lastDiagnosticResult.value = result
        _hasCompletedDiagnostic.value = true
        savePersistedState()

        _events.tryEmit(EPAEngineEvent.DiagnosticCompleted(result))
        return result
    }

    /**
     * Explicitly marks the diagnostic onboarding as completed (e.g. when dismissed or skipped).
     */
    fun markDiagnosticCompleted() {
        _hasCompletedDiagnostic.value = true
        savePersistedState()
    }

    // MARK: - Section & Overall Readiness (Coverage 40% + Accuracy 60%)
    /**
     * Calculates readiness score for a specific [EPASection].
     * Weighted: 40% Question Pool Coverage + 60% Answer Accuracy.
     */
    fun readiness(section: EPASection): Double {
        if (section == EPASection.UNIVERSAL) {
            return _overallReadinessScore.value
        }

        val sectionQuestions = HVACQuestionBank.getQuestionsForSection(section)
        val total = sectionQuestions.size
        if (total == 0) return 0.0

        val completed = _completedQuestionIds.value
        val correct = _correctQuestionIds.value

        val answeredQuestions = sectionQuestions.filter { completed.contains(it.id) }
        if (answeredQuestions.isEmpty()) return 0.0

        val correctCount = answeredQuestions.count { correct.contains(it.id) }
        val coverage = (answeredQuestions.size.toDouble() / total.toDouble()) * 40.0
        val accuracy = (correctCount.toDouble() / answeredQuestions.size.toDouble()) * 60.0

        return minOf(100.0, coverage + accuracy)
    }

    private fun updateOverallReadiness() {
        val allQuestions = HVACQuestionBank.studyQuestions
        val totalQuestions = allQuestions.size
        if (totalQuestions == 0) {
            _overallReadinessScore.value = 0.0
            return
        }

        val completed = _completedQuestionIds.value
        val correct = _correctQuestionIds.value

        val answeredCount = allQuestions.count { completed.contains(it.id) }
        val correctCount = allQuestions.count { correct.contains(it.id) }

        val coverageScore = (answeredCount.toDouble() / totalQuestions.toDouble()) * 40.0
        val accuracyScore = if (answeredCount > 0) {
            (correctCount.toDouble() / answeredCount.toDouble()) * 60.0
        } else {
            0.0
        }

        _overallReadinessScore.value = minOf(100.0, coverageScore + accuracyScore)
    }

    // MARK: - Study Progress Mutation
    /**
     * Records a question answer attempt, updating completed & correct sets and recalculating readiness.
     */
    fun recordAnswer(questionId: String, isCorrect: Boolean) {
        val newCompleted = _completedQuestionIds.value.toMutableSet().apply { add(questionId) }
        val newCorrect = _correctQuestionIds.value.toMutableSet().apply {
            if (isCorrect) add(questionId) else remove(questionId)
        }

        _completedQuestionIds.value = newCompleted
        _correctQuestionIds.value = newCorrect

        updateOverallReadiness()
        savePersistedState()

        _events.tryEmit(EPAEngineEvent.AnswerRecorded(questionId, isCorrect))
    }

    /**
     * Toggles bookmark state for a question.
     * @return New bookmark state (true = bookmarked, false = removed)
     */
    fun toggleBookmark(questionId: String): Boolean {
        val isNowBookmarked: Boolean
        val newBookmarks = _bookmarkedQuestionIds.value.toMutableSet().apply {
            if (contains(questionId)) {
                remove(questionId)
                isNowBookmarked = false
            } else {
                add(questionId)
                isNowBookmarked = true
            }
        }
        _bookmarkedQuestionIds.value = newBookmarks
        savePersistedState()

        _events.tryEmit(EPAEngineEvent.BookmarkToggled(questionId, isNowBookmarked))
        return isNowBookmarked
    }

    /**
     * Explicitly sets bookmark state.
     */
    fun setBookmark(questionId: String, bookmarked: Boolean) {
        val current = _bookmarkedQuestionIds.value
        if (current.contains(questionId) == bookmarked) return

        val updated = current.toMutableSet().apply {
            if (bookmarked) add(questionId) else remove(questionId)
        }
        _bookmarkedQuestionIds.value = updated
        savePersistedState()

        _events.tryEmit(EPAEngineEvent.BookmarkToggled(questionId, bookmarked))
    }

    /**
     * Records a completed simulated exam attempt.
     */
    fun recordAttempt(attempt: ExamAttempt) {
        val updated = _recentExamAttempts.value.toMutableList().apply { add(attempt) }
        _recentExamAttempts.value = updated
        if (attempt.passed) {
            _hasUnlockedCertificate.value = true
        }
        savePersistedState()

        _events.tryEmit(EPAEngineEvent.ExamAttemptRecorded(attempt))
    }

    /**
     * Sets target exam date (epoch milliseconds).
     */
    fun setTargetExamDate(dateEpochMs: Long) {
        _targetExamDateEpochMs.value = dateEpochMs
        savePersistedState()
    }

    /**
     * Resets all study progress, attempts, and bookmarks to day-0 clean slate.
     */
    fun resetProgress() {
        _completedQuestionIds.value = emptySet()
        _correctQuestionIds.value = emptySet()
        _bookmarkedQuestionIds.value = emptySet()
        _recentExamAttempts.value = emptyList()
        _lastDiagnosticResult.value = null
        _hasCompletedDiagnostic.value = false
        _targetExamDateEpochMs.value = defaultTargetDate()
        _overallReadinessScore.value = 0.0

        savePersistedState()
        _events.tryEmit(EPAEngineEvent.ProgressReset)
    }

    // MARK: - Certificate Management
    fun setTechnicianName(name: String) {
        val trimmed = name.trim().ifEmpty { "Alex Miller" }
        _technicianName.value = trimmed
        sharedPreferences?.edit()?.putString(KEY_TECHNICIAN_NAME, trimmed)?.apply()
    }

    fun unlockCertificate(unlocked: Boolean = true) {
        _hasUnlockedCertificate.value = unlocked
        sharedPreferences?.edit()?.putBoolean(KEY_CERTIFICATE_UNLOCKED, unlocked)?.apply()
    }

    fun getCertificateData(): UniversalCertificateData {
        val passedAttempt = _recentExamAttempts.value.firstOrNull { it.passed && it.section == EPASection.UNIVERSAL }
        val overall = passedAttempt?.score ?: maxOf(92.5, _overallReadinessScore.value)

        return UniversalCertificateData(
            certificateId = _certificateId.value,
            technicianName = _technicianName.value,
            issueDateFormatted = java.text.SimpleDateFormat("MMMM d, yyyy", java.util.Locale.US).format(java.util.Date()),
            overallScore = overall,
            coreScore = 95.0,
            type1Score = 90.0,
            type2Score = 95.0,
            type3Score = 90.0
        )
    }

    // MARK: - Android SharedPreferences Persistence
    fun savePersistedState() {
        val prefs = sharedPreferences ?: return
        try {
            prefs.edit().apply {
                putStringSet(KEY_COMPLETED_IDS, _completedQuestionIds.value.toSet())
                putStringSet(KEY_CORRECT_IDS, _correctQuestionIds.value.toSet())
                putStringSet(KEY_BOOKMARKED_IDS, _bookmarkedQuestionIds.value.toSet())
                putBoolean(KEY_HAS_DIAGNOSTIC, _hasCompletedDiagnostic.value)
                putLong(KEY_TARGET_EXAM_DATE, _targetExamDateEpochMs.value)

                val diagnostic = _lastDiagnosticResult.value
                if (diagnostic != null) {
                    putString(KEY_DIAGNOSTIC_RESULT, serializeDiagnostic(diagnostic))
                } else {
                    remove(KEY_DIAGNOSTIC_RESULT)
                }

                val attempts = _recentExamAttempts.value
                if (attempts.isNotEmpty()) {
                    putString(KEY_EXAM_ATTEMPTS, serializeAttempts(attempts))
                } else {
                    remove(KEY_EXAM_ATTEMPTS)
                }

                putString(KEY_TECHNICIAN_NAME, _technicianName.value)
                putBoolean(KEY_CERTIFICATE_UNLOCKED, _hasUnlockedCertificate.value)
                putString(KEY_CERTIFICATE_ID, _certificateId.value)
                apply()
            }
        } catch (ignored: Exception) {
            // Failsafe against background disk serialization anomalies
        }
    }

    // MARK: - Rooftop Sunlight Glare Mode Control
    fun toggleRooftopMode(): Boolean {
        val next = !_isRooftopMode.value
        _isRooftopMode.value = next
        IndustrialColors.isRooftopMode = next
        sharedPreferences?.edit()?.putBoolean(KEY_ROOFTOP_MODE, next)?.apply()
        return next
    }

    fun setRooftopMode(enabled: Boolean) {
        _isRooftopMode.value = enabled
        IndustrialColors.isRooftopMode = enabled
        sharedPreferences?.edit()?.putBoolean(KEY_ROOFTOP_MODE, enabled)?.apply()
    }

    fun loadPersistedState() {
        val prefs = sharedPreferences ?: return
        try {
            val rooftop = prefs.getBoolean(KEY_ROOFTOP_MODE, false)
            _isRooftopMode.value = rooftop
            IndustrialColors.isRooftopMode = rooftop

            val completed = prefs.getStringSet(KEY_COMPLETED_IDS, null)
            if (completed != null) {
                _completedQuestionIds.value = completed.toSet()
            }

            val correct = prefs.getStringSet(KEY_CORRECT_IDS, null)
            if (correct != null) {
                _correctQuestionIds.value = correct.toSet()
            }

            val bookmarked = prefs.getStringSet(KEY_BOOKMARKED_IDS, null)
            if (bookmarked != null) {
                _bookmarkedQuestionIds.value = bookmarked.toSet()
            }

            _hasCompletedDiagnostic.value = prefs.getBoolean(KEY_HAS_DIAGNOSTIC, false)

            val targetDate = prefs.getLong(KEY_TARGET_EXAM_DATE, 0L)
            if (targetDate > 0L) {
                _targetExamDateEpochMs.value = targetDate
            }

            val diagJson = prefs.getString(KEY_DIAGNOSTIC_RESULT, null)
            if (!diagJson.isNullOrBlank()) {
                _lastDiagnosticResult.value = deserializeDiagnostic(diagJson)
            }

            val attemptsJson = prefs.getString(KEY_EXAM_ATTEMPTS, null)
            if (!attemptsJson.isNullOrBlank()) {
                _recentExamAttempts.value = deserializeAttempts(attemptsJson)
            }

            _technicianName.value = prefs.getString(KEY_TECHNICIAN_NAME, "Alex Miller") ?: "Alex Miller"
            _hasUnlockedCertificate.value = prefs.getBoolean(KEY_CERTIFICATE_UNLOCKED, false)
            _certificateId.value = prefs.getString(KEY_CERTIFICATE_ID, "EPA-608-UNIV-9482-B2A") ?: "EPA-608-UNIV-9482-B2A"
        } catch (ignored: Exception) {
            // Fallback to default state on parse error
        }
    }

    // MARK: - JSON Serialization Helpers
    private fun serializeDiagnostic(result: DiagnosticResult): String {
        val obj = JSONObject()
        obj.put("scorePercentage", result.scorePercentage)
        obj.put("correctCount", result.correctCount)
        obj.put("totalQuestions", result.totalQuestions)
        obj.put("passed", result.passed)
        val arr = JSONArray()
        result.weakSections.forEach { arr.put(it.rawValue) }
        obj.put("weakSections", arr)
        obj.put("timestamp", result.timestamp)
        return obj.toString()
    }

    private fun deserializeDiagnostic(jsonString: String): DiagnosticResult? {
        return try {
            val obj = JSONObject(jsonString)
            val score = obj.getDouble("scorePercentage")
            val correct = obj.getInt("correctCount")
            val total = obj.getInt("totalQuestions")
            val passed = obj.getBoolean("passed")
            val arr = obj.optJSONArray("weakSections")
            val weak = mutableListOf<EPASection>()
            if (arr != null) {
                for (i in 0 until arr.length()) {
                    EPASection.fromString(arr.getString(i))?.let { weak.add(it) }
                }
            }
            val timestamp = obj.optLong("timestamp", System.currentTimeMillis())
            DiagnosticResult(score, correct, total, passed, weak, timestamp)
        } catch (e: Exception) {
            null
        }
    }

    private fun serializeAttempts(attempts: List<ExamAttempt>): String {
        val arr = JSONArray()
        for (att in attempts) {
            val obj = JSONObject()
            obj.put("id", att.id)
            obj.put("timestamp", att.timestamp)
            obj.put("section", att.section.rawValue)
            obj.put("score", att.score)
            obj.put("passed", att.passed)
            obj.put("timeElapsedSeconds", att.timeElapsedSeconds)
            arr.put(obj)
        }
        return arr.toString()
    }

    private fun deserializeAttempts(jsonString: String): List<ExamAttempt> {
        return try {
            val arr = JSONArray(jsonString)
            val list = mutableListOf<ExamAttempt>()
            for (i in 0 until arr.length()) {
                val obj = arr.getJSONObject(i)
                val id = obj.getString("id")
                val timestamp = obj.getLong("timestamp")
                val section = EPASection.fromString(obj.getString("section")) ?: EPASection.UNIVERSAL
                val score = obj.getDouble("score")
                val passed = obj.getBoolean("passed")
                val time = obj.getInt("timeElapsedSeconds")
                list.add(ExamAttempt(id, timestamp, section, score, passed, time))
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }

    companion object {
        const val PREFS_NAME = "epa608_study_engine_prefs"

        const val KEY_COMPLETED_IDS = "epa_completed_ids"
        const val KEY_CORRECT_IDS = "epa_correct_ids"
        const val KEY_BOOKMARKED_IDS = "epa_bookmarked_ids"
        const val KEY_HAS_DIAGNOSTIC = "epa_has_diagnostic"
        const val KEY_TARGET_EXAM_DATE = "epa_target_exam_date"
        const val KEY_DIAGNOSTIC_RESULT = "epa_diagnostic_result"
        const val KEY_EXAM_ATTEMPTS = "epa_exam_attempts"
        const val KEY_ROOFTOP_MODE = "epa_rooftop_sunlight_mode"
        const val KEY_TECHNICIAN_NAME = "epa_technician_name"
        const val KEY_CERTIFICATE_UNLOCKED = "epa_cert_unlocked"
        const val KEY_CERTIFICATE_ID = "epa_cert_id"

        @Volatile
        private var INSTANCE: EPAEngine? = null

        /**
         * Singleton accessor for Application / Activity / ViewModel usage.
         */
        fun getInstance(context: Context? = null): EPAEngine {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: run {
                    val prefs = context?.applicationContext?.getSharedPreferences(
                        PREFS_NAME,
                        Context.MODE_PRIVATE
                    )
                    EPAEngine(prefs).also { INSTANCE = it }
                }
            }
        }

        /**
         * Explicit initialization during Application onCreate().
         */
        fun initialize(context: Context): EPAEngine {
            return synchronized(this) {
                val prefs = context.applicationContext.getSharedPreferences(
                    PREFS_NAME,
                    Context.MODE_PRIVATE
                )
                EPAEngine(prefs).also { INSTANCE = it }
            }
        }

        /**
         * For unit testing injection or test harness mocking.
         */
        fun setTestInstance(engine: EPAEngine) {
            INSTANCE = engine
        }

        private fun defaultTargetDate(): Long {
            return System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30)
        }
    }
}
