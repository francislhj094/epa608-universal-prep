import SwiftUI

@main
struct EPA608App: App {
    @State private var storeKit = StoreKitManager.shared
    @State private var engine = EPAEngine.shared
    
    var body: some Scene {
        WindowGroup {
            ContentView()
                .preferredColorScheme(.dark)
                .task {
                    await storeKit.requestProducts()
                    await storeKit.updateCustomerProductStatus()
                }
        }
    }
}
