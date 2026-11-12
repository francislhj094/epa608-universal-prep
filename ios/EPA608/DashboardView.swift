import SwiftUI

public struct DashboardView: View {
    @State private var engine = EPAEngine.shared
    @State private var storeKit = StoreKitManager.shared
    @State private var showPaywall: Bool = false
    @State private var showFlashcards: Bool = false
    @State private var showCertificate: Bool = false
    @State private var selectedSectionForQuiz: EPASection?
    
    public init() {}
    
    public var body: some View {
        NavigationStack {
            ZStack {
                Theme.background.ignoresSafeArea()
                
                ScrollView {
                    VStack(spacing: 20) {
                        // Header
                        headerView
                        
                        // Readiness Card
                        readinessCard
                        
                        // Universal Pass Certificate Banner
                        certificateBanner
                        
                        // Quick Drills
                        quickDrillsSection
                        
                        // Section Breakdown
                        sectionBreakdownSection
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 100) // Space for bottom tab bar
                }
            }
            .navigationDestination(item: $selectedSectionForQuiz) { section in
                QuizView(section: section)
            }
            .sheet(isPresented: $showPaywall) {
                PaywallView()
            }
            .fullScreenCover(isPresented: $showFlashcards) {
                FlashcardsView()
            }
            .fullScreenCover(isPresented: $showCertificate) {
                CertificateView()
            }
        }
    }
    
    // MARK: - Header
    private var headerView: some View {
        HStack {
            VStack(alignment: .leading, spacing: 2) {
                Text("EPA 608 UNIVERSAL")
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(1.5)
                Text("Technician Hub")
                    .font(.system(size: 24, weight: .black))
                    .foregroundColor(Theme.textPrimary)
            }
            Spacer()
            
            HStack(spacing: 8) {
                // Rooftop Sunlight Mode Toggle Pill
                Button {
                    Haptic.selection()
                    engine.toggleRooftopMode()
                } label: {
                    HStack(spacing: 4) {
                        Image(systemName: engine.isRooftopMode ? "sun.max.fill" : "moon.stars.fill")
                            .font(.system(size: 13, weight: .bold))
                        Text(engine.isRooftopMode ? "SUN" : "ROOF")
                            .font(.system(size: 13, weight: .bold))
                    }
                    .padding(.horizontal, 10)
                    .padding(.vertical, 6)
                    .background(engine.isRooftopMode ? Theme.safetyAmber.opacity(0.2) : Theme.elevatedSurface)
                    .foregroundColor(engine.isRooftopMode ? Theme.safetyAmber : Theme.textSecondary)
                    .cornerRadius(20)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(engine.isRooftopMode ? Theme.safetyAmber : Theme.cardBorder, lineWidth: 1)
                    )
                }

                // PRO / UPGRADE Button
                Button {
                    Haptic.tap()
                    showPaywall = true
                } label: {
                    HStack(spacing: 6) {
                        Image(systemName: "crown.fill")
                            .font(.system(size: 13))
                        Text(storeKit.isProUser ? "PRO" : "UPGRADE")
                            .font(.system(size: 13, weight: .bold))
                    }
                    .padding(.horizontal, 12)
                    .padding(.vertical, 6)
                    .background(storeKit.isProUser ? Theme.passGreen.opacity(0.2) : Theme.cyanAccent.opacity(0.2))
                    .foregroundColor(storeKit.isProUser ? Theme.passGreen : Theme.cyanAccent)
                    .cornerRadius(20)
                    .overlay(
                        RoundedRectangle(cornerRadius: 20)
                            .stroke(storeKit.isProUser ? Theme.passGreen : Theme.cyanAccent, lineWidth: 1)
                    )
                }
            }
        }
        .padding(.top, 10)
    }
    
    // MARK: - Readiness Card
    private var readinessCard: some View {
        VStack(spacing: 16) {
            HStack(spacing: 16) {
                // Readiness Dial
                ZStack {
                    Circle()
                        .stroke(Theme.cardBorder, lineWidth: 8)
                        .frame(width: 80, height: 80)
                    
                    Circle()
                        .trim(from: 0, to: CGFloat(engine.overallReadinessScore / 100.0))
                        .stroke(
                            engine.overallReadinessScore >= 70 ? Theme.passGreen : Theme.safetyAmber,
                            style: StrokeStyle(lineWidth: 8, lineCap: .round)
                        )
                        .frame(width: 80, height: 80)
                        .rotationEffect(.degrees(-90))
                    
                    Text("\(Int(engine.overallReadinessScore))%")
                        .font(.system(size: 18, weight: .black, design: .monospaced))
                        .foregroundColor(Theme.textPrimary)
                }
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(engine.overallReadinessScore >= 70 ? "Passing Zone" : "Prep Required")
                        .font(.system(size: 18, weight: .bold))
                        .foregroundColor(engine.overallReadinessScore >= 70 ? Theme.passGreen : Theme.safetyAmber)
                    
                    Text("Official EPA passing threshold is 70% (minimum 18/25 per section).")
                        .font(.system(size: 13))
                        .foregroundColor(Theme.textSecondary)
                }
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity)
        .background(Theme.cardSurface)
        .cornerRadius(16)
        .overlay(RoundedRectangle(cornerRadius: 16).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Certificate Banner
    private var certificateBanner: some View {
        Button {
            Haptic.tap()
            showCertificate = true
        } label: {
            HStack(spacing: 12) {
                ZStack {
                    Circle()
                        .fill(Theme.safetyAmber.opacity(0.18))
                        .frame(width: 44, height: 44)
                    Image(systemName: "checkmark.seal.fill")
                        .font(.system(size: 22))
                        .foregroundColor(Theme.safetyAmber)
                }
                
                VStack(alignment: .leading, spacing: 2) {
                    Text("UNIVERSAL PASS CERTIFICATE")
                        .font(.system(size: 10, weight: .black))
                        .foregroundColor(Theme.safetyAmber)
                        .tracking(1)
                    Text("Official Proof of Exam Readiness")
                        .font(.system(size: 14, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Text("Shareable with employers & union apprenticeships")
                        .font(.system(size: 11))
                        .foregroundColor(Theme.textSecondary)
                }
                
                Spacer()
                
                Image(systemName: "chevron.right")
                    .font(.system(size: 14, weight: .semibold))
                    .foregroundColor(Theme.safetyAmber)
            }
            .padding(14)
            .background(Theme.cardSurface)
            .cornerRadius(14)
            .overlay(RoundedRectangle(cornerRadius: 14).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Quick Drills & Flashcards
    private var quickDrillsSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("QUICK DRILLS & FLASHCARDS")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            // Row 1: Flashcards & Rapid Fire
            HStack(spacing: 12) {
                drillButton(title: "Flashcards", desc: "Truck Break Flip", icon: "square.stack.3d.up.fill", color: Theme.cyanAccent) {
                    showFlashcards = true
                }
                
                drillButton(title: "Rapid Fire", desc: "10 Random Qs", icon: "bolt.fill", color: Theme.safetyAmber) {
                    selectedSectionForQuiz = .universal
                }
            }
            
            // Row 2: Weak Areas & Saved
            HStack(spacing: 12) {
                drillButton(title: "Weak Areas", desc: "\(engine.completedQuestionIds.count - engine.correctQuestionIds.count) Missed", icon: "exclamationmark.triangle.fill", color: Theme.hazardRed) {
                    selectedSectionForQuiz = .core
                }
                
                drillButton(title: "Saved", desc: "\(engine.bookmarkedQuestionIds.count) Saved", icon: "bookmark.fill", color: Theme.passGreen) {
                    selectedSectionForQuiz = .type1
                }
            }
        }
    }
    
    private func drillButton(title: String, desc: String, icon: String, color: Color, action: @escaping () -> Void) -> some View {
        Button {
            Haptic.tap()
            action()
        } label: {
            VStack(alignment: .leading, spacing: 6) {
                Image(systemName: icon)
                    .font(.system(size: 18))
                    .foregroundColor(color)
                
                Text(title)
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                
                Text(desc)
                    .font(.system(size: 11))
                    .foregroundColor(Theme.textSecondary)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(12)
            .background(Theme.cardSurface)
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Section Breakdown
    private var sectionBreakdownSection: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("EXAM SECTIONS (40 CFR PART 82)")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            VStack(spacing: 12) {
                ForEach([EPASection.core, .type1, .type2, .type3]) { sec in
                    sectionRow(section: sec)
                }
            }
        }
    }
    
    private func sectionRow(section: EPASection) -> some View {
        let readiness = engine.readiness(for: section)
        return Button {
            Haptic.tap()
            selectedSectionForQuiz = section
        } label: {
            VStack(spacing: 12) {
                HStack {
                    VStack(alignment: .leading, spacing: 4) {
                        HStack {
                            Text(section.rawValue)
                                .font(.system(size: 13, weight: .black))
                                .foregroundColor(Theme.sectionColor(for: section))
                            Text("•")
                                .foregroundColor(Theme.textMuted)
                            Text(section.title)
                                .font(.system(size: 15, weight: .bold))
                                .foregroundColor(Theme.textPrimary)
                        }
                        Text(section.subtitle)
                            .font(.system(size: 12))
                            .foregroundColor(Theme.textSecondary)
                            .lineLimit(1)
                    }
                    Spacer()
                    
                    Text("\(Int(readiness))%")
                        .font(.system(size: 16, weight: .bold, design: .monospaced))
                        .foregroundColor(readiness >= 70 ? Theme.passGreen : Theme.textSecondary)
                }
                
                // Progress Bar
                GeometryReader { geo in
                    ZStack(alignment: .leading) {
                        Rectangle()
                            .fill(Theme.cardBorder)
                            .frame(height: 5)
                        Rectangle()
                            .fill(Theme.sectionColor(for: section))
                            .frame(width: geo.size.width * CGFloat(readiness / 100.0), height: 5)
                    }
                    .cornerRadius(2.5)
                }
                .frame(height: 5)
            }
            .padding(16)
            .background(Theme.cardSurface)
            .cornerRadius(14)
            .overlay(RoundedRectangle(cornerRadius: 14).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
}
