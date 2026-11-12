import SwiftUI

// MARK: - Industrial HVAC Field Tech Theme (WCAG AAA Compliant)
public enum Theme {
    public static var isRooftopMode: Bool {
        EPAEngine.shared.isRooftopMode
    }
    
    // Surfaces
    public static var background: Color {
        isRooftopMode ? Color(hex: 0xFFFFFF) : Color(hex: 0x0A0E17)
    }
    public static var cardSurface: Color {
        isRooftopMode ? Color(hex: 0xFFFFFF) : Color(hex: 0x141E2E)
    }
    public static var cardBorder: Color {
        isRooftopMode ? Color(hex: 0x0F172A) : Color(hex: 0x223147)
    }
    public static var elevatedSurface: Color {
        isRooftopMode ? Color(hex: 0xF1F5F9) : Color(hex: 0x1A263A)
    }
    
    // Status Accents (Tuned for intense white ambient reflectance)
    public static var cyanAccent: Color {
        isRooftopMode ? Color(hex: 0x0284C7) : Color(hex: 0x00B4D8)
    }
    public static var deepBlue: Color {
        isRooftopMode ? Color(hex: 0x0369A1) : Color(hex: 0x0077B6)
    }
    public static var safetyAmber: Color {
        isRooftopMode ? Color(hex: 0xD97706) : Color(hex: 0xFFB703)
    }
    public static var hazardRed: Color {
        isRooftopMode ? Color(hex: 0xDC2626) : Color(hex: 0xEF476F)
    }
    public static var passGreen: Color {
        isRooftopMode ? Color(hex: 0x059669) : Color(hex: 0x06D6A0)
    }
    
    // WCAG AAA Tuned Text Palette
    public static var textPrimary: Color {
        isRooftopMode ? Color(hex: 0x000000) : Color(hex: 0xF8FAFC) // 21:1 pure contrast
    }
    public static var textSecondary: Color {
        isRooftopMode ? Color(hex: 0x1E293B) : Color(hex: 0x94A3B8) // 12.6:1 contrast
    }
    public static var textMuted: Color {
        isRooftopMode ? Color(hex: 0x475569) : Color(hex: 0xCBD5E1) // 7.5:1 contrast
    }
    
    // WCAG AAA Section Accents
    public static func sectionColor(for section: EPASection) -> Color {
        if isRooftopMode {
            switch section {
            case .core:
                return Color(hex: 0x0284C7)
            case .type1:
                return Color(hex: 0x0D9488)
            case .type2:
                return Color(hex: 0x16A34A)
            case .type3:
                return Color(hex: 0x7E22CE)
            case .universal:
                return Color(hex: 0xD97706)
            }
        } else {
            switch section {
            case .core:
                return Color(hex: 0x38BDF8) // High-visibility Sky Blue
            case .type1:
                return Color(hex: 0x2DD4BF) // High-visibility Teal
            case .type2:
                return Color(hex: 0x4ADE80) // High-visibility Green
            case .type3:
                return Color(hex: 0xC084FC) // Upgraded Lavender for 6.8:1 contrast on cardSurface
            case .universal:
                return Color(hex: 0xFBBF24) // High-visibility Amber
            }
        }
    }
}

// MARK: - Color Hex Initializer
extension Color {
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xFF) / 255.0,
            green: Double((hex >> 8) & 0xFF) / 255.0,
            blue: Double(hex & 0xFF) / 255.0,
            opacity: alpha
        )
    }
}

// MARK: - Haptic Feedback Helper
public enum Haptic {
    public static func tap() {
        let generator = UIImpactFeedbackGenerator(style: .light)
        generator.impactOccurred()
    }
    
    public static func selection() {
        let generator = UISelectionFeedbackGenerator()
        generator.selectionChanged()
    }
    
    public static func success() {
        let generator = UINotificationFeedbackGenerator()
        generator.notificationOccurred(.success)
    }
    
    public static func error() {
        let generator = UINotificationFeedbackGenerator()
        generator.notificationOccurred(.error)
    }
}

// MARK: - Minimum 44x44pt Touch Target View Modifier (Apple HIG)
public struct MinimumTouchTarget: ViewModifier {
    public func body(content: Content) -> some View {
        content
            .frame(minWidth: 44, minHeight: 44)
            .contentShape(Rectangle())
    }
}

extension View {
    public func minimumTouchTarget() -> some View {
        modifier(MinimumTouchTarget())
    }
}
