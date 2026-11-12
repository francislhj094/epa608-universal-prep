import SwiftUI
import StoreKit

public struct PaywallView: View {
    @Environment(\.dismiss) private var dismiss
    private let storeKit = StoreKitManager.shared
    
    @State private var selectedProductID: String = StoreKitManager.lifetimeID
    @State private var isPurchasing: Bool = false
    @State private var errorAlertMessage: String?
    @State private var showErrorAlert: Bool = false
    
    public init() {}
    
    private var selectedProduct: Product? {
        storeKit.products.first(where: { $0.id == selectedProductID })
    }
    
    public var body: some View {
        ZStack {
            Theme.background.ignoresSafeArea()
            
            ScrollView {
                VStack(spacing: 24) {
                    // Close Button with 44x44 minimum touch target
                    HStack {
                        Spacer()
                        Button {
                            Haptic.tap()
                            dismiss()
                        } label: {
                            Image(systemName: "xmark.circle.fill")
                                .font(.system(size: 26))
                                .foregroundColor(Theme.textSecondary)
                        }
                        .minimumTouchTarget()
                    }
                    .padding(.horizontal, 20)
                    .padding(.top, 16)
                    
                    // Hero Shield
                    VStack(spacing: 12) {
                        ZStack {
                            Circle()
                                .fill(Theme.cyanAccent.opacity(0.12))
                                .frame(width: 80, height: 80)
                            Image(systemName: "shield.checkered")
                                .font(.system(size: 40))
                                .foregroundColor(Theme.cyanAccent)
                        }
                        
                        Text("100% PASS GUARANTEE")
                            .font(.system(size: 13, weight: .bold))
                            .foregroundColor(Theme.safetyAmber)
                            .tracking(2)
                        
                        Text("Pass Your EPA 608 Exam.\nFirst Attempt Guaranteed.")
                            .font(.system(size: 26, weight: .black))
                            .foregroundColor(Theme.textPrimary)
                            .multilineTextAlignment(.center)
                            .lineSpacing(4)
                        
                        Text("Updated for the 2026/2027 AIM Act with mandatory A2L refrigerant safety protocols.")
                            .font(.system(size: 15))
                            .foregroundColor(Theme.textSecondary)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal, 20)
                    }
                    
                    // Feature Rows
                    VStack(spacing: 14) {
                        featureRow(icon: "checkmark.seal.fill", title: "650+ Verified 2027 Questions", desc: "Core, Type I, Type II, and Type III master banks.")
                        featureRow(icon: "flame.fill", title: "A2L Refrigerants Mastered", desc: "Covers R-454B & R-32 flammability and DOT cylinder rules.")
                        featureRow(icon: "timer", title: "Realistic 80-Question Exam Simulator", desc: "Simulates proctored testing conditions with 70% passing cutoffs.")
                        featureRow(icon: "dollarsign.arrow.circlepath", title: "100% Tax Deductible Tool", desc: "Expenseable under IRC Section 162 or employer tool allowances.")
                    }
                    .padding(.horizontal, 20)
                    
                    // Dynamic Pricing Cards (StoreKit 2 Loaded)
                    VStack(spacing: 12) {
                        if storeKit.products.isEmpty {
                            // Fallback Display Cards during initial load or offline
                            fallbackPricingCard(id: StoreKitManager.lifetimeID, title: "Universal Lifetime Access", subtitle: "One-time purchase · Own it forever", price: "$59.99", badge: "MOST POPULAR", isBestValue: true)
                            fallbackPricingCard(id: StoreKitManager.annualID, title: "Annual Pass", subtitle: "3-Day Free Trial · Then $49.99/yr", price: "$49.99/yr", badge: "FREE TRIAL", isBestValue: false)
                            fallbackPricingCard(id: StoreKitManager.monthlyID, title: "Monthly Pass", subtitle: "Billed monthly · Cancel anytime", price: "$19.99/mo", badge: nil, isBestValue: false)
                        } else {
                            ForEach(storeKit.products) { product in
                                dynamicPricingCard(for: product)
                            }
                        }
                    }
                    .padding(.horizontal, 20)
                    
                    // Call to Action
                    VStack(spacing: 12) {
                        Button {
                            Haptic.tap()
                            initiatePurchase()
                        } label: {
                            HStack {
                                if isPurchasing {
                                    ProgressView().tint(.black)
                                } else {
                                    Text(ctaButtonText)
                                        .font(.system(size: 18, weight: .bold))
                                        .foregroundColor(.black)
                                }
                            }
                            .frame(maxWidth: .infinity)
                            .frame(height: 56)
                            .background(Theme.cyanAccent)
                            .cornerRadius(14)
                        }
                        .disabled(isPurchasing)
                        
                        // Guarantee Terms Notice
                        Text("Pass Guarantee Protection: Complete 100% of the prep bank, and if you don't pass your EPA 608 exam, submit your score sheet for full guarantee verification subject to terms.")
                            .font(.system(size: 11))
                            .foregroundColor(Theme.textSecondary)
                            .multilineTextAlignment(.center)
                            .padding(.horizontal, 20)
                        
                        // Mandatory App Store Guideline 3.1.2 Subscriptions Disclosure
                        VStack(spacing: 8) {
                            Text("Payment will be charged to your Apple ID account at confirmation of purchase. Subscriptions automatically renew unless canceled at least 24 hours prior to the end of the current period. Manage or cancel subscriptions in your App Store Account Settings.")
                                .font(.system(size: 11))
                                .foregroundColor(Theme.textSecondary)
                                .multilineTextAlignment(.center)
                            
                            HStack(spacing: 14) {
                                Button("Restore Purchases") {
                                    Task { await storeKit.restorePurchases() }
                                }
                                Text("•")
                                Link("Privacy Policy", destination: URL(string: "https://epa608prep.com/privacy.html")!)
                                Text("•")
                                Link("Terms of Use (EULA)", destination: URL(string: "https://www.apple.com/legal/internet-services/itunes/dev/stdeula/")!)
                            }
                            .font(.system(size: 12, weight: .semibold))
                            .foregroundColor(Theme.textSecondary)
                        }
                        .padding(.horizontal, 16)
                        .padding(.top, 4)
                    }
                    .padding(.horizontal, 20)
                    .padding(.bottom, 32)
                }
            }
        }
        .alert("Purchase Notice", isPresented: $showErrorAlert) {
            Button("OK", role: .cancel) {}
        } message: {
            Text(errorAlertMessage ?? "Unable to complete purchase. Check your internet connection.")
        }
    }
    
    private var ctaButtonText: String {
        switch selectedProductID {
        case StoreKitManager.annualID:
            return "Start 3-Day Free Trial →"
        case StoreKitManager.monthlyID:
            return "Start Monthly Subscription →"
        default:
            return "Unlock Universal Lifetime Access →"
        }
    }
    
    private func featureRow(icon: String, title: String, desc: String) -> some View {
        HStack(alignment: .top, spacing: 14) {
            Image(systemName: icon)
                .font(.system(size: 18))
                .foregroundColor(Theme.cyanAccent)
                .frame(width: 24)
                .padding(.top, 2)
            
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .font(.system(size: 15, weight: .bold))
                    .foregroundColor(Theme.textPrimary)
                Text(desc)
                    .font(.system(size: 13))
                    .foregroundColor(Theme.textSecondary)
            }
            Spacer()
        }
    }
    
    private func dynamicPricingCard(for product: Product) -> some View {
        let isSelected = selectedProductID == product.id
        let isLifetime = product.id == StoreKitManager.lifetimeID
        let isAnnual = product.id == StoreKitManager.annualID
        let badge: String? = isLifetime ? "MOST POPULAR" : (isAnnual ? "3-DAY TRIAL" : nil)
        
        return Button {
            Haptic.selection()
            selectedProductID = product.id
        } label: {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    if let badge = badge {
                        Text(badge)
                            .font(.system(size: 10, weight: .black))
                            .padding(.horizontal, 8)
                            .padding(.vertical, 3)
                            .background(isLifetime ? Theme.safetyAmber : Theme.cyanAccent)
                            .foregroundColor(.black)
                            .cornerRadius(6)
                    }
                    Text(product.displayName)
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Text(product.description)
                        .font(.system(size: 13))
                        .foregroundColor(Theme.textSecondary)
                }
                Spacer()
                Text(product.displayPrice)
                    .font(.system(size: 19, weight: .black, design: .monospaced))
                    .foregroundColor(isSelected ? Theme.cyanAccent : Theme.textPrimary)
            }
            .padding(16)
            .background(isSelected ? Theme.elevatedSurface : Theme.cardSurface)
            .cornerRadius(14)
            .overlay(
                RoundedRectangle(cornerRadius: 14)
                    .stroke(isSelected ? Theme.cyanAccent : Theme.cardBorder, lineWidth: isSelected ? 2 : 1)
            )
        }
    }
    
    private func fallbackPricingCard(id: String, title: String, subtitle: String, price: String, badge: String?, isBestValue: Bool) -> some View {
        let isSelected = selectedProductID == id
        return Button {
            Haptic.selection()
            selectedProductID = id
        } label: {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    if let badge = badge {
                        Text(badge)
                            .font(.system(size: 10, weight: .black))
                            .padding(.horizontal, 8)
                            .padding(.vertical, 3)
                            .background(isBestValue ? Theme.safetyAmber : Theme.cyanAccent)
                            .foregroundColor(.black)
                            .cornerRadius(6)
                    }
                    Text(title)
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(Theme.textPrimary)
                    Text(subtitle)
                        .font(.system(size: 13))
                        .foregroundColor(Theme.textSecondary)
                }
                Spacer()
                Text(price)
                    .font(.system(size: 19, weight: .black, design: .monospaced))
                    .foregroundColor(isSelected ? Theme.cyanAccent : Theme.textPrimary)
            }
            .padding(16)
            .background(isSelected ? Theme.elevatedSurface : Theme.cardSurface)
            .cornerRadius(14)
            .overlay(
                RoundedRectangle(cornerRadius: 14)
                    .stroke(isSelected ? Theme.cyanAccent : Theme.cardBorder, lineWidth: isSelected ? 2 : 1)
            )
        }
    }
    
    private func initiatePurchase() {
        guard let product = selectedProduct else {
            errorAlertMessage = "App Store catalog unavailable. Please verify internet connection."
            showErrorAlert = true
            return
        }
        
        isPurchasing = true
        Task {
            do {
                let success = try await storeKit.purchase(product)
                isPurchasing = false
                if success {
                    dismiss()
                }
            } catch {
                isPurchasing = false
                errorAlertMessage = error.localizedDescription
                showErrorAlert = true
            }
        }
    }
}
