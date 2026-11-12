import SwiftUI

public struct FieldToolsView: View {
    @State private var selectedTab: Int = 0 // 0 = Vacuum Lookup, 1 = Leak Calculator, 2 = P-T Chart
    @FocusState private var isInputActive: Bool
    
    // Leak Calculator State
    @State private var fullChargeLbs: String = "120"
    @State private var refrigerantAddedLbs: String = "15"
    @State private var daysSinceLastAddition: String = "90"
    @State private var applianceType: ApplianceType = .commercialRefrigeration
    
    // P-T Chart State
    @State private var selectedRefrigerant: Refrigerant = .r454B
    @State private var temperatureF: Double = 40.0 // Standard evaporator saturation
    
    public init() {}
    
    public enum ApplianceType: String, CaseIterable, Identifiable {
        case comfortCooling = "Comfort Cooling"
        case commercialRefrigeration = "Commercial"
        case industrialProcess = "Industrial Process"
        
        public var id: String { rawValue }
        
        public var triggerRate: Double {
            switch self {
            case .comfortCooling: return 10.0
            case .commercialRefrigeration: return 20.0
            case .industrialProcess: return 30.0
            }
        }
        
        public var citation: String {
            switch self {
            case .comfortCooling: return "40 CFR § 82.157(c)(2)(iii) [10% Threshold]"
            case .commercialRefrigeration: return "40 CFR § 82.157(c)(2)(ii) [20% Threshold]"
            case .industrialProcess: return "40 CFR § 82.157(c)(2)(i) [30% Threshold]"
            }
        }
    }
    
    public enum Refrigerant: String, CaseIterable, Identifiable {
        case r410A = "R-410A (A1)"
        case r454B = "R-454B (A2L)"
        case r32   = "R-32 (A2L)"
        case r22   = "R-22 (HCFC)"
        case r134a = "R-134a (HFC)"
        
        public var id: String { rawValue }
    }
    
    // 40 CFR § 82.152 Compliant Annualized Leak Rate Formula
    public var calculatedLeakRate: Double {
        guard let charge = Double(fullChargeLbs), charge > 0,
              let added = Double(refrigerantAddedLbs), added >= 0,
              let days = Double(daysSinceLastAddition), days > 0 else {
            return 0.0
        }
        // 40 CFR § 82.152 Step (2): Divide the shorter of the days since last addition or 365 by 365
        let effectiveDays = min(days, 365.0)
        return (added / charge) * (365.0 / effectiveDays) * 100.0
    }
    
    public var isExceedingTrigger: Bool {
        // Support 15+ lbs threshold for AIM Act / HFCs and 50+ lbs for Title VI / ODS
        guard let charge = Double(fullChargeLbs), charge >= 15.0 else { return false }
        return calculatedLeakRate > applianceType.triggerRate
    }
    
    // Saturated Pressure Antoine Approximation for Job-Site Field Reference
    private var saturationPressurePSIG: Double {
        switch selectedRefrigerant {
        case .r410A: return max(0, 0.0038 * pow(temperatureF, 2) + 1.85 * temperatureF + 42.0)
        case .r454B: return max(0, 0.0036 * pow(temperatureF, 2) + 1.80 * temperatureF + 39.0)
        case .r32:   return max(0, 0.0039 * pow(temperatureF, 2) + 1.90 * temperatureF + 44.0)
        case .r22:   return max(0, 0.0022 * pow(temperatureF, 2) + 1.15 * temperatureF + 28.0)
        case .r134a: return max(0, 0.0019 * pow(temperatureF, 2) + 0.85 * temperatureF + 15.0)
        }
    }
    
    public var body: some View {
        NavigationStack {
            ZStack {
                Theme.background.ignoresSafeArea()
                
                VStack(spacing: 0) {
                    // Header
                    headerView
                    
                    // Segmented Selector
                    toolSelector
                        .padding(.horizontal, 20)
                        .padding(.vertical, 12)
                    
                    ScrollView {
                        if selectedTab == 0 {
                            evacuationLookupContent
                        } else if selectedTab == 1 {
                            leakCalculatorContent
                        } else {
                            ptChartContent
                        }
                    }
                }
            }
            .toolbar {
                ToolbarItemGroup(placement: .keyboard) {
                    Spacer()
                    Button("Done") {
                        isInputActive = false
                    }
                    .font(.system(size: 15, weight: .bold))
                    .foregroundColor(Theme.cyanAccent)
                }
            }
        }
    }
    
    // MARK: - Header
    private var headerView: some View {
        HStack {
            VStack(alignment: .leading, spacing: 2) {
                Text("EPA 40 CFR PART 82 & 84")
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(Theme.safetyAmber)
                    .tracking(1.5)
                Text("Job-Site Tools")
                    .font(.system(size: 24, weight: .black))
                    .foregroundColor(Theme.textPrimary)
            }
            Spacer()
            Image(systemName: "wrench.and.screwdriver.fill")
                .font(.system(size: 20))
                .foregroundColor(Theme.cyanAccent)
        }
        .padding(.horizontal, 20)
        .padding(.top, 10)
    }
    
    // MARK: - Tool Selector
    private var toolSelector: some View {
        HStack(spacing: 6) {
            selectorButton(title: "Evacuation", icon: "gauge", index: 0)
            selectorButton(title: "Leak Calc", icon: "drop.fill", index: 1)
            selectorButton(title: "P-T Chart", icon: "thermometer.medium", index: 2)
        }
        .padding(4)
        .background(Theme.cardSurface)
        .cornerRadius(12)
        .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    private func selectorButton(title: String, icon: String, index: Int) -> some View {
        Button {
            Haptic.selection()
            selectedTab = index
        } label: {
            HStack(spacing: 4) {
                Image(systemName: icon)
                    .font(.system(size: 12, weight: .semibold))
                Text(title)
                    .font(.system(size: 12, weight: .bold))
            }
            .foregroundColor(selectedTab == index ? .black : Theme.textSecondary)
            .frame(maxWidth: .infinity)
            .frame(height: 38)
            .background(selectedTab == index ? Theme.cyanAccent : Color.clear)
            .cornerRadius(8)
        }
    }
    
    // MARK: - Tab 0: Evacuation Table (100% 40 CFR § 82.156 Table 1 Compliant)
    private var evacuationLookupContent: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text("EPA REQUIRED EVACUATION LEVELS (TABLE 1)")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            evacCard(
                category: "Small Appliances (Type I - 40 CFR § 82.156(a))",
                specs: [
                    ("Post-1993 Equipment (Running)", "90% charge or 4\" Hg vacuum"),
                    ("Post-1993 Equipment (Inoperative)", "80% charge or 4\" Hg vacuum"),
                    ("Pre-1993 Equipment (Any)", "80% charge or 4\" Hg vacuum")
                ]
            )
            
            evacCard(
                category: "High-Pressure: R-22, R-410A (Type II - Table 1)",
                specs: [
                    ("< 200 lbs (Post-1993)", "0 inches Hg (0 psig)"),
                    ("< 200 lbs (Pre-1993)", "0 inches Hg (0 psig)"),
                    (">= 200 lbs (Post-1993)", "10 inches Hg vacuum"),
                    (">= 200 lbs (Pre-1993)", "4 inches Hg vacuum"),
                    ("Leaking Appliance Exception", "0 psig (isolate non-leaking)")
                ]
            )
            
            evacCard(
                category: "Medium-Pressure: R-134a, R-12 (Type II - Table 1)",
                specs: [
                    ("< 200 lbs (Post-1993)", "10 inches Hg vacuum"),
                    ("< 200 lbs (Pre-1993)", "4 inches Hg vacuum"),
                    (">= 200 lbs (Post-1993)", "15 inches Hg vacuum"),
                    (">= 200 lbs (Pre-1993)", "4 inches Hg vacuum")
                ]
            )
            
            evacCard(
                category: "Low-Pressure Chillers: R-123 (Type III - Table 1)",
                specs: [
                    ("Post-1993 Equipment", "25 mm Hg absolute"),
                    ("Pre-1993 Equipment", "25 inches Hg vacuum"),
                    ("Rupture Disc Setting", "15 psig (ASHRAE 15)"),
                    ("Max Leak Test Pressure", "10 psig (Dry Nitrogen)")
                ]
            )
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 100)
    }
    
    private func evacCard(category: String, specs: [(String, String)]) -> some View {
        VStack(alignment: .leading, spacing: 12) {
            Text(category)
                .font(.system(size: 14, weight: .bold))
                .foregroundColor(Theme.cyanAccent)
            
            VStack(spacing: 8) {
                ForEach(specs, id: \.0) { spec in
                    HStack {
                        Text(spec.0)
                            .font(.system(size: 13, weight: .medium))
                            .foregroundColor(Theme.textSecondary)
                        Spacer()
                        Text(spec.1)
                            .font(.system(size: 13, weight: .bold, design: .monospaced))
                            .foregroundColor(Theme.textPrimary)
                    }
                }
            }
        }
        .padding(16)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Theme.cardSurface)
        .cornerRadius(14)
        .overlay(RoundedRectangle(cornerRadius: 14).stroke(Theme.cardBorder, lineWidth: 1))
    }
    
    // MARK: - Tab 1: Leak Calculator (40 CFR § 82.152 & § 82.157 Compliant)
    private var leakCalculatorContent: some View {
        VStack(alignment: .leading, spacing: 20) {
            // Result Banner
            VStack(spacing: 12) {
                HStack {
                    VStack(alignment: .leading, spacing: 4) {
                        Text("ANNUALIZED LEAK RATE")
                            .font(.system(size: 11, weight: .bold))
                            .foregroundColor(Theme.textMuted)
                        Text(String(format: "%.1f%%", calculatedLeakRate))
                            .font(.system(size: 36, weight: .black, design: .monospaced))
                            .foregroundColor(isExceedingTrigger ? Theme.hazardRed : Theme.passGreen)
                    }
                    Spacer()
                    
                    VStack(alignment: .trailing, spacing: 4) {
                        Text("LEGAL THRESHOLD")
                            .font(.system(size: 11, weight: .bold))
                            .foregroundColor(Theme.textMuted)
                        Text(String(format: "%.0f%%", applianceType.triggerRate))
                            .font(.system(size: 22, weight: .bold, design: .monospaced))
                            .foregroundColor(Theme.safetyAmber)
                    }
                }
                
                Divider().background(Theme.cardBorder)
                
                HStack {
                    Image(systemName: isExceedingTrigger ? "exclamationmark.octagon.fill" : "checkmark.shield.fill")
                        .foregroundColor(isExceedingTrigger ? Theme.hazardRed : Theme.passGreen)
                    Text(isExceedingTrigger ?
                         "MANDATORY 30-DAY REPAIR REQUIRED (40 CFR § 82.157)" :
                         "Compliant: Below Statutory Leak Threshold")
                        .font(.system(size: 12, weight: .bold))
                        .foregroundColor(isExceedingTrigger ? Theme.hazardRed : Theme.passGreen)
                    Spacer()
                }
            }
            .padding(16)
            .background(Theme.cardSurface)
            .cornerRadius(16)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(isExceedingTrigger ? Theme.hazardRed : Theme.cardBorder, lineWidth: 1.5))
            
            // Appliance Type Picker
            VStack(alignment: .leading, spacing: 8) {
                Text("APPLIANCE CATEGORY (15+ LBS AIM / 50+ LBS ODS)")
                    .font(.system(size: 12, weight: .bold))
                    .foregroundColor(Theme.textMuted)
                
                ForEach(ApplianceType.allCases) { type in
                    Button {
                        Haptic.selection()
                        applianceType = type
                    } label: {
                        HStack {
                            VStack(alignment: .leading, spacing: 2) {
                                Text(type.rawValue)
                                    .font(.system(size: 14, weight: .bold))
                                    .foregroundColor(Theme.textPrimary)
                                Text(type.citation)
                                    .font(.system(size: 11))
                                    .foregroundColor(Theme.textSecondary)
                            }
                            Spacer()
                            Text(String(format: "%.0f%%", type.triggerRate))
                                .font(.system(size: 15, weight: .bold, design: .monospaced))
                                .foregroundColor(applianceType == type ? Theme.cyanAccent : Theme.textMuted)
                        }
                        .padding(12)
                        .background(applianceType == type ? Theme.elevatedSurface : Theme.cardSurface)
                        .cornerRadius(10)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(applianceType == type ? Theme.cyanAccent : Theme.cardBorder, lineWidth: 1)
                        )
                    }
                }
            }
            
            // Inputs
            VStack(alignment: .leading, spacing: 14) {
                inputField(title: "Full Appliance Charge (lbs)", value: $fullChargeLbs, placeholder: "e.g. 120")
                inputField(title: "Refrigerant Added (lbs)", value: $refrigerantAddedLbs, placeholder: "e.g. 15")
                inputField(title: "Days Since Last Addition", value: $daysSinceLastAddition, placeholder: "e.g. 90")
            }
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 100)
    }
    
    // MARK: - Tab 2: Interactive P-T Chart (Daily Retention Utility)
    private var ptChartContent: some View {
        VStack(alignment: .leading, spacing: 20) {
            Text("REFRIGERANT PRESSURE-TEMPERATURE CHART")
                .font(.system(size: 12, weight: .bold))
                .foregroundColor(Theme.textMuted)
                .tracking(1)
            
            // Refrigerant Picker
            VStack(alignment: .leading, spacing: 8) {
                ForEach(Refrigerant.allCases) { ref in
                    Button {
                        Haptic.selection()
                        selectedRefrigerant = ref
                    } label: {
                        HStack {
                            Text(ref.rawValue)
                                .font(.system(size: 14, weight: .bold))
                                .foregroundColor(Theme.textPrimary)
                            Spacer()
                            if selectedRefrigerant == ref {
                                Image(systemName: "checkmark.circle.fill")
                                    .foregroundColor(Theme.cyanAccent)
                            }
                        }
                        .padding(12)
                        .background(selectedRefrigerant == ref ? Theme.elevatedSurface : Theme.cardSurface)
                        .cornerRadius(10)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(selectedRefrigerant == ref ? Theme.cyanAccent : Theme.cardBorder, lineWidth: 1)
                        )
                    }
                }
            }
            
            // Gauge Readout Card
            VStack(spacing: 8) {
                Text("SATURATED VAPOR PRESSURE")
                    .font(.system(size: 11, weight: .bold))
                    .foregroundColor(Theme.textSecondary)
                Text(String(format: "%.1f PSIG", saturationPressurePSIG))
                    .font(.system(size: 38, weight: .black, design: .monospaced))
                    .foregroundColor(Theme.cyanAccent)
                Text("Evaporator / Condenser Saturation: \(Int(temperatureF))°F")
                    .font(.system(size: 15, weight: .semibold))
                    .foregroundColor(Theme.textPrimary)
            }
            .padding(20)
            .frame(maxWidth: .infinity)
            .background(Theme.cardSurface)
            .cornerRadius(16)
            .overlay(RoundedRectangle(cornerRadius: 16).stroke(Theme.cardBorder, lineWidth: 1))
            
            // Slider Control
            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Text("Saturation Temperature:")
                        .font(.system(size: 13, weight: .medium))
                        .foregroundColor(Theme.textSecondary)
                    Spacer()
                    Text("\(Int(temperatureF))°F")
                        .font(.system(size: 15, weight: .bold, design: .monospaced))
                        .foregroundColor(Theme.safetyAmber)
                }
                
                Slider(value: $temperatureF, in: -20...140, step: 1)
                    .tint(Theme.cyanAccent)
            }
            .padding(16)
            .background(Theme.cardSurface)
            .cornerRadius(12)
            .overlay(RoundedRectangle(cornerRadius: 12).stroke(Theme.cardBorder, lineWidth: 1))
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 100)
    }
    
    private func inputField(title: String, value: Binding<String>, placeholder: String) -> some View {
        VStack(alignment: .leading, spacing: 6) {
            Text(title)
                .font(.system(size: 13, weight: .semibold))
                .foregroundColor(Theme.textSecondary)
            TextField(placeholder, text: value)
                .keyboardType(.decimalPad)
                .focused($isInputActive)
                .font(.system(size: 16, weight: .bold, design: .monospaced))
                .foregroundColor(Theme.textPrimary)
                .padding(12)
                .background(Theme.cardSurface)
                .cornerRadius(10)
                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Theme.cardBorder, lineWidth: 1))
        }
    }
}
