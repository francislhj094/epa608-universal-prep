import SwiftUI

public struct CertificateView: View {
    @Environment(\.dismiss) private var dismiss
    @State private var engine = EPAEngine.shared
    
    @State private var showEditNameAlert: Bool = false
    @State private var nameInput: String = ""
    @State private var showCopiedToast: Bool = false
    
    public init() {}
    
    public var body: some View {
        NavigationStack {
            ZStack {
                Theme.background.ignoresSafeArea()
                
                ScrollView {
                    VStack(spacing: 20) {
                        // Certificate Parchment Card
                        certificateCard
                        
                        // Action Buttons
                        actionButtons
                        
                        Spacer(modifier: .height(40))
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 14)
                }
            }
            .navigationTitle("EPA Universal Certificate")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button {
                        Haptic.tap()
                        dismiss()
                    } label: {
                        Image(systemName: "xmark")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(Theme.textPrimary)
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
                        
                        ShareLink(
                            item: engine.getCertificateData().shareableText,
                            subject: Text("Official EPA 608 Universal Certificate"),
                            message: Text("EPA 608 Universal Readiness Certificate for \(engine.technicianName)")
                        ) {
                            Image(systemName: "square.and.arrow.up")
                                .font(.system(size: 16, weight: .bold))
                                .foregroundColor(Theme.cyanAccent)
                        }
                        .minimumTouchTarget()
                    }
                }
            }
            .alert("Edit Technician Name", isPresented: $showEditNameAlert) {
                TextField("Technician Name / Company", text: $nameInput)
                Button("Save", role: .none) {
                    Haptic.tap()
                    engine.setTechnicianName(nameInput)
                }
                Button("Cancel", role: .cancel) {}
            } message: {
                Text("Enter your full legal name or Union Local/Company to be printed on your official credential.")
            }
            .overlay(alignment: .bottom) {
                if showCopiedToast {
                    Text("Verification link copied to clipboard!")
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(.black)
                        .padding(.horizontal, 16)
                        .padding(.vertical, 10)
                        .background(Theme.cyanAccent)
                        .cornerRadius(20)
                        .padding(.bottom, 20)
                        .transition(.move(edge: .bottom).combined(with: .opacity))
                }
            }
        }
    }
    
    // MARK: - Certificate Card
    private var certificateCard: some View {
        let cert = engine.getCertificateData()
        
        return VStack(spacing: 16) {
            // Statutory Heading
            VStack(spacing: 4) {
                Text("UNITED STATES ENVIRONMENTAL PROTECTION AGENCY")
                    .font(.system(size: 9, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(1.2)
                    .multilineTextAlignment(.center)
                
                Text("CLEAN AIR ACT TITLE VI • 40 CFR PART 82")
                    .font(.system(size: 9, weight: .semibold))
                    .foregroundColor(Theme.textMuted)
                    .tracking(1)
                
                Text("CERTIFICATE OF READINESS")
                    .font(.system(size: 22, weight: .black))
                    .foregroundColor(Theme.textPrimary)
                    .padding(.top, 4)
                
                Text("SECTION 608 UNIVERSAL CERTIFICATION")
                    .font(.system(size: 13, weight: .heavy))
                    .foregroundColor(Theme.cyanAccent)
            }
            
            // Golden Verified Seal
            ZStack {
                Circle()
                    .fill(Color(red: 0.85, green: 0.47, blue: 0.02).opacity(0.18))
                    .frame(width: 80, height: 80)
                Circle()
                    .stroke(Theme.safetyAmber, lineWidth: 2)
                    .frame(width: 80, height: 80)
                
                VStack(spacing: 2) {
                    Image(systemName: "checkmark.seal.fill")
                        .font(.system(size: 34))
                        .foregroundColor(Theme.safetyAmber)
                    Text("VERIFIED")
                        .font(.system(size: 9, weight: .black))
                        .foregroundColor(Theme.safetyAmber)
                        .tracking(1)
                }
            }
            
            // Technician Presentation
            VStack(spacing: 4) {
                Text("THIS OFFICIALLY CERTIFIES THAT")
                    .font(.system(size: 10, weight: .bold))
                    .foregroundColor(Theme.textMuted)
                    .tracking(1)
                
                Button {
                    Haptic.tap()
                    nameInput = engine.technicianName
                    showEditNameAlert = true
                } label: {
                    HStack(spacing: 6) {
                        Text(cert.technicianName)
                            .font(.system(size: 24, weight: .black))
                            .foregroundColor(Theme.textPrimary)
                        Image(systemName: "pencil.circle.fill")
                            .font(.system(size: 18))
                            .foregroundColor(Theme.cyanAccent)
                    }
                }
                
                Text("has demonstrated full technical competency across Core, Type I, Type II, and Type III disciplines under Section 608 of the federal Clean Air Act.")
                    .font(.system(size: 12))
                    .foregroundColor(Theme.textSecondary)
                    .multilineTextAlignment(.center)
                    .lineSpacing(3)
                    .padding(.horizontal, 10)
            }
            
            Divider().background(Theme.cardBorder)
            
            // 4-Discipline Modular Scoring Matrix
            VStack(spacing: 8) {
                Text("STATUTORY DISCIPLINE MASTERY (70% PASS MINIMUM)")
                    .font(.system(size: 9, weight: .bold))
                    .foregroundColor(Theme.textMuted)
                    .tracking(1)
                
                HStack(spacing: 6) {
                    scoreBadge(title: "CORE", score: cert.coreScore)
                    scoreBadge(title: "TYPE I", score: cert.type1Score)
                    scoreBadge(title: "TYPE II", score: cert.type2Score)
                    scoreBadge(title: "TYPE III", score: cert.type3Score)
                }
                
                HStack {
                    Text("Overall Universal Readiness:")
                        .font(.system(size: 13, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Spacer()
                    Text("\(Int(cert.overallScore))% (PASSED)")
                        .font(.system(size: 13, weight: .black))
                        .foregroundColor(Theme.passGreen)
                }
                .padding(10)
                .background(Theme.cardSurface)
                .cornerRadius(8)
            }
            
            Divider().background(Theme.cardBorder)
            
            // Credential ID & Signatures Block
            HStack {
                VStack(alignment: .leading, spacing: 3) {
                    Text("CREDENTIAL ID: \(cert.certificateId)")
                        .font(.system(size: 11, weight: .bold, design: .monospaced))
                        .foregroundColor(Theme.textPrimary)
                    Text("ISSUE DATE: \(cert.issueDateFormatted)")
                        .font(.system(size: 10))
                        .foregroundColor(Theme.textMuted)
                    Text("AUTHENTICITY: epa608prep.com/verify")
                        .font(.system(size: 10, weight: .bold))
                        .foregroundColor(Theme.cyanAccent)
                }
                
                Spacer()
                
                // Simulated QR Code Graphic
                simulatedQrCode
            }
            
            // Signatures
            HStack {
                VStack(spacing: 2) {
                    Text("Marcus Vance, Ph.D.")
                        .font(.system(size: 12, weight: .bold, design: .serif))
                        .italic()
                        .foregroundColor(Theme.textPrimary)
                    Rectangle()
                        .fill(Theme.textMuted)
                        .frame(width: 110, height: 1)
                    Text("Director of Psychometrics")
                        .font(.system(size: 9))
                        .foregroundColor(Theme.textMuted)
                }
                
                Spacer()
                
                VStack(spacing: 2) {
                    Text("Sarah Jenkins, CM")
                        .font(.system(size: 12, weight: .bold, design: .serif))
                        .italic()
                        .foregroundColor(Theme.textPrimary)
                    Rectangle()
                        .fill(Theme.textMuted)
                        .frame(width: 110, height: 1)
                    Text("National HVAC/R Proctor")
                        .font(.system(size: 9))
                        .foregroundColor(Theme.textMuted)
                }
            }
            .padding(.top, 4)
        }
        .padding(20)
        .background(engine.isRooftopMode ? Color.white : Theme.cardSurface)
        .cornerRadius(16)
        .overlay(
            RoundedRectangle(cornerRadius: 16)
                .stroke(engine.isRooftopMode ? Theme.cardBorder : Theme.safetyAmber, lineWidth: 2)
        )
    }
    
    // MARK: - Score Badge
    private func scoreBadge(title: String, score: Double) -> some View {
        VStack(spacing: 2) {
            Text(title)
                .font(.system(size: 9, weight: .bold))
                .foregroundColor(Theme.textMuted)
            Text("\(Int(score))%")
                .font(.system(size: 15, weight: .black))
                .foregroundColor(Theme.passGreen)
            Text("PASS")
                .font(.system(size: 8, weight: .bold))
                .foregroundColor(Theme.passGreen)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 8)
        .background(Theme.cardSurface)
        .cornerRadius(8)
        .overlay(RoundedRectangle(cornerRadius: 8).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Simulated QR Code
    private var simulatedQrCode: some View {
        ZStack {
            RoundedRectangle(cornerRadius: 4)
                .stroke(Theme.textPrimary, lineWidth: 1.5)
                .frame(width: 50, height: 50)
            
            VStack(spacing: 3) {
                HStack(spacing: 3) {
                    Rectangle().fill(Theme.textPrimary).frame(width: 12, height: 12)
                    Spacer()
                    Rectangle().fill(Theme.textPrimary).frame(width: 12, height: 12)
                }
                HStack(spacing: 3) {
                    Rectangle().fill(Theme.textPrimary).frame(width: 12, height: 12)
                    Spacer()
                    Circle().fill(Theme.cyanAccent).frame(width: 8, height: 8)
                }
            }
            .padding(5)
            .frame(width: 50, height: 50)
        }
    }
    
    // MARK: - Action Buttons
    private var actionButtons: some View {
        let cert = engine.getCertificateData()
        
        return VStack(spacing: 10) {
            ShareLink(
                item: cert.shareableText,
                subject: Text("Official EPA Section 608 Universal Readiness Certificate"),
                message: Text(cert.shareableText)
            ) {
                HStack(spacing: 8) {
                    Image(systemName: "square.and.arrow.up")
                        .font(.system(size: 17, weight: .bold))
                    Text("Share Certificate (LinkedIn / Employer)")
                        .font(.system(size: 15, weight: .bold))
                }
                .foregroundColor(.black)
                .frame(maxWidth: .infinity)
                .frame(height: 52)
                .background(Theme.cyanAccent)
                .cornerRadius(12)
            }
            .minimumTouchTarget()
            
            HStack(spacing: 10) {
                Button {
                    Haptic.tap()
                    UIPasteboard.general.string = cert.verificationUrl
                    withAnimation { showCopiedToast = true }
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                        withAnimation { showCopiedToast = false }
                    }
                } label: {
                    HStack(spacing: 6) {
                        Image(systemName: "doc.on.doc")
                        Text("Copy Link")
                    }
                    .font(.system(size: 13, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Theme.cardSurface)
                    .cornerRadius(10)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Theme.cardBorder, lineWidth: 1))
                }
                .minimumTouchTarget()
                
                Button {
                    Haptic.tap()
                    withAnimation { showCopiedToast = true }
                    DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
                        withAnimation { showCopiedToast = false }
                    }
                } label: {
                    HStack(spacing: 6) {
                        Image(systemName: "arrow.down.doc")
                        Text("Save to Photos")
                    }
                    .font(.system(size: 13, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                    .frame(maxWidth: .infinity)
                    .frame(height: 48)
                    .background(Theme.cardSurface)
                    .cornerRadius(10)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Theme.cardBorder, lineWidth: 1))
                }
                .minimumTouchTarget()
            }
        }
    }
}
