package com.epa608.hvac.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EPASectionTest {

    @Test
    fun testEPASectionMetadata() {
        assertEquals("Core", EPASection.CORE.rawValue)
        assertEquals("Type I", EPASection.TYPE_I.rawValue)
        assertEquals("Type II", EPASection.TYPE_II.rawValue)
        assertEquals("Type III", EPASection.TYPE_III.rawValue)
        assertEquals("Universal", EPASection.UNIVERSAL.rawValue)

        assertEquals(25, EPASection.CORE.totalExamQuestions)
        assertEquals(25, EPASection.TYPE_I.totalExamQuestions)
        assertEquals(25, EPASection.TYPE_II.totalExamQuestions)
        assertEquals(25, EPASection.TYPE_III.totalExamQuestions)
        assertEquals(80, EPASection.UNIVERSAL.totalExamQuestions)

        assertTrue(EPASection.CORE.title.contains("Core"))
        assertTrue(EPASection.TYPE_I.title.contains("Small Appliances"))
        assertTrue(EPASection.TYPE_II.title.contains("High-Pressure"))
        assertTrue(EPASection.TYPE_III.title.contains("Low-Pressure Chillers"))
        assertTrue(EPASection.UNIVERSAL.title.contains("Universal"))
    }

    @Test
    fun testFromStringResolution() {
        assertEquals(EPASection.CORE, EPASection.fromString("Core"))
        assertEquals(EPASection.TYPE_I, EPASection.fromString("Type I"))
        assertEquals(EPASection.TYPE_I, EPASection.fromString("type1"))
        assertEquals(EPASection.TYPE_I, EPASection.fromString("TYPE_I"))
        assertEquals(EPASection.TYPE_II, EPASection.fromString("Type II"))
        assertEquals(EPASection.TYPE_II, EPASection.fromString("type2"))
        assertEquals(EPASection.TYPE_III, EPASection.fromString("Type III"))
        assertEquals(EPASection.TYPE_III, EPASection.fromString("type3"))
        assertEquals(EPASection.UNIVERSAL, EPASection.fromString("Universal"))
    }
}
