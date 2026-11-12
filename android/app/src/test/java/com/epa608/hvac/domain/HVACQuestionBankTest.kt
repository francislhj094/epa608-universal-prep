package com.epa608.hvac.domain

import com.epa608.hvac.data.HVACQuestionBank
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class HVACQuestionBankTest {

    @Test
    fun testQuestionCountsMatchStatutoryBank() {
        assertEquals(10, HVACQuestionBank.diagnosticQuestions.size)
        assertEquals(100, HVACQuestionBank.coreQuestions.size)
        assertEquals(100, HVACQuestionBank.type1Questions.size)
        assertEquals(100, HVACQuestionBank.type2Questions.size)
        assertEquals(100, HVACQuestionBank.type3Questions.size)
        assertEquals(400, HVACQuestionBank.studyQuestions.size)
        assertEquals(410, HVACQuestionBank.allQuestions.size)
    }

    @Test
    fun testAllQuestionsHaveValidOptionsAndExplanations() {
        for (q in HVACQuestionBank.allQuestions) {
            assertTrue("Question ID ${q.id} stem must not be blank", q.questionText.isNotBlank())
            assertTrue("Question ID ${q.id} must have >= 2 options", q.options.size >= 2)
            assertTrue(
                "Question ID ${q.id} correctIndex ${q.correctIndex} must be within options [0..${q.options.size - 1}]",
                q.correctIndex in q.options.indices
            )
            assertTrue("Question ID ${q.id} explanation must not be blank", q.explanation.isNotBlank())
            assertTrue("Question ID ${q.id} citation must not be blank", q.citation.isNotBlank())
        }
    }

    @Test
    fun testAIMActAndA2LQuestionsFlagged() {
        val diag1 = HVACQuestionBank.findQuestionById("diag-1")
        assertNotNull(diag1)
        assertTrue(diag1!!.isA2L)
        assertTrue(diag1.citation.contains("84.54") || diag1.citation.contains("CGA-164"))

        val core5 = HVACQuestionBank.findQuestionById("core-5")
        assertNotNull(core5)
        assertTrue(core5!!.isA2L)
        assertTrue(core5.explanation.contains("R-454B") || core5.explanation.contains("700"))

        val core7 = HVACQuestionBank.findQuestionById("core-7")
        assertNotNull(core7)
        assertTrue(core7!!.isA2L)
        assertTrue(core7.citation.contains("ASHRAE Standard 15"))

        val t13 = HVACQuestionBank.findQuestionById("t1-3")
        assertNotNull(t13)
        assertTrue(t13!!.isA2L)
        assertTrue(t13.citation.contains("SNAP Rule 21") || t13.explanation.contains("150 grams"))
    }

    @Test
    fun testSectionQuestionsLookup() {
        val core = HVACQuestionBank.getQuestionsForSection(EPASection.CORE)
        assertEquals(100, core.size)
        assertTrue(core.all { it.section == EPASection.CORE })

        val t1 = HVACQuestionBank.getQuestionsForSection(EPASection.TYPE_I)
        assertEquals(100, t1.size)
        assertTrue(t1.all { it.section == EPASection.TYPE_I })

        val t2 = HVACQuestionBank.getQuestionsForSection(EPASection.TYPE_II)
        assertEquals(100, t2.size)
        assertTrue(t2.all { it.section == EPASection.TYPE_II })

        val t3 = HVACQuestionBank.getQuestionsForSection(EPASection.TYPE_III)
        assertEquals(100, t3.size)
        assertTrue(t3.all { it.section == EPASection.TYPE_III })

        val universal = HVACQuestionBank.getQuestionsForSection(EPASection.UNIVERSAL)
        assertEquals(400, universal.size)
    }
}
