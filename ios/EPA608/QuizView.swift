import SwiftUI
import StoreKit

public struct QuizView: View {
    @Environment(\.dismiss) private var dismiss
    @Environment(\.requestReview) private var requestReview
    public let section: EPASection
    
    @State private var engine = EPAEngine.shared
    @State private var storeKit = StoreKitManager.shared
    @State private var showPaywall: Bool = false
    
    @State private var questions: [Question] = []
    @State private var currentIndex: Int = 0
    @State private var selectedOptionIndex: Int?
    @State private var hasAnswered: Bool = false
    @State private var correctAnswersCount: Int = 0
    @State private var isCompleted: Bool = false
    
    public init(section: EPASection) {
        self.section = section
    }
    
    public var body: some View {
        ZStack {
            Theme.background.ignoresSafeArea()
            
            if questions.isEmpty {
                ProgressView().tint(Theme.cyanAccent)
            } else if isCompleted {
                completionView
            } else {
                activeQuizContent
            }
        }
        .onAppear {
            loadQuestions()
        }
        .sheet(isPresented: $showPaywall) {
            PaywallView()
        }
        .navigationBarBackButtonHidden(true)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button {
                    Haptic.tap()
                    dismiss()
                } label: {
                    Image(systemName: "xmark")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(Theme.textSecondary)
                }
                .minimumTouchTarget()
            }
            ToolbarItem(placement: .navigationBarTrailing) {
                HStack(spacing: 8) {
                    Button {
                        Haptic.tap()
                        engine.toggleRooftopMode()
                    } label: {
                        Image(systemName: engine.isRooftopMode ? "sun.max.fill" : "sun.max")
                            .font(.system(size: 16))
                            .foregroundColor(engine.isRooftopMode ? Theme.safetyAmber : Theme.textSecondary)
                    }
                    .minimumTouchTarget()
                    
                    if !questions.isEmpty && currentIndex < questions.count {
                        let q = questions[currentIndex]
                        Button {
                            Haptic.tap()
                            engine.toggleBookmark(questionId: q.id)
                        } label: {
                            Image(systemName: engine.bookmarkedQuestionIds.contains(q.id) ? "bookmark.fill" : "bookmark")
                                .font(.system(size: 16))
                                .foregroundColor(Theme.safetyAmber)
                        }
                        .minimumTouchTarget()
                    }
                }
            }
        }
    }
    
    // MARK: - Active Quiz Content
    private var activeQuizContent: some View {
        let question = questions[currentIndex]
        return VStack(alignment: .leading, spacing: 16) {
            // Header Progress
            HStack {
                Text("\(currentIndex + 1) of \(questions.count)")
                    .font(.system(size: 14, weight: .semibold, design: .monospaced))
                    .foregroundColor(Theme.textSecondary)
                Spacer()
                Text(question.citation)
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(Theme.textMuted)
            }
            .padding(.horizontal, 20)
            
            // Progress Bar
            GeometryReader { geo in
                ZStack(alignment: .leading) {
                    Rectangle().fill(Theme.cardBorder).frame(height: 4)
                    Rectangle()
                        .fill(Theme.cyanAccent)
                        .frame(width: geo.size.width * CGFloat(currentIndex + 1) / CGFloat(questions.count), height: 4)
                }
                .cornerRadius(2)
            }
            .frame(height: 4)
            .padding(.horizontal, 20)
            
            ScrollView {
                VStack(alignment: .leading, spacing: 20) {
                    if question.isA2L {
                        HStack(spacing: 6) {
                            Image(systemName: "flame.fill").foregroundColor(Theme.safetyAmber)
                            Text("AIM ACT A2L MANDATE").font(.system(size: 11, weight: .black)).foregroundColor(Theme.safetyAmber)
                        }
                    }
                    
                    Text(question.questionText)
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                        .lineSpacing(3)
                    
                    // Options
                    VStack(spacing: 12) {
                        ForEach(0..<question.options.count, id: \.self) { idx in
                            let isSelected = selectedOptionIndex == idx
                            let isCorrectOption = idx == question.correctIndex
                            
                            var bgColor = Theme.cardSurface
                            var borderColor = Theme.cardBorder
                            
                            if hasAnswered {
                                if isCorrectOption {
                                    bgColor = Theme.passGreen.opacity(0.15)
                                    borderColor = Theme.passGreen
                                } else if isSelected {
                                    bgColor = Theme.hazardRed.opacity(0.15)
                                    borderColor = Theme.hazardRed
                                }
                            } else if isSelected {
                                bgColor = Theme.elevatedSurface
                                borderColor = Theme.cyanAccent
                            }
                            
                            Button {
                                guard !hasAnswered else { return }
                                Haptic.selection()
                                selectOption(idx, question: question)
                            } label: {
                                HStack(alignment: .top, spacing: 12) {
                                    Text(["A", "B", "C", "D"][idx])
                                        .font(.system(size: 14, weight: .black, design: .monospaced))
                                        .foregroundColor(hasAnswered && isCorrectOption ? Theme.passGreen : Theme.textSecondary)
                                        .frame(width: 24, height: 24)
                                        .background(Theme.elevatedSurface)
                                        .cornerRadius(6)
                                    
                                    Text(question.options[idx])
                                        .font(.system(size: 15, weight: .medium))
                                        .foregroundColor(Theme.textPrimary)
                                        .multilineTextAlignment(.leading)
                                    
                                    Spacer()
                                }
                                .padding(14)
                                .background(bgColor)
                                .cornerRadius(12)
                                .overlay(RoundedRectangle(cornerRadius: 12).stroke(borderColor, lineWidth: 1.5))
                            }
                        }
                    }
                    
                    // Explanation Drawer
                    if hasAnswered {
                        VStack(alignment: .leading, spacing: 8) {
                            HStack(spacing: 6) {
                                Image(systemName: selectedOptionIndex == question.correctIndex ? "checkmark.circle.fill" : "xmark.circle.fill")
                                    .foregroundColor(selectedOptionIndex == question.correctIndex ? Theme.passGreen : Theme.hazardRed)
                                Text(selectedOptionIndex == question.correctIndex ? "CORRECT" : "INCORRECT")
                                    .font(.system(size: 13, weight: .black))
                                    .foregroundColor(selectedOptionIndex == question.correctIndex ? Theme.passGreen : Theme.hazardRed)
                            }
                            
                            Text(question.explanation)
                                .font(.system(size: 14))
                                .foregroundColor(Theme.textSecondary)
                                .lineSpacing(2)
                        }
                        .padding(16)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .background(Theme.cardSurface)
                        .cornerRadius(12)
                        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
                    }
                }
                .padding(.horizontal, 20)
                .padding(.bottom, 20)
            }
            
            // Next Button
            if hasAnswered {
                Button {
                    Haptic.tap()
                    advanceToNext()
                } label: {
                    Text(currentIndex + 1 < questions.count ? "Next Question →" : "View Results")
                        .font(.system(size: 17, weight: .bold))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(height: 54)
                        .background(Theme.cyanAccent)
                        .cornerRadius(12)
                }
                .padding(.horizontal, 20)
                .padding(.bottom, 20)
            }
        }
    }
    
    // MARK: - Completion View
    private var completionView: some View {
        let scorePct = questions.isEmpty ? 0.0 : (Double(correctAnswersCount) / Double(questions.count)) * 100.0
        let passed = scorePct >= 70.0
        
        return VStack(spacing: 24) {
            Spacer()
            
            ZStack {
                Circle()
                    .fill(passed ? Theme.passGreen.opacity(0.15) : Theme.hazardRed.opacity(0.15))
                    .frame(width: 100, height: 100)
                Image(systemName: passed ? "checkmark.seal.fill" : "xmark.seal.fill")
                    .font(.system(size: 48))
                    .foregroundColor(passed ? Theme.passGreen : Theme.hazardRed)
            }
            
            VStack(spacing: 8) {
                Text(passed ? "Section Drill Passed" : "Needs Review")
                    .font(.system(size: 26, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                
                Text("\(correctAnswersCount) of \(questions.count) correct (\(Int(scorePct))%)")
                    .font(.system(size: 16, weight: .bold, design: .monospaced))
                    .foregroundColor(passed ? Theme.passGreen : Theme.hazardRed)
            }
            
            Spacer()
            
            Button {
                Haptic.tap()
                dismiss()
            } label: {
                Text("Back to Dashboard")
                    .font(.system(size: 17, weight: .bold))
                    .foregroundColor(.black)
                    .frame(maxWidth: .infinity)
                    .frame(height: 54)
                    .background(Theme.cyanAccent)
                    .cornerRadius(12)
            }
            .padding(.horizontal, 24)
            .padding(.bottom, 24)
        }
    }
    
    // MARK: - Logic & Gating
    private func loadQuestions() {
        let pool: [Question]
        switch section {
        case .core: pool = HVACQuestionBank.coreQuestions
        case .type1: pool = HVACQuestionBank.type1Questions
        case .type2: pool = HVACQuestionBank.type2Questions
        case .type3: pool = HVACQuestionBank.type3Questions
        case .universal:
            pool = (HVACQuestionBank.coreQuestions +
                    HVACQuestionBank.type1Questions +
                    HVACQuestionBank.type2Questions +
                    HVACQuestionBank.type3Questions).shuffled()
        }
        
        // Free users get 5 sample questions; Pro users get full question bank
        if storeKit.isProUser {
            self.questions = pool
        } else {
            self.questions = Array(pool.prefix(5))
        }
    }
    
    private func selectOption(_ index: Int, question: Question) {
        selectedOptionIndex = index
        hasAnswered = true
        let isCorrect = index == question.correctIndex
        if isCorrect {
            correctAnswersCount += 1
            Haptic.success()
        } else {
            Haptic.error()
        }
        engine.recordAnswer(questionId: question.id, isCorrect: isCorrect)
    }
    
    private func advanceToNext() {
        if currentIndex + 1 < questions.count {
            currentIndex += 1
            selectedOptionIndex = nil
            hasAnswered = false
        } else {
            isCompleted = true
            let scorePct = questions.isEmpty ? 0.0 : (Double(correctAnswersCount) / Double(questions.count)) * 100.0
            if scorePct >= 80.0 {
                DispatchQueue.main.asyncAfter(deadline: .now() + 1.0) {
                    requestReview()
                }
            }
        }
    }
}
