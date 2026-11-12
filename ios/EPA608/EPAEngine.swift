import Foundation
import SwiftUI

// MARK: - EPA 608 Core Calculation & Assessment Engine
@Observable
public final class EPAEngine {
    public static let shared = EPAEngine()
    
    // Progress Tracking
    public var completedQuestionIds: Set<String> = []
    public var correctQuestionIds: Set<String> = []
    public var bookmarkedQuestionIds: Set<String> = []
    public var recentExamAttempts: [ExamAttempt] = []
    public var lastDiagnosticResult: DiagnosticResult?
    
    // User Settings
    public var hasCompletedDiagnostic: Bool = false
    public var targetExamDate: Date = Calendar.current.date(byAdding: .day, value: 30, to: Date()) ?? Date()
    public var isRooftopMode: Bool = false
    public var technicianName: String = "Alex Miller"
    public var certificateId: String = "EPA-608-UNIV-9482-B2A"
    public var hasUnlockedCertificate: Bool = false
    
    public init() {
        loadPersistedState()
    }
    
    // MARK: - Diagnostic Test Evaluation
    public func evaluateDiagnostic(answers: [String: Int]) -> DiagnosticResult {
        var correctCount = 0
        var sectionScores: [EPASection: (correct: Int, total: Int)] = [:]
        
        for q in HVACQuestionBank.diagnosticQuestions {
            let isCorrect = answers[q.id] == q.correctIndex
            if isCorrect {
                correctCount += 1
            }
            
            var current = sectionScores[q.section] ?? (correct: 0, total: 0)
            current.total += 1
            if isCorrect {
                current.correct += 1
            }
            sectionScores[q.section] = current
        }
        
        let total = HVACQuestionBank.diagnosticQuestions.count
        let percentage = total > 0 ? (Double(correctCount) / Double(total)) * 100.0 : 0.0
        let passed = percentage >= 70.0 // Official EPA passing threshold
        
        var weakSections: [EPASection] = []
        for (sec, stat) in sectionScores {
            let secPct = stat.total > 0 ? (Double(stat.correct) / Double(stat.total)) * 100.0 : 0.0
            if secPct < 70.0 {
                weakSections.append(sec)
            }
        }
        
        let result = DiagnosticResult(
            scorePercentage: percentage,
            correctCount: correctCount,
            totalQuestions: total,
            passed: passed,
            weakSections: weakSections,
            timestamp: Date()
        )
        
        self.lastDiagnosticResult = result
        self.hasCompletedDiagnostic = true
        savePersistedState()
        return result
    }
    
    // MARK: - Overall Exam Readiness Score (Coverage 40% + Accuracy 60%)
    public var overallReadinessScore: Double {
        let allQuestions = HVACQuestionBank.coreQuestions +
                           HVACQuestionBank.type1Questions +
                           HVACQuestionBank.type2Questions +
                           HVACQuestionBank.type3Questions
        let totalQuestions = allQuestions.count
        guard totalQuestions > 0 else { return 0 }
        
        let answeredCount = allQuestions.filter { completedQuestionIds.contains($0.id) }.count
        let correctCount = allQuestions.filter { correctQuestionIds.contains($0.id) }.count
        
        // 40% based on coverage, 60% based on accuracy
        let coverageScore = (Double(answeredCount) / Double(totalQuestions)) * 40.0
        let accuracyScore = answeredCount > 0 ? (Double(correctCount) / Double(answeredCount)) * 60.0 : 0.0
        
        return min(100.0, coverageScore + accuracyScore)
    }
    
    // MARK: - Section Readiness (Weighted Coverage & Accuracy)
    public func readiness(for section: EPASection) -> Double {
        let sectionQuestions: [Question]
        switch section {
        case .core: sectionQuestions = HVACQuestionBank.coreQuestions
        case .type1: sectionQuestions = HVACQuestionBank.type1Questions
        case .type2: sectionQuestions = HVACQuestionBank.type2Questions
        case .type3: sectionQuestions = HVACQuestionBank.type3Questions
        case .universal:
            return overallReadinessScore
        }
        
        let total = sectionQuestions.count
        guard total > 0 else { return 0 }
        
        let answered = sectionQuestions.filter { completedQuestionIds.contains($0.id) }
        guard !answered.isEmpty else { return 0 }
        
        let correct = answered.filter { correctQuestionIds.contains($0.id) }
        let coverage = (Double(answered.count) / Double(total)) * 40.0
        let accuracy = (Double(correct.count) / Double(answered.count)) * 60.0
        
        return min(100.0, coverage + accuracy)
    }
    
    // MARK: - Record Answer
    public func recordAnswer(questionId: String, isCorrect: Bool) {
        completedQuestionIds.insert(questionId)
        if isCorrect {
            correctQuestionIds.insert(questionId)
        } else {
            correctQuestionIds.remove(questionId)
        }
        savePersistedState()
    }
    
    // MARK: - Toggle Bookmark
    public func toggleBookmark(questionId: String) {
        if bookmarkedQuestionIds.contains(questionId) {
            bookmarkedQuestionIds.remove(questionId)
        } else {
            bookmarkedQuestionIds.insert(questionId)
        }
        savePersistedState()
    }
    
    // MARK: - Record Exam Attempt
    public func recordAttempt(_ attempt: ExamAttempt) {
        recentExamAttempts.append(attempt)
        savePersistedState()
    }
    
    // MARK: - Persistence
    public func savePersistedState() {
        UserDefaults.standard.set(Array(completedQuestionIds), forKey: "epa_completed_ids")
        UserDefaults.standard.set(Array(correctQuestionIds), forKey: "epa_correct_ids")
        UserDefaults.standard.set(Array(bookmarkedQuestionIds), forKey: "epa_bookmarked_ids")
        UserDefaults.standard.set(hasCompletedDiagnostic, forKey: "epa_has_diagnostic")
        UserDefaults.standard.set(targetExamDate.timeIntervalSince1970, forKey: "epa_target_exam_date")
        
        if let lastDiagnosticResult, let data = try? JSONEncoder().encode(lastDiagnosticResult) {
            UserDefaults.standard.set(data, forKey: "epa_diagnostic_result")
        }
        if let attemptsData = try? JSONEncoder().encode(recentExamAttempts) {
            UserDefaults.standard.set(attemptsData, forKey: "epa_exam_attempts")
        }
        UserDefaults.standard.set(isRooftopMode, forKey: "epa_rooftop_sunlight_mode")
        UserDefaults.standard.set(technicianName, forKey: "epa_technician_name")
        UserDefaults.standard.set(certificateId, forKey: "epa_cert_id")
        UserDefaults.standard.set(hasUnlockedCertificate, forKey: "epa_cert_unlocked")
    }
    
    public func loadPersistedState() {
        isRooftopMode = UserDefaults.standard.bool(forKey: "epa_rooftop_sunlight_mode")
        if let completed = UserDefaults.standard.stringArray(forKey: "epa_completed_ids") {
            completedQuestionIds = Set(completed)
        }
        if let correct = UserDefaults.standard.stringArray(forKey: "epa_correct_ids") {
            correctQuestionIds = Set(correct)
        }
        if let bookmarked = UserDefaults.standard.stringArray(forKey: "epa_bookmarked_ids") {
            bookmarkedQuestionIds = Set(bookmarked)
        }
        hasCompletedDiagnostic = UserDefaults.standard.bool(forKey: "epa_has_diagnostic")
        
        let targetInterval = UserDefaults.standard.double(forKey: "epa_target_exam_date")
        if targetInterval > 0 {
            targetExamDate = Date(timeIntervalSince1970: targetInterval)
        }
        
        if let data = UserDefaults.standard.data(forKey: "epa_diagnostic_result"),
           let res = try? JSONDecoder().decode(DiagnosticResult.self, from: data) {
            lastDiagnosticResult = res
        }
        if let attemptsData = UserDefaults.standard.data(forKey: "epa_exam_attempts"),
           let attempts = try? JSONDecoder().decode([ExamAttempt].self, from: attemptsData) {
            recentExamAttempts = attempts
        }
        
        technicianName = UserDefaults.standard.string(forKey: "epa_technician_name") ?? "Alex Miller"
        certificateId = UserDefaults.standard.string(forKey: "epa_cert_id") ?? "EPA-608-UNIV-9482-B2A"
        hasUnlockedCertificate = UserDefaults.standard.bool(forKey: "epa_cert_unlocked")
    }
    
    public func resetProgress() {
        completedQuestionIds.removeAll()
        correctQuestionIds.removeAll()
        bookmarkedQuestionIds.removeAll()
        recentExamAttempts.removeAll()
        lastDiagnosticResult = nil
        hasCompletedDiagnostic = false
        targetExamDate = Calendar.current.date(byAdding: .day, value: 30, to: Date()) ?? Date()
        savePersistedState()
    }
    
    public func toggleRooftopMode() {
        isRooftopMode.toggle()
        savePersistedState()
    }
    
    // MARK: - Certificate Management
    public func setTechnicianName(_ name: String) {
        let trimmed = name.trimmingCharacters(in: .whitespacesAndNewlines)
        self.technicianName = trimmed.isEmpty ? "Alex Miller" : trimmed
        UserDefaults.standard.set(self.technicianName, forKey: "epa_technician_name")
    }
    
    public func unlockCertificate() {
        self.hasUnlockedCertificate = true
        UserDefaults.standard.set(true, forKey: "epa_cert_unlocked")
    }
    
    public func getCertificateData() -> UniversalCertificateData {
        let passedAttempt = recentExamAttempts.first { $0.passed && $0.section == .universal }
        let score = passedAttempt?.score ?? 92.5
        
        return UniversalCertificateData(
            certificateId: certificateId,
            technicianName: technicianName,
            overallScore: score,
            coreScore: 95.0,
            type1Score: 90.0,
            type2Score: 95.0,
            type3Score: 90.0
        )
    }
}
