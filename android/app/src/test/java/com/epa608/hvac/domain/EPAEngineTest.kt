package com.epa608.hvac.domain

import com.epa608.hvac.data.HVACQuestionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class EPAEngineTest {

    private lateinit var engine: EPAEngine

    @Before
    fun setUp() {
        // Construct headless in-memory instance without Android SharedPreferences
        engine = EPAEngine(sharedPreferences = null)
        engine.resetProgress()
    }

    @Test
    fun testDiagnosticPerfectPass() {
        // Provide correct answers for all 10 diagnostic questions
        val answers = HVACQuestionBank.diagnosticQuestions.associate { it.id to it.correctIndex }
        val result = engine.evaluateDiagnostic(answers)

        assertEquals(10, result.correctCount)
        assertEquals(10, result.totalQuestions)
        assertEquals(100.0, result.scorePercentage, 0.01)
        assertTrue(result.passed)
        assertTrue(result.weakSections.isEmpty())
        assertTrue(engine.hasCompletedDiagnostic.value)
        assertEquals(result, engine.lastDiagnosticResult.value)
    }

    @Test
    fun testMarkDiagnosticCompletedExplicitly() {
        assertFalse(engine.hasCompletedDiagnostic.value)
        engine.markDiagnosticCompleted()
        assertTrue(engine.hasCompletedDiagnostic.value)
    }

    @Test
    fun testDiagnosticFailureWithWeakSections() {
        // Fail all questions except diag-1 (Core)
        val answers = mutableMapOf<String, Int>()
        answers["diag-1"] = HVACQuestionBank.findQuestionById("diag-1")!!.correctIndex
        // For other questions, provide an intentionally wrong index
        HVACQuestionBank.diagnosticQuestions.filter { it.id != "diag-1" }.forEach { q ->
            val wrongIndex = if (q.correctIndex == 0) 1 else 0
            answers[q.id] = wrongIndex
        }

        val result = engine.evaluateDiagnostic(answers)
        assertEquals(1, result.correctCount)
        assertEquals(10, result.totalQuestions)
        assertEquals(10.0, result.scorePercentage, 0.01)
        assertFalse(result.passed)
        // Type I, Type II, Type III should be marked weak (< 70%)
        assertTrue(result.weakSections.contains(EPASection.TYPE_I))
        assertTrue(result.weakSections.contains(EPASection.TYPE_II))
        assertTrue(result.weakSections.contains(EPASection.TYPE_III))
    }

    @Test
    fun testReadinessCoverageAndAccuracyWeighting() {
        // Initial state: 0 answered -> 0.0 readiness
        assertEquals(0.0, engine.overallReadinessScore.value, 0.001)

        val allQuestions = HVACQuestionBank.studyQuestions // 23 questions
        val total = allQuestions.size

        // Answer all questions correctly:
        // Coverage = 23/23 * 40.0 = 40.0
        // Accuracy = 23/23 * 60.0 = 60.0
        // Total = 100.0
        allQuestions.forEach { q ->
            engine.recordAnswer(q.id, isCorrect = true)
        }
        assertEquals(100.0, engine.overallReadinessScore.value, 0.01)

        // Reset and test partial coverage with 100% accuracy:
        engine.resetProgress()
        assertEquals(0.0, engine.overallReadinessScore.value, 0.001)

        // Answer half (e.g. first 10 questions) correctly:
        allQuestions.take(10).forEach { q ->
            engine.recordAnswer(q.id, isCorrect = true)
        }
        val expectedCoverage = (10.0 / total.toDouble()) * 40.0
        val expectedAccuracy = 60.0 // 10/10 correct = 100% accuracy * 60.0
        val expectedScore = expectedCoverage + expectedAccuracy
        assertEquals(expectedScore, engine.overallReadinessScore.value, 0.05)
    }

    @Test
    fun testSectionReadiness() {
        val coreQuestions = HVACQuestionBank.coreQuestions // 8 questions
        coreQuestions.forEach { q ->
            engine.recordAnswer(q.id, isCorrect = true)
        }

        // Core section should now have 100% readiness
        assertEquals(100.0, engine.readiness(EPASection.CORE), 0.01)

        // Type I has not been answered yet -> 0%
        assertEquals(0.0, engine.readiness(EPASection.TYPE_I), 0.01)
    }

    @Test
    fun testBookmarkToggle() {
        val qId = "core-1"
        assertFalse(engine.isBookmarked(qId))

        val nowBookmarked = engine.toggleBookmark(qId)
        assertTrue(nowBookmarked)
        assertTrue(engine.isBookmarked(qId))
        assertTrue(engine.bookmarkedQuestionIds.value.contains(qId))

        val removed = engine.toggleBookmark(qId)
        assertFalse(removed)
        assertFalse(engine.isBookmarked(qId))
    }

    @Test
    fun testRecordExamAttempt() {
        val attempt = ExamAttempt(
            section = EPASection.UNIVERSAL,
            score = 85.0,
            passed = true,
            timeElapsedSeconds = 3600
        )
        engine.recordAttempt(attempt)

        assertEquals(1, engine.recentExamAttempts.value.size)
        assertEquals(attempt.id, engine.recentExamAttempts.value.first().id)
        assertEquals(85.0, engine.recentExamAttempts.value.first().score, 0.01)
    }

    @Test
    fun testResetProgress() {
        engine.recordAnswer("core-1", true)
        engine.toggleBookmark("core-2")
        engine.recordAttempt(ExamAttempt(section = EPASection.CORE, score = 90.0, passed = true, timeElapsedSeconds = 500))

        assertTrue(engine.completedQuestionIds.value.isNotEmpty())
        assertTrue(engine.bookmarkedQuestionIds.value.isNotEmpty())
        assertTrue(engine.recentExamAttempts.value.isNotEmpty())

        engine.resetProgress()

        assertTrue(engine.completedQuestionIds.value.isEmpty())
        assertTrue(engine.correctQuestionIds.value.isEmpty())
        assertTrue(engine.bookmarkedQuestionIds.value.isEmpty())
        assertTrue(engine.recentExamAttempts.value.isEmpty())
        assertEquals(0.0, engine.overallReadinessScore.value, 0.001)
    }
}
