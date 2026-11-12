import SwiftUI
import StoreKit

public struct ExamSimulatorView: View {
    @Environment(\.requestReview) private var requestReview
    @State private var storeKit = StoreKitManager.shared
    @State private var engine = EPAEngine.shared
    @State private var showPaywall: Bool = false
    
    @State private var isExamActive: Bool = false
    @State private var examQuestions: [Question] = []
    @State private var currentQuestionIndex: Int = 0
    @State private var userAnswers: [String: Int] = [:]
    @State private var flaggedQuestionIds: Set<String> = []
    @State private var examAttemptResult: ExamAttempt?
    
    // Background-Resilient Timestamp Anchors
    @State private var examStartTime: Date?
    @State private var examDurationSeconds: TimeInterval = 7200 // 120 minutes
    @State private var showCertificate: Bool = false
    
    public init() {}
    
    public var body: some View {
        NavigationStack {
            ZStack {
                Theme.background.ignoresSafeArea()
                
                if !isExamActive && examAttemptResult == nil {
                    startExamView
                } else if isExamActive {
                    // TimelineView updates accurately and survives app backgrounding/locking
                    TimelineView(.periodic(from: .now, by: 1.0)) { context in
                        let elapsed = context.date.timeIntervalSince(examStartTime ?? context.date)
                        let remaining = max(0, examDurationSeconds - elapsed)
                        
                        activeExamView(remainingSeconds: Int(remaining))
                            .onChange(of: remaining) { _, newRemaining in
                                if newRemaining <= 0 {
                                    finishExam(timeSpent: Int(examDurationSeconds))
                                }
                            }
                    }
                } else if let result = examAttemptResult {
                    examResultView(result: result)
                }
            }
            .sheet(isPresented: $showPaywall) {
                PaywallView()
            }
            .fullScreenCover(isPresented: $showCertificate) {
                CertificateView()
            }
        }
    }
    
    // MARK: - Start Exam View
    private var startExamView: some View {
        VStack(spacing: 24) {
            Spacer()
            
            ZStack {
                Circle()
                    .fill(Theme.cyanAccent.opacity(0.12))
                    .frame(width: 90, height: 90)
                Image(systemName: "doc.text.fill")
                    .font(.system(size: 42))
                    .foregroundColor(Theme.cyanAccent)
            }
            
            VStack(spacing: 10) {
                Text("UNIVERSAL PROCTORED SIMULATOR")
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(2)
                
                Text("Official 80-Question Exam")
                    .font(.system(size: 26, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                
                Text("Accurately simulates the proctored EPA Section 608 Universal Certification testing environment.")
                    .font(.system(size: 15))
                    .foregroundColor(Theme.textSecondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 24)
            }
            
            VStack(spacing: 12) {
                infoRow(icon: "list.number", title: "Format", value: "80 Multiple Choice (20 per Section)")
                infoRow(icon: "timer", title: "Time Allowed", value: "120 Minutes (2 Hours)")
                infoRow(icon: "checkmark.seal", title: "Passing Grade", value: "70% Minimum per Section")
                infoRow(icon: "flame.fill", title: "2027 Standards", value: "A2L Refrigerants & AIM Act")
            }
            .padding(.horizontal, 20)
            
            Spacer()
            
            Button {
                Haptic.tap()
                if storeKit.isProUser {
                    startExam()
                } else {
                    showPaywall = true
                }
            } label: {
                HStack(spacing: 8) {
                    if !storeKit.isProUser {
                        Image(systemName: "lock.fill")
                    }
                    Text(storeKit.isProUser ? "Begin Timed Simulation →" : "Unlock Universal Exam Simulator")
                }
                .font(.system(size: 18, weight: .bold))
                .foregroundColor(.black)
                .frame(maxWidth: .infinity)
                .frame(height: 56)
                .background(Theme.cyanAccent)
                .cornerRadius(14)
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 32)
        }
    }
    
    private func infoRow(icon: String, title: String, value: String) -> some View {
        HStack(spacing: 12) {
            Image(systemName: icon)
                .font(.system(size: 16))
                .foregroundColor(Theme.cyanAccent)
                .frame(width: 24)
            Text(title)
                .font(.system(size: 14, weight: .medium))
                .foregroundColor(Theme.textSecondary)
            Spacer()
            Text(value)
                .font(.system(size: 13, weight: .bold))
                .foregroundColor(Theme.textPrimary)
        }
        .padding(14)
        .background(Theme.cardSurface)
        .cornerRadius(12)
        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Active Exam View
    private func activeExamView(remainingSeconds: Int) -> some View {
        let question = examQuestions[currentQuestionIndex]
        let isFlagged = flaggedQuestionIds.contains(question.id)
        
        return VStack(spacing: 14) {
            // Top Bar
            HStack {
                // Timer
                HStack(spacing: 6) {
                    Image(systemName: "timer")
                        .foregroundColor(remainingSeconds < 600 ? Theme.hazardRed : Theme.safetyAmber)
                    Text(formatTime(remainingSeconds))
                        .font(.system(size: 15, weight: .bold, design: .monospaced))
                        .foregroundColor(remainingSeconds < 600 ? Theme.hazardRed : Theme.textPrimary)
                }
                
                Spacer()
                
                // Rooftop Sunlight Toggle
                Button {
                    Haptic.tap()
                    engine.toggleRooftopMode()
                } label: {
                    Image(systemName: engine.isRooftopMode ? "sun.max.fill" : "sun.max")
                        .foregroundColor(engine.isRooftopMode ? Theme.safetyAmber : Theme.textSecondary)
                        .padding(.horizontal, 10)
                        .padding(.vertical, 6)
                        .background(Theme.cardSurface)
                        .cornerRadius(8)
                }
                .minimumTouchTarget()
                
                // Flag Question
                Button {
                    Haptic.tap()
                    if isFlagged {
                        flaggedQuestionIds.remove(question.id)
                    } else {
                        flaggedQuestionIds.insert(question.id)
                    }
                } label: {
                    HStack(spacing: 4) {
                        Image(systemName: isFlagged ? "flag.fill" : "flag")
                            .foregroundColor(isFlagged ? Theme.hazardRed : Theme.textSecondary)
                        Text(isFlagged ? "Flagged" : "Flag")
                            .font(.system(size: 12, weight: .bold))
                            .foregroundColor(isFlagged ? Theme.hazardRed : Theme.textSecondary)
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 6)
                    .background(Theme.cardSurface)
                    .cornerRadius(8)
                }
                .minimumTouchTarget()
                
                // Finish Button
                Button {
                    Haptic.tap()
                    finishExam()
                } label: {
                    Text("Submit")
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(.black)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 8)
                        .background(Theme.safetyAmber)
                        .cornerRadius(8)
                }
                .minimumTouchTarget()
            }
            .padding(.horizontal, 20)
            
            // Progress Tracker
            HStack {
                Text("Q\(currentQuestionIndex + 1) of \(examQuestions.count)")
                    .font(.system(size: 14, weight: .bold, design: .monospaced))
                    .foregroundColor(Theme.textSecondary)
                Spacer()
                Text(question.section.rawValue)
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(Theme.sectionColor(for: question.section))
            }
            .padding(.horizontal, 20)
            
            ScrollView {
                VStack(alignment: .leading, spacing: 18) {
                    Text(question.questionText)
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                        .lineSpacing(4)
                    
                    VStack(spacing: 12) {
                        ForEach(0..<question.options.count, id: \.self) { idx in
                            let isSelected = userAnswers[question.id] == idx
                            Button {
                                Haptic.selection()
                                userAnswers[question.id] = idx
                            } label: {
                                HStack(alignment: .top, spacing: 12) {
                                    Text(["A", "B", "C", "D"][idx])
                                        .font(.system(size: 14, weight: .bold, design: .monospaced))
                                        .foregroundColor(isSelected ? .black : Theme.textSecondary)
                                        .frame(width: 26, height: 26)
                                        .background(isSelected ? Theme.cyanAccent : Theme.elevatedSurface)
                                        .cornerRadius(6)
                                    
                                    Text(question.options[idx])
                                        .font(.system(size: 15, weight: .medium))
                                        .foregroundColor(isSelected ? Theme.textPrimary : Theme.textSecondary)
                                        .multilineTextAlignment(.leading)
                                    Spacer()
                                }
                                .padding(14)
                                .background(isSelected ? Theme.elevatedSurface : Theme.cardSurface)
                                .cornerRadius(12)
                                .overlay(
                                    RoundedRectangle(cornerRadius: 12)
                                        .stroke(isSelected ? Theme.cyanAccent : Theme.cardBorder, lineWidth: 1.5)
                                )
                            }
                        }
                    }
                }
                .padding(.horizontal, 20)
            }
            
            // Navigation Bar
            HStack(spacing: 12) {
                Button {
                    Haptic.tap()
                    if currentQuestionIndex > 0 { currentQuestionIndex -= 1 }
                } label: {
                    Text("← Previous")
                        .font(.system(size: 15, weight: .bold))
                        .foregroundColor(currentQuestionIndex > 0 ? Theme.textPrimary : Theme.textMuted)
                        .frame(maxWidth: .infinity)
                        .frame(height: 50)
                        .background(Theme.cardSurface)
                        .cornerRadius(12)
                }
                .disabled(currentQuestionIndex == 0)
                
                Button {
                    Haptic.tap()
                    if currentQuestionIndex + 1 < examQuestions.count {
                        currentQuestionIndex += 1
                    } else {
                        finishExam()
                    }
                } label: {
                    Text(currentQuestionIndex + 1 < examQuestions.count ? "Next →" : "Finish")
                        .font(.system(size: 15, weight: .bold))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(height: 50)
                        .background(Theme.cyanAccent)
                        .cornerRadius(12)
                }
            }
            .padding(.horizontal, 20)
            .padding(.bottom, 20)
        }
    }
    
    // MARK: - Modular Section Scoring Exam Result View
    private var examResultView(result: ExamAttempt) -> some View {
        VStack(spacing: 24) {
            Spacer()
            
            ZStack {
                Circle()
                    .fill(result.passed ? Theme.passGreen.opacity(0.15) : Theme.hazardRed.opacity(0.15))
                    .frame(width: 100, height: 100)
                Image(systemName: result.passed ? "checkmark.seal.fill" : "xmark.seal.fill")
                    .font(.system(size: 48))
                    .foregroundColor(result.passed ? Theme.passGreen : Theme.hazardRed)
            }
            
            VStack(spacing: 8) {
                Text(result.passed ? "EPA Universal Certified" : "Examination Not Cleared")
                    .font(.system(size: 24, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                
                Text(result.passed ?
                     "Congratulations! You met the statutory 70% passing threshold across Core, Type I, Type II, and Type III." :
                     "You scored \(Int(result.score))%. Official EPA Universal certification mandates scoring at least 70% in all four sections individually.")
                    .font(.system(size: 15))
                    .foregroundColor(Theme.textSecondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 24)
            }
            
            Spacer()
            
            if result.passed {
                Button {
                    Haptic.tap()
                    showCertificate = true
                } label: {
                    HStack(spacing: 8) {
                        Image(systemName: "checkmark.seal.fill")
                        Text("Claim & Share Official Certificate 🎖️")
                    }
                    .font(.system(size: 16, weight: .black))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 54)
                    .background(Theme.safetyAmber)
                    .cornerRadius(12)
                }
                .padding(.horizontal, 24)
            }
            
            Button {
                Haptic.tap()
                isExamActive = false
                examAttemptResult = nil
            } label: {
                Text("Return to Simulator")
                    .font(.system(size: 17, weight: .bold))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 54)
                    .background(Theme.cyanAccent)
                    .cornerRadius(12)
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 32)
        }
    }
    
    // MARK: - Actions
    private func startExam() {
        var pool: [Question] = []
        pool.append(contentsOf: HVACQuestionBank.coreQuestions.shuffled().prefix(20))
        pool.append(contentsOf: HVACQuestionBank.type1Questions.shuffled().prefix(20))
        pool.append(contentsOf: HVACQuestionBank.type2Questions.shuffled().prefix(20))
        pool.append(contentsOf: HVACQuestionBank.type3Questions.shuffled().prefix(20))
        examQuestions = pool.shuffled()
        currentQuestionIndex = 0
        userAnswers.removeAll()
        flaggedQuestionIds.removeAll()
        examStartTime = Date()
        isExamActive = true
    }
    
    private func finishExam(timeSpent: Int? = nil) {
        let elapsed = timeSpent ?? Int(Date().timeIntervalSince(examStartTime ?? Date()))
        
        var sectionScores: [EPASection: (correct: Int, total: Int)] = [:]
        for q in examQuestions {
            var stat = sectionScores[q.section] ?? (0, 0)
            stat.total += 1
            if userAnswers[q.id] == q.correctIndex {
                stat.correct += 1
            }
            sectionScores[q.section] = stat
        }
        
        // Statutory EPA Modular Rule: Must achieve >= 70% in EVERY section
        let corePass = (Double(sectionScores[.core]?.correct ?? 0) / Double(max(1, sectionScores[.core]?.total ?? 1))) >= 0.70
        let t1Pass   = (Double(sectionScores[.type1]?.correct ?? 0) / Double(max(1, sectionScores[.type1]?.total ?? 1))) >= 0.70
        let t2Pass   = (Double(sectionScores[.type2]?.correct ?? 0) / Double(max(1, sectionScores[.type2]?.total ?? 1))) >= 0.70
        let t3Pass   = (Double(sectionScores[.type3]?.correct ?? 0) / Double(max(1, sectionScores[.type3]?.total ?? 1))) >= 0.70
        
        let universalPassed = corePass && t1Pass && t2Pass && t3Pass
        
        let totalCorrect = sectionScores.values.reduce(0) { $0 + $1.correct }
        let scorePct = examQuestions.isEmpty ? 0.0 : (Double(totalCorrect) / Double(examQuestions.count)) * 100.0
        
        let attempt = ExamAttempt(
            section: .universal,
            score: scorePct,
            passed: universalPassed,
            timeElapsedSeconds: elapsed
        )
        
        engine.recordAttempt(attempt)
        self.examAttemptResult = attempt
        self.isExamActive = false
        
        // Review Seeding Trigger: Prompt 5-star review strictly on high-relief pass
        if scorePct >= 80.0 && universalPassed {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
                requestReview()
            }
        }
    }
    
    private func formatTime(_ seconds: Int) -> String {
        let hrs = seconds / 3600
        let mins = (seconds % 3600) / 60
        let secs = seconds % 60
        return String(format: "%02d:%02d:%02d", hrs, mins, secs)
    }
}
