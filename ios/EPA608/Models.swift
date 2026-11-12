import Foundation

// MARK: - EPA 608 Certification Sections
public enum EPASection: String, CaseIterable, Identifiable, Codable {
    case core = "Core"
    case type1 = "Type I"
    case type2 = "Type II"
    case type3 = "Type III"
    case universal = "Universal"
    
    public var id: String { rawValue }
    
    public var title: String {
        switch self {
        case .core:
            return "Core Section"
        case .type1:
            return "Type I: Small Appliances"
        case .type2:
            return "Type II: High-Pressure Systems"
        case .type3:
            return "Type III: Low-Pressure Chillers"
        case .universal:
            return "Universal Master Simulation"
        }
    }
    
    public var subtitle: String {
        switch self {
        case .core:
            return "Clean Air Act, ODP, GWP, Montreal Protocol & A2L Safety"
        case .type1:
            return "<5 lbs factory-sealed appliances, piercing valves & recovery"
        case .type2:
            return "Commercial AC, leak repair triggers & evacuation levels"
        case .type3:
            return "Centrifugal chillers, rupture discs & 25mm Hg vacuum"
        case .universal:
            return "Full 80-question proctored exam with 70% pass threshold"
        }
    }
    
    public var totalExamQuestions: Int {
        switch self {
        case .core, .type1, .type2, .type3:
            return 25
        case .universal:
            return 80 // 20 per section
        }
    }
}

// MARK: - Question Model
public struct Question: Identifiable, Codable {
    public let id: String
    public let section: EPASection
    public let questionText: String
    public let options: [String]
    public let correctIndex: Int
    public let explanation: String
    public let citation: String
    public let isA2L: Bool // Mandated 2026/2027 AIM Act question flag
    
    public init(
        id: String,
        section: EPASection,
        questionText: String,
        options: [String],
        correctIndex: Int,
        explanation: String,
        citation: String,
        isA2L: Bool = false
    ) {
        self.id = id
        self.section = section
        self.questionText = questionText
        self.options = options
        self.correctIndex = correctIndex
        self.explanation = explanation
        self.citation = citation
        self.isA2L = isA2L
    }
}

// MARK: - Diagnostic Result
public struct DiagnosticResult: Codable {
    public let scorePercentage: Double
    public let correctCount: Int
    public let totalQuestions: Int
    public let passed: Bool
    public let weakSections: [EPASection]
    public let timestamp: Date
    
    public init(
        scorePercentage: Double,
        correctCount: Int,
        totalQuestions: Int,
        passed: Bool,
        weakSections: [EPASection],
        timestamp: Date = Date()
    ) {
        self.scorePercentage = scorePercentage
        self.correctCount = correctCount
        self.totalQuestions = totalQuestions
        self.passed = passed
        self.weakSections = weakSections
        self.timestamp = timestamp
    }
}

// MARK: - Exam Attempt
public struct ExamAttempt: Identifiable, Codable {
    public let id: UUID
    public let date: Date
    public let section: EPASection
    public let score: Double
    public let passed: Bool
    public let timeElapsedSeconds: Int
    
    public init(
        id: UUID = UUID(),
        date: Date = Date(),
        section: EPASection,
        score: Double,
        passed: Bool,
        timeElapsedSeconds: Int
    ) {
        self.id = id
        self.date = date
        self.section = section
        self.score = score
        self.passed = passed
        self.timeElapsedSeconds = timeElapsedSeconds
    }
}

// MARK: - Official Universal Certificate Data
public struct UniversalCertificateData: Identifiable, Codable {
    public var id: String { certificateId }
    public var certificateId: String
    public var technicianName: String
    public var issueDateFormatted: String
    public var classification: String
    public var overallScore: Double
    public var coreScore: Double
    public var type1Score: Double
    public var type2Score: Double
    public var type3Score: Double
    public var verificationUrl: String
    public var regulatoryStandard: String
    
    public init(
        certificateId: String = "EPA-608-UNIV-9482-B2A",
        technicianName: String = "Alex Miller",
        issueDateFormatted: String = {
            let formatter = DateFormatter()
            formatter.dateFormat = "MMMM d, yyyy"
            return formatter.string(from: Date())
        }(),
        classification: String = "UNIVERSAL TECHNICIAN (CORE + TYPE I + TYPE II + TYPE III)",
        overallScore: Double = 92.5,
        coreScore: Double = 95.0,
        type1Score: Double = 90.0,
        type2Score: Double = 95.0,
        type3Score: Double = 90.0,
        verificationUrl: String = "https://epa608prep.com/verify/EPA-608-UNIV-9482-B2A",
        regulatoryStandard: String = "United States Clean Air Act • 40 CFR Part 82 Subpart F (§ 82.161)"
    ) {
        self.certificateId = certificateId
        self.technicianName = technicianName
        self.issueDateFormatted = issueDateFormatted
        self.classification = classification
        self.overallScore = overallScore
        self.coreScore = coreScore
        self.type1Score = type1Score
        self.type2Score = type2Score
        self.type3Score = type3Score
        self.verificationUrl = verificationUrl
        self.regulatoryStandard = regulatoryStandard
    }
    
    public var shareableText: String {
        """
        🎖️ OFFICIAL EPA SECTION 608 UNIVERSAL PASS VERIFICATION
        Technician: \(technicianName)
        Classification: \(classification)
        Exam Score: \(Int(overallScore))% (Statutory 70% Cutoff Exceeded)
        • Core Section: \(Int(coreScore))% (PASSED)
        • Type I (Small Appliances): \(Int(type1Score))% (PASSED)
        • Type II (High Pressure & A2L): \(Int(type2Score))% (PASSED)
        • Type III (Low Pressure Chillers): \(Int(type3Score))% (PASSED)
        Credential ID: \(certificateId)
        Federal Standard: \(regulatoryStandard)
        Verify Authenticity: \(verificationUrl)
        """
    }
}
