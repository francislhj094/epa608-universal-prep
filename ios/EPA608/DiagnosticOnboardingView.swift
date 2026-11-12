import SwiftUI

public struct DiagnosticOnboardingView: View {
    @Binding var isPresented: Bool
    @State private var currentStep: Int = 0 // 0 = Welcome, 1...10 = Questions, 11 = Result
    @State private var selectedAnswers: [String: Int] = [:]
    @State private var showPaywall: Bool = false
    @State private var diagnosticResult: DiagnosticResult?
    
    private let questions = HVACQuestionBank.diagnosticQuestions
    
    public init(isPresented: Binding<Bool>) {
        self._isPresented = isPresented
    }
    
    public var body: some View {
        ZStack {
            Theme.background.ignoresSafeArea()
            
            if currentStep == 0 {
                welcomeView
            } else if currentStep <= questions.count {
                questionView(question: questions[currentStep - 1])
            } else if let result = diagnosticResult {
                resultsView(result: result)
            }
        }
        .sheet(isPresented: $showPaywall) {
            PaywallView()
        }
    }
    
    // MARK: - Step 0: Welcome Screen
    private var welcomeView: some View {
        VStack(spacing: 24) {
            Spacer()
            
            // Icon
            ZStack {
                Circle()
                    .fill(Theme.cardSurface)
                    .frame(width: 96, height: 96)
                    .overlay(
                        Circle().stroke(Theme.cyanAccent.opacity(0.4), lineWidth: 2)
                    )
                Image(systemName: "gauge.with.needle.fill")
                    .font(.system(size: 44))
                    .foregroundColor(Theme.cyanAccent)
            }
            
            VStack(spacing: 12) {
                Text("2027 EPA 608 HVAC")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .textCase(.uppercase)
                    .tracking(2)
                
                Text("Official Diagnostic Test")
                    .font(.system(size: 28, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                    .multilineTextAlignment(.center)
                
                Text("10 verified questions testing 2026/2027 AIM Act A2L refrigerants, leak trigger thresholds, and evacuation levels.")
                    .font(.system(size: 16))
                    .foregroundColor(Theme.textSecondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 24)
            }
            
            // Metric Cards
            HStack(spacing: 16) {
                metricPill(title: "Questions", value: "10")
                metricPill(title: "Passing Score", value: "70%")
                metricPill(title: "Time Limit", value: "5 Min")
            }
            .padding(.horizontal, 20)
            
            Spacer()
            
            Button {
                Haptic.tap()
                currentStep = 1
            } label: {
                Text("Start Free Diagnostic Test")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(Theme.cyanAccent)
                    .cornerRadius(14)
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 24)
        }
    }
    
    private func metricPill(title: String, value: String) -> some View {
        VStack(spacing: 4) {
            Text(value)
                .font(.system(size: 18, weight: .black, design: .monospaced))
                .foregroundColor(Theme.cyanAccent)
            Text(title)
                .font(.system(size: 12, weight: .medium))
                .foregroundColor(Theme.textMuted)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 12)
        .background(Theme.cardSurface)
        .cornerRadius(12)
        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Question View
    private func questionView(question: Question) -> some View {
        VStack(alignment: .leading, spacing: 20) {
            // Header Progress
            HStack {
                Text("Question \(currentStep) of \(questions.count)")
                    .font(.system(size: 14, weight: .semibold, design: .monospaced))
                    .foregroundColor(Theme.textSecondary)
                Spacer()
                Text(question.section.rawValue)
                    .font(.system(size: 12, weight: .bold))
                    .padding(.horizontal, 10)
                    .padding(.vertical, 4)
                    .background(Theme.sectionColor(for: question.section).opacity(0.2))
                    .foregroundColor(Theme.sectionColor(for: question.section))
                    .cornerRadius(8)
            }
            .padding(.horizontal, 20)
            .padding(.top, 16)
            
            // Progress Bar
            GeometryReader { geo in
                ZStack(alignment: .leading) {
                    Rectangle()
                        .fill(Theme.cardBorder)
                        .frame(height: 6)
                    Rectangle()
                        .fill(Theme.cyanAccent)
                        .frame(width: geo.size.width * CGFloat(currentStep) / CGFloat(questions.count), height: 6)
                }
                .cornerRadius(3)
            }
            .frame(height: 6)
            .padding(.horizontal, 20)
            
            // Question Text
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    if question.isA2L {
                        HStack(spacing: 6) {
                            Image(systemName: "flame.fill")
                                .foregroundColor(Theme.safetyAmber)
                            Text("2026/2027 AIM ACT MANDATE (A2L)")
                                .font(.system(size: 11, weight: .bold))
                                .foregroundColor(Theme.safetyAmber)
                        }
                    }
                    
                    Text(question.questionText)
                        .font(.system(size: 19, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                        .lineSpacing(4)
                    
                    // Options
                    VStack(spacing: 12) {
                        ForEach(0..<question.options.count, id: \.self) { idx in
                            let isSelected = selectedAnswers[question.id] == idx
                            Button {
                                Haptic.selection()
                                selectedAnswers[question.id] = idx
                            } label: {
                                HStack(alignment: .top, spacing: 14) {
                                    ZStack {
                                        Circle()
                                            .stroke(isSelected ? Theme.cyanAccent : Theme.cardBorder, lineWidth: 2)
                                            .frame(width: 28, height: 28)
                                        if isSelected {
                                            Circle()
                                                .fill(Theme.cyanAccent)
                                                .frame(width: 14, height: 14)
                                        }
                                    }
                                    .padding(.top, 2)
                                    
                                    Text(question.options[idx])
                                        .font(.system(size: 15, weight: .medium))
                                        .foregroundColor(isSelected ? Theme.textPrimary : Theme.textSecondary)
                                        .multilineTextAlignment(.leading)
                                    
                                    Spacer()
                                }
                                .padding(16)
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
            
            // Next Button
            Button {
                Haptic.tap()
                if currentStep < questions.count {
                    currentStep += 1
                } else {
                    let result = EPAEngine.shared.evaluateDiagnostic(answers: selectedAnswers)
                    self.diagnosticResult = result
                    currentStep = questions.count + 1
                }
            } label: {
                Text(currentStep < questions.count ? "Next Question →" : "Calculate My Score")
                    .font(.system(size: 17, weight: .bold))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 54)
                    .background(selectedAnswers[question.id] != nil ? Theme.cyanAccent : Theme.textMuted)
                    .cornerRadius(12)
            }
            .disabled(selectedAnswers[question.id] == nil)
            .padding(.horizontal, 20)
            .padding(.bottom, 20)
        }
    }
    
    // MARK: - Results Screen (The Shock Conversion Step)
    private func resultsView(result: DiagnosticResult) -> some View {
        VStack(spacing: 24) {
            Spacer()
            
            // Result Badge
            ZStack {
                Circle()
                    .fill(result.passed ? Theme.passGreen.opacity(0.15) : Theme.hazardRed.opacity(0.15))
                    .frame(width: 110, height: 110)
                
                VStack(spacing: 2) {
                    Text("\(Int(result.scorePercentage))%")
                        .font(.system(size: 32, weight: .black, design: .monospaced))
                        .foregroundColor(result.passed ? Theme.passGreen : Theme.hazardRed)
                    Text(result.passed ? "PASSING" : "FAILED")
                        .font(.system(size: 12, weight: .black))
                        .foregroundColor(result.passed ? Theme.passGreen : Theme.hazardRed)
                }
            }
            
            VStack(spacing: 8) {
                Text(result.passed ? "Promising Baseline" : "Warning: Certification at Risk")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                
                Text(result.passed ?
                     "You cleared the 70% bar, but proctored exams contain 80 questions with higher variance." :
                     "You scored \(Int(result.scorePercentage))%. The official EPA passing score is 70%. Failing your exam wastes $150+ in testing fees and delays your commercial career.")
                    .font(.system(size: 15))
                    .foregroundColor(Theme.textSecondary)
                    .multilineTextAlignment(.center)
                    .padding(.horizontal, 28)
            }
            
            // Weak Area Highlights
            if !result.weakSections.isEmpty {
                VStack(alignment: .leading, spacing: 10) {
                    Text("CRITICAL KNOWLEDGE GAPS DETECTED:")
                        .font(.system(size: 12, weight: .bold))
                        .foregroundColor(Theme.safetyAmber)
                    
                    ForEach(result.weakSections) { sec in
                        HStack(spacing: 8) {
                            Image(systemName: "exclamationmark.triangle.fill")
                                .foregroundColor(Theme.hazardRed)
                                .font(.system(size: 14))
                            Text(sec.title)
                                .font(.system(size: 14, weight: .semibold))
                                .foregroundColor(Theme.textPrimary)
                        }
                    }
                }
                .padding(16)
                .background(Theme.cardSurface)
                .cornerRadius(12)
                .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
                .padding(.horizontal, 24)
            }
            
            Spacer()
            
            VStack(spacing: 12) {
                Button {
                    Haptic.tap()
                    showPaywall = true
                } label: {
                    HStack(spacing: 8) {
                        Image(systemName: "checkmark.seal.fill")
                        Text("Unlock 100% Pass Guarantee →")
                    }
                    .font(.system(size: 17, weight: .bold))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 56)
                    .background(Theme.cyanAccent)
                    .cornerRadius(14)
                }
                
                Button {
                    Haptic.tap()
                    isPresented = false
                } label: {
                    Text("Continue to Free Study Hub")
                        .font(.system(size: 15, weight: .medium))
                        .foregroundColor(Theme.textSecondary)
                }
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 24)
        }
    }
}
