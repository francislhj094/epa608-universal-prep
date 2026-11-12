import SwiftUI

// MARK: - Deck Filter Mode
public enum FlashcardDeckFilter: String, CaseIterable, Identifiable {
    case all = "All (410)"
    case core = "Core (100)"
    case type1 = "Type I (100)"
    case type2 = "Type II (100)"
    case type3 = "Type III (100)"
    case saved = "Saved"
    
    public var id: String { rawValue }
}

// MARK: - 7-Figure Swipeable Flashcards View ("Truck Break Drills")
public struct FlashcardsView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var engine = EPAEngine.shared
    @State private var storeKit = StoreKitManager.shared
    @State private var showPaywall: Bool = false
    
    @State private var selectedFilter: FlashcardDeckFilter = .all
    @State private var currentCardIndex: Int = 0
    @State private var isFlipped: Bool = false
    
    // Decks
    @State private var activeDeck: [Question] = []
    @State private var stillLearningList: [Question] = []
    @State private var masteredList: [Question] = []
    
    // Drag gesture offset
    @State private var dragOffset: CGSize = .zero
    
    public init(initialFilter: FlashcardDeckFilter = .all) {
        _selectedFilter = State(initialValue: initialFilter)
    }
    
    public var body: some View {
        ZStack {
            Theme.background.ignoresSafeArea()
            
            VStack(spacing: 0) {
                // Top App Bar
                topAppBar
                
                // Deck Section Filter Tabs
                filterTabBar
                
                // Progress Bar
                progressBar
                
                // Card Canvas Body
                ZStack {
                    if isDeckFinished || activeDeck.isEmpty {
                        deckCompletedView
                    } else if let question = currentQuestion {
                        flashcardItem(question: question)
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .padding(.horizontal, 20)
                .padding(.vertical, 16)
                
                // Bottom Action Controls
                if !isDeckFinished && !activeDeck.isEmpty {
                    bottomActionBar
                }
            }
        }
        .sheet(isPresented: $showPaywall) {
            PaywallView()
        }
        .onAppear {
            reloadDeck(filter: selectedFilter)
        }
        .onChange(of: selectedFilter) { newFilter in
            reloadDeck(filter: newFilter)
        }
    }
    
    private var currentQuestion: Question? {
        guard currentCardIndex < activeDeck.count else { return nil }
        return activeDeck[currentCardIndex]
    }
    
    private var isDeckFinished: Bool {
        return currentCardIndex >= activeDeck.count && !activeDeck.isEmpty
    }
    
    // MARK: - Top App Bar
    private var topAppBar: some View {
        HStack {
            Button {
                Haptic.tap()
                dismiss()
            } label: {
                Image(systemName: "arrow.backward")
                    .font(.system(size: 18, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                    .frame(width: 44, height: 44)
            }
            
            Spacer()
            
            VStack(spacing: 2) {
                Text("TRUCK BREAK FLASHCARDS")
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(1)
                
                Text(isDeckFinished ? "Deck Completed" : "Card \(currentCardIndex + 1) of \(activeDeck.count)")
                    .font(.system(size: 16, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
            }
            
            Spacer()
            
            HStack(spacing: 4) {
                Button {
                    Haptic.tap()
                    engine.toggleRooftopMode()
                } label: {
                    Image(systemName: engine.isRooftopMode ? "sun.max.fill" : "sun.max")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(engine.isRooftopMode ? Theme.safetyAmber : Theme.textSecondary)
                        .frame(width: 44, height: 44)
                }
                
                Button {
                    Haptic.selection()
                    shuffleDeck()
                } label: {
                    Image(systemName: "shuffle")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(Theme.cyanAccent)
                        .frame(width: 44, height: 44)
                }
                
                if let question = currentQuestion {
                    let isSaved = engine.bookmarkedQuestionIds.contains(question.id)
                    Button {
                        Haptic.selection()
                        engine.toggleBookmark(questionId: question.id)
                    } label: {
                        Image(systemName: isSaved ? "bookmark.fill" : "bookmark")
                            .font(.system(size: 16, weight: .semibold))
                            .foregroundColor(isSaved ? Theme.passGreen : Theme.textSecondary)
                            .frame(width: 44, height: 44)
                    }
                }
            }
        }
        .padding(.horizontal, 16)
        .padding(.top, 8)
    }
    
    // MARK: - Filter Tab Bar
    private var filterTabBar: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            HStack(spacing: 8) {
                ForEach(FlashcardDeckFilter.allCases) { filter in
                    let isSelected = filter == selectedFilter
                    Button {
                        Haptic.selection()
                        selectedFilter = filter
                    } label: {
                        Text(filter.rawValue)
                            .font(.system(size: 13, weight: isSelected ? .bold : .medium))
                            .padding(.horizontal, 14)
                            .padding(.vertical, 8)
                            .background(isSelected ? Theme.cyanAccent.opacity(0.15) : Theme.cardSurface)
                            .foregroundColor(isSelected ? Theme.cyanAccent : Theme.textMuted)
                            .cornerRadius(10)
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(isSelected ? Theme.cyanAccent : Theme.cardBorder, lineWidth: 1)
                            )
                    }
                }
            }
            .padding(.horizontal, 16)
            .padding(.vertical, 10)
        }
    }
    
    // MARK: - Progress Bar
    private var progressBar: some View {
        let progress = activeDeck.isEmpty ? 0.0 : Double(currentCardIndex) / Double(activeDeck.count)
        return GeometryReader { geo in
            ZStack(alignment: .leading) {
                Rectangle()
                    .fill(Theme.elevatedSurface)
                    .frame(height: 4)
                Rectangle()
                    .fill(Theme.cyanAccent)
                    .frame(width: geo.size.width * CGFloat(progress), height: 4)
            }
        }
        .frame(height: 4)
    }
    
    // MARK: - Flashcard Item with 3D Flip & Drag
    private func flashcardItem(question: Question) -> some View {
        ZStack {
            // Card Content
            ZStack {
                if !isFlipped {
                    cardFront(question: question)
                } else {
                    cardBack(question: question)
                        .rotation3DEffect(.degrees(180), axis: (x: 0, y: 1, z: 0))
                }
            }
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Theme.cardSurface)
            .cornerRadius(20)
            .overlay(
                RoundedRectangle(cornerRadius: 20)
                    .stroke(isFlipped ? Theme.passGreen.opacity(0.4) : Theme.cardBorder, lineWidth: 1.5)
            )
            .rotation3DEffect(
                .degrees(isFlipped ? 180 : 0),
                axis: (x: 0, y: 1, z: 0)
            )
            .offset(x: dragOffset.width, y: dragOffset.height * 0.2)
            .rotationEffect(.degrees(Double(dragOffset.width) / 25.0))
            .gesture(
                DragGesture()
                    .onChanged { value in
                        dragOffset = value.translation
                    }
                    .onEnded { value in
                        let threshold: CGFloat = 120
                        if value.translation.width > threshold {
                            // Swiped Right -> Mastered
                            dismissCard(mastered: true)
                        } else if value.translation.width < -threshold {
                            // Swiped Left -> Still Learning
                            dismissCard(mastered: false)
                        } else {
                            // Snap back
                            withAnimation(.spring(response: 0.35, dampingFraction: 0.7)) {
                                dragOffset = .zero
                            }
                        }
                    }
            )
            .onTapGesture {
                flipCard()
            }
            
            // Drag Stamp Stamps
            if abs(dragOffset.width) > 40 {
                let isRight = dragOffset.width > 0
                VStack {
                    HStack {
                        if !isRight { Spacer() }
                        Text(isRight ? "I KNOW THIS ✓" : "STILL LEARNING ✗")
                            .font(.system(size: 16, weight: .black))
                            .foregroundColor(isRight ? Theme.passGreen : Theme.hazardRed)
                            .padding(.horizontal, 14)
                            .padding(.vertical, 8)
                            .background(isRight ? Theme.passGreen.opacity(0.2) : Theme.hazardRed.opacity(0.2))
                            .cornerRadius(10)
                            .overlay(
                                RoundedRectangle(cornerRadius: 10)
                                    .stroke(isRight ? Theme.passGreen : Theme.hazardRed, lineWidth: 2)
                            )
                            .padding(24)
                        if isRight { Spacer() }
                    }
                    Spacer()
                }
                .opacity(Double(min(abs(dragOffset.width) / 100.0, 1.0)))
            }
        }
    }
    
    // MARK: - Card Front View
    private func cardFront(question: Question) -> some View {
        VStack(alignment: .leading, spacing: 20) {
            // Section Badge
            HStack {
                Text(question.section.rawValue.uppercased())
                    .font(.system(size: 12, weight: .black))
                    .foregroundColor(Theme.sectionColor(for: question.section))
                    .padding(.horizontal, 10)
                    .padding(.vertical, 5)
                    .background(Theme.sectionColor(for: question.section).opacity(0.15))
                    .cornerRadius(8)
                
                Spacer()
                
                Text("Tap card to flip")
                    .font(.system(size: 11, weight: .medium))
                    .foregroundColor(Theme.textMuted)
            }
            
            Spacer()
            
            // Question Prompt
            Text(question.text)
                .font(.system(size: 20, weight: .bold))
                .foregroundColor(Theme.textPrimary)
                .lineSpacing(6)
            
            Spacer()
            
            // Bottom Prompt Bar
            HStack(spacing: 8) {
                Image(systemName: "arrow.triangle.2.circlepath")
                    .font(.system(size: 14))
                    .foregroundColor(Theme.textMuted)
                Text("Tap card or button to reveal answer")
                    .font(.system(size: 13, weight: .medium))
                    .foregroundColor(Theme.textMuted)
            }
            .frame(maxWidth: .infinity)
            .padding(.vertical, 12)
            .background(Theme.elevatedSurface)
            .cornerRadius(12)
        }
        .padding(24)
    }
    
    // MARK: - Card Back View
    private func cardBack(question: Question) -> some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 18) {
                // Section Badge & Answer Tag
                HStack(spacing: 8) {
                    Image(systemName: "checkmark.circle.fill")
                        .font(.system(size: 16))
                        .foregroundColor(Theme.passGreen)
                    
                    Text("STATUTORY CORRECT ANSWER")
                        .font(.system(size: 11, weight: .bold))
                        .foregroundColor(Theme.passGreen)
                        .tracking(1)
                }
                
                // Correct Answer
                Text(question.correctAnswerText)
                    .font(.system(size: 18, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                    .lineSpacing(4)
                
                Divider()
                    .background(Theme.cardBorder)
                
                // Detailed Technical Explanation
                Text(question.explanation)
                    .font(.system(size: 14))
                    .foregroundColor(Theme.textSecondary)
                    .lineSpacing(4)
                
                // EPA 40 CFR Part 82 Subpart Citation Pill
                HStack {
                    Text("LEGAL CITATION: \(question.citation)")
                        .font(.system(size: 12, weight: .bold, design: .monospaced))
                        .foregroundColor(Theme.cyanAccent)
                }
                .padding(.horizontal, 12)
                .padding(.vertical, 8)
                .frame(maxWidth: .infinity, alignment: .leading)
                .background(Theme.elevatedSurface)
                .cornerRadius(8)
                .overlay(RoundedRectangle(cornerRadius: 8).stroke(Theme.cardBorder, lineWidth: 1))
            }
            .padding(24)
        }
    }
    
    // MARK: - Bottom Action Controls
    private var bottomActionBar: some View {
        HStack(spacing: 20) {
            // Still Learning Button
            Button {
                Haptic.error()
                dismissCard(mastered: false)
            } label: {
                Image(systemName: "xmark")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(Theme.hazardRed)
                    .frame(width: 58, height: 58)
                    .background(Theme.hazardRed.opacity(0.15))
                    .clipShape(Circle())
                    .overlay(Circle().stroke(Theme.hazardRed.opacity(0.5), lineWidth: 1.5))
            }
            
            // Flip Button
            Button {
                flipCard()
            } label: {
                HStack(spacing: 8) {
                    Image(systemName: "arrow.triangle.2.circlepath")
                        .font(.system(size: 16, weight: .bold))
                    Text(isFlipped ? "Show Question" : "Tap to Flip")
                        .font(.system(size: 15, weight: .bold))
                }
                .foregroundColor(Theme.textPrimary)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(Theme.elevatedSurface)
                .cornerRadius(26)
                .overlay(RoundedRectangle(cornerRadius: 26).stroke(Theme.cardBorder, lineWidth: 1))
            }
            
            // Mastered Button
            Button {
                Haptic.success()
                dismissCard(mastered: true)
            } label: {
                Image(systemName: "checkmark")
                    .font(.system(size: 20, weight: .bold))
                    .foregroundColor(Theme.passGreen)
                    .frame(width: 58, height: 58)
                    .background(Theme.passGreen.opacity(0.15))
                    .clipShape(Circle())
                    .overlay(Circle().stroke(Theme.passGreen.opacity(0.5), lineWidth: 1.5))
            }
        }
        .padding(.horizontal, 24)
        .padding(.bottom, 20)
    }
    
    // MARK: - Deck Completed View
    private var deckCompletedView: some View {
        let total = activeDeck.count
        let mastered = masteredList.count
        let learning = stillLearningList.count
        let reviewed = mastered + learning
        let retention = reviewed > 0 ? Int((Double(mastered) / Double(reviewed)) * 100.0) : (total > 0 ? Int((Double(mastered) / Double(total)) * 100.0) : 0)
        let passing = retention >= 70
        
        return VStack(spacing: 24) {
            Spacer()
            
            // Retention Gauge Circle
            ZStack {
                Circle()
                    .stroke(Theme.cardBorder, lineWidth: 10)
                    .frame(width: 130, height: 130)
                
                Circle()
                    .trim(from: 0, to: CGFloat(Double(retention) / 100.0))
                    .stroke(
                        passing ? Theme.passGreen : Theme.safetyAmber,
                        style: StrokeStyle(lineWidth: 10, lineCap: .round)
                    )
                    .frame(width: 130, height: 130)
                    .rotationEffect(.degrees(-90))
                
                VStack(spacing: 2) {
                    Text("\(retention)%")
                        .font(.system(size: 32, weight: .black, design: .monospaced))
                        .foregroundColor(Theme.textPrimary)
                    Text("RETENTION")
                        .font(.system(size: 11, weight: .bold))
                        .foregroundColor(Theme.textMuted)
                        .tracking(1)
                }
            }
            
            VStack(spacing: 8) {
                Text("Truck Drill Complete!")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                
                Text("\(mastered) Mastered · \(learning) Still Learning")
                    .font(.system(size: 15))
                    .foregroundColor(Theme.textSecondary)
            }
            
            Spacer()
            
            // Action Buttons
            VStack(spacing: 12) {
                if learning > 0 {
                    Button {
                        Haptic.tap()
                        reviewStillLearning()
                    } label: {
                        Text("Review Still Learning (\(learning))")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(.black)
                            .frame(maxWidth: .infinity)
                            .frame(height: 52)
                            .background(Theme.safetyAmber)
                            .cornerRadius(12)
                    }
                }
                
                Button {
                    Haptic.tap()
                    reloadDeck(filter: selectedFilter)
                } label: {
                    Text("Restart Full Deck")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(.black)
                        .frame(maxWidth: .infinity)
                        .frame(height: 52)
                        .background(Theme.cyanAccent)
                        .cornerRadius(12)
                }
                
                Button {
                    Haptic.tap()
                    dismiss()
                } label: {
                    Text("Return to Technician Hub")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                        .frame(maxWidth: .infinity)
                        .frame(height: 52)
                        .background(Theme.elevatedSurface)
                        .cornerRadius(12)
                        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
                }
            }
            .padding(.bottom, 20)
        }
        .padding(.horizontal, 20)
    }
    
    // MARK: - Actions
    private func flipCard() {
        Haptic.selection()
        withAnimation(.spring(response: 0.4, dampingFraction: 0.8)) {
            isFlipped.toggle()
        }
    }
    
    private func dismissCard(mastered: Bool) {
        guard let question = currentQuestion else { return }
        
        let screenWidth = UIScreen.main.bounds.width
        let targetX: CGFloat = mastered ? screenWidth * 1.2 : -screenWidth * 1.2
        
        withAnimation(.easeOut(duration: 0.25)) {
            dragOffset = CGSize(width: targetX, height: 0)
        }
        
        DispatchQueue.main.asyncAfter(deadline: .now() + 0.25) {
            if mastered {
                masteredList.append(question)
                engine.recordAnswer(questionId: question.id, isCorrect: true)
            } else {
                stillLearningList.append(question)
                engine.recordAnswer(questionId: question.id, isCorrect: false)
            }
            
            dragOffset = .zero
            isFlipped = false
            currentCardIndex += 1
        }
    }
    
    private func reviewStillLearning() {
        activeDeck = stillLearningList.shuffled()
        stillLearningList.removeAll()
        masteredList.removeAll()
        currentCardIndex = 0
        isFlipped = false
    }
    
    private func shuffleDeck() {
        activeDeck.shuffle()
        currentCardIndex = 0
        isFlipped = false
    }
    
    private func reloadDeck(filter: FlashcardDeckFilter) {
        let pool: [Question]
        switch filter {
        case .all:
            pool = HVACQuestionBank.studyQuestions
        case .core:
            pool = HVACQuestionBank.coreQuestions
        case .type1:
            pool = HVACQuestionBank.type1Questions
        case .type2:
            pool = HVACQuestionBank.type2Questions
        case .type3:
            pool = HVACQuestionBank.type3Questions
        case .saved:
            let saved = HVACQuestionBank.studyQuestions.filter { engine.bookmarkedQuestionIds.contains($0.id) }
            pool = saved.isEmpty ? HVACQuestionBank.coreQuestions : saved
        }
        activeDeck = pool.shuffled()
        stillLearningList.removeAll()
        masteredList.removeAll()
        currentCardIndex = 0
        isFlipped = false
    }
}
