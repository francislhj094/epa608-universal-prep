import SwiftUI

public struct ContentView: View {
    @State private var selectedTab: Int = 0
    @State private var showDiagnosticOnboarding: Bool = false
    private let engine = EPAEngine.shared
    
    public init() {}
    
    public var body: some View {
        TabView(selection: $selectedTab) {
            DashboardView()
                .tabItem {
                    Label("Study", systemImage: "book.fill")
                }
                .tag(0)
            
            ExamSimulatorView()
                .tabItem {
                    Label("Exam Sim", systemImage: "doc.text.fill")
                }
                .tag(1)
            
            FieldToolsView()
                .tabItem {
                    Label("Field Tools", systemImage: "wrench.and.screwdriver.fill")
                }
                .tag(2)
            
            SettingsView()
                .tabItem {
                    Label("Profile", systemImage: "gearshape.fill")
                }
                .tag(3)
        }
        .tint(Theme.cyanAccent)
        .onAppear {
            if !engine.hasCompletedDiagnostic {
                showDiagnosticOnboarding = true
            }
        }
        .fullScreenCover(isPresented: $showDiagnosticOnboarding) {
            DiagnosticOnboardingView(isPresented: $showDiagnosticOnboarding)
        }
    }
}
