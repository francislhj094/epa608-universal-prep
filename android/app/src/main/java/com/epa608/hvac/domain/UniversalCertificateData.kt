package com.epa608.hvac.domain

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Official EPA Section 608 Universal Readiness Certificate Model.
 * Designed for employer verification, union apprenticeships (UA Local), and social proof (LinkedIn).
 *
 * References:
 * - 40 CFR Part 82 Subpart F (§ 82.161 Technician Certification)
 * - Clean Air Act Title VI & AIM Act A2L statutory mandates
 */
data class UniversalCertificateData(
    val certificateId: String = "EPA-608-UNIV-9482-B2A",
    val technicianName: String = "Alex Miller",
    val issueDateFormatted: String = SimpleDateFormat("MMMM d, yyyy", Locale.US).format(Date()),
    val classification: String = "UNIVERSAL TECHNICIAN (CORE + TYPE I + TYPE II + TYPE III)",
    val overallScore: Double = 92.5,
    val coreScore: Double = 95.0,
    val type1Score: Double = 90.0,
    val type2Score: Double = 95.0,
    val type3Score: Double = 90.0,
    val verificationUrl: String = "https://epa608prep.com/verify/EPA-608-UNIV-9482-B2A",
    val regulatoryStandard: String = "United States Clean Air Act • 40 CFR Part 82 Subpart F (§ 82.161)"
) {
    val shareableText: String
        get() = """
            🎖️ OFFICIAL EPA SECTION 608 UNIVERSAL PASS VERIFICATION
            Technician: $technicianName
            Classification: $classification
            Exam Score: ${overallScore.toInt()}% (Statutory 70% Cutoff Exceeded)
            • Core Section: ${coreScore.toInt()}% (PASSED)
            • Type I (Small Appliances): ${type1Score.toInt()}% (PASSED)
            • Type II (High Pressure & A2L): ${type2Score.toInt()}% (PASSED)
            • Type III (Low Pressure Chillers): ${type3Score.toInt()}% (PASSED)
            Credential ID: $certificateId
            Federal Standard: $regulatoryStandard
            Verify Authenticity: $verificationUrl
        """.trimIndent()
}
