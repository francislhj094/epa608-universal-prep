import SwiftUI

public struct SettingsView: View {
    @State private var engine = EPAEngine.shared
    @State private var storeKit = StoreKitManager.shared
    @State private var showPaywall: Bool = false
    @State private var showResetAlert: Bool = false
    @State private var showCertificate: Bool = false
    
    public init() {}
    
    public var body: some View {
        NavigationStack {
            ZStack {
                Theme.background.ignoresSafeArea()
                
                ScrollView {
                    VStack(spacing: 20) {
                        // Header
                        headerView
                        
                        // License Status Card
                        licenseStatusCard
                        
                        // Universal Pass Certificate Row
                        certificateRow
                        
                        // Study Preferences
                        studyPreferencesSection
                        
                        // Job-Site Environment
                        environmentSection
                        
                        // Support & Legal
                        legalSection
                        
                        // Reset Data
                        resetSection
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 100)
                }
            }
            .sheet(isPresented: $showPaywall) {
                PaywallView()
            }
            .fullScreenCover(isPresented: $showCertificate) {
                CertificateView()
            }
            .alert("Reset All Study Progress?", isPresented: $showResetAlert) {
                Button("Cancel", role: .cancel) {}
                Button("Reset Everything", role: .destructive) {
                    Haptic.tap()
                    engine.resetProgress()
                }
            } message: {
                Text("This permanently deletes all your question attempts, diagnostic scores, and saved bookmarks.")
            }
        }
    }
    
    // MARK: - Header
    private var headerView: some View {
        HStack {
            VStack(alignment: .leading, spacing: 2) {
                Text("TECHNICIAN PROFILE")
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(1.5)
                Text("Settings & Pass Guarantee")
                    .font(.system(size: 24, weight: .black))
                    .foregroundColor(Theme.textPrimary)
            }
            Spacer()
        }
        .padding(.top, 10)
    }
    
    // MARK: - License Status Card
    private var licenseStatusCard: some View {
        VStack(spacing: 12) {
            HStack {
                ZStack {
                    Circle()
                        .fill(storeKit.isProUser ? Theme.passGreen.opacity(0.15) : Theme.cyanAccent.opacity(0.15))
                        .frame(width: 44, height: 44)
                    Image(systemName: storeKit.isProUser ? "checkmark.seal.fill" : "crown.fill")
                        .foregroundColor(storeKit.isProUser ? Theme.passGreen : Theme.cyanAccent)
                        .font(.system(size: 20))
                }
                
                VStack(alignment: .leading, spacing: 2) {
                    Text(storeKit.isProUser ? "Universal License Active" : "Free Study Version")
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Text(storeKit.isProUser ? "100% Pass Guarantee Protected" : "Limited access to 2027 question banks")
                        .font(.system(size: 13))
                        .foregroundColor(Theme.textSecondary)
                }
                
                Spacer()
                
                if !storeKit.isProUser {
                    Button {
                        Haptic.tap()
                        showPaywall = true
                    } label: {
                        Text("Upgrade")
                            .font(.system(size: 13, weight: .bold))
                            .foregroundColor(.black)
                            .padding(.horizontal, 14)
                            .padding(.vertical, 8)
                            .background(Theme.cyanAccent)
                            .cornerRadius(10)
                    }
                }
            }
        }
        .padding(16)
        .background(Theme.cardSurface)
        .cornerRadius(14)
        .overlay(RoundedRectangle(cornerRadius: 14).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Certificate Row
    private var certificateRow: some View {
        Button {
            Haptic.tap()
            showCertificate = true
        } label: {
            HStack(spacing: 12) {
                ZStack {
                    Circle()
                        .fill(Theme.safetyAmber.opacity(0.18))
                        .frame(width: 40, height: 40)
                    Image(systemName: "checkmark.seal.fill")
                        .font(.system(size: 20))
                        .foregroundColor(Theme.safetyAmber)
                }
                
                VStack(alignment: .leading, spacing: 2) {
                    Text("Universal Pass Certificate")
                        .font(.system(size: 15, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Text("Official shareable verification for employers & UA unions")
                        .font(.system(size: 12))
                        .foregroundColor(Theme.textSecondary)
                }
                
                Spacer()
                
                Text("VERIFIED")
                    .font(.system(size: 10, weight: .black))
                    .foregroundColor(Theme.passGreen)
                    .padding(.horizontal, 8)
                    .padding(.vertical, 4)
                    .background(Theme.passGreen.opacity(0.15))
                    .cornerRadius(6)
            }
            .padding(14)
            .background(Theme.cardSurface)
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Study Preferences
    private var studyPreferencesSection: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("STUDY SCHEDULE")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            VStack(spacing: 1) {
                DatePicker(
                    "Target Exam Date",
                    selection: $engine.targetExamDate,
                    displayedComponents: .date
                )
                .font(.system(size: 15, weight: .medium))
                .foregroundColor(Theme.textPrimary)
                .padding(14)
                .background(Theme.cardSurface)
            }
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Job-Site Environment
    private var environmentSection: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("JOB-SITE ENVIRONMENT")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            VStack(spacing: 0) {
                Toggle(isOn: Binding(
                    get: { engine.isRooftopMode },
                    set: { _ in
                        Haptic.tap()
                        engine.toggleRooftopMode()
                    }
                )) {
                    HStack(spacing: 12) {
                        Image(systemName: "sun.max.fill")
                            .foregroundColor(engine.isRooftopMode ? Theme.safetyAmber : Theme.cyanAccent)
                            .font(.system(size: 20))
                            .frame(width: 28)
                        
                        VStack(alignment: .leading, spacing: 2) {
                            Text("Rooftop Sunlight Mode")
                                .font(.system(size: 15, weight: .bold))
                                .foregroundColor(Theme.textPrimary)
                            Text("21:1 ultra-contrast for bright sunlight & rooftop glare")
                                .font(.system(size: 12))
                                .foregroundColor(Theme.textSecondary)
                        }
                    }
                }
                .tint(Theme.cyanAccent)
                .padding(14)
            }
            .background(Theme.cardSurface)
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Legal & Support
    private var legalSection: some View {
        VStack(alignment: .leading, spacing: 10) {
            Text("RESOURCES & POLICIES")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            VStack(spacing: 0) {
                Button {
                    Haptic.tap()
                    Task { await storeKit.restorePurchases() }
                } label: {
                    settingsRow(title: "Restore StoreKit Purchases", icon: "arrow.clockwise")
                }
                
                Divider().background(Theme.cardBorder)
                
                Link(destination: URL(string: "https://epa608prep.com/support.html")!) {
                    settingsRow(title: "Technician Support & Help", icon: "questionmark.circle")
                }
                
                Divider().background(Theme.cardBorder)
                
                Link(destination: URL(string: "https://epa608prep.com/privacy.html")!) {
                    settingsRow(title: "Privacy Policy", icon: "hand.raised")
                }
                
                Divider().background(Theme.cardBorder)
                
                Link(destination: URL(string: "https://epa608prep.com/terms.html")!) {
                    settingsRow(title: "Terms of Use (EULA)", icon: "doc.text")
                }
            }
            .background(Theme.cardSurface)
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
    
    // MARK: - Reset Section
    private var resetSection: some View {
        VStack(spacing: 12) {
            Button {
                Haptic.tap()
                showResetAlert = true
            } label: {
                Text("Reset All Study History")
                    .font(.system(size: 14, weight: .bold))
                    .foregroundColor(Theme.hazardRed)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Theme.hazardRed.opacity(0.12))
                    .cornerRadius(10)
            }
            
            Text("EPA 608 Universal HVAC Prep · Build 2027.1\nCompliant with 40 CFR Part 82 Regulations")
                .font(.system(size: 11))
                .foregroundColor(Theme.textMuted)
                .multilineTextAlignment(.center)
        }
    }
    
    private func settingsRow(title: String, icon: String) -> some View {
        HStack {
            Image(systemName: icon)
                .font(.system(size: 16))
                .foregroundColor(Theme.cyanAccent)
                .frame(width: 24)
            Text(title)
                .font(.system(size: 15, weight: .medium))
                .foregroundColor(Theme.textPrimary)
            Spacer()
            Image(systemName: "chevron.right")
                .font(.system(size: 13, weight: .bold))
                .foregroundColor(Theme.textMuted)
        }
        .padding(14)
    }
}
