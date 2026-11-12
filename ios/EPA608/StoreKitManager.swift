import Foundation
import StoreKit
import SwiftUI

// MARK: - StoreKit 2 Modern Monetization Manager
@Observable
public final class StoreKitManager {
    public static let shared = StoreKitManager()
    
    // Product Identifiers
    public static let lifetimeID = "com.epa608.hvac.lifetime"
    public static let annualID = "com.epa608.hvac.annual"
    public static let monthlyID = "com.epa608.hvac.monthly"
    
    public private(set) var products: [Product] = []
    public private(set) var isProUser: Bool = false
    public private(set) var purchasedProductIDs = Set<String>()
    
    private var transactionListener: Task<Void, Error>?
    
    public init() {
        transactionListener = listenForTransactions()
        Task {
            await requestProducts()
            await updateCustomerProductStatus()
        }
    }
    
    deinit {
        transactionListener?.cancel()
    }
    
    // MARK: - Fetch Products
    @MainActor
    public func requestProducts() async {
        do {
            let productIDs = [Self.lifetimeID, Self.annualID, Self.monthlyID]
            let fetchedProducts = try await Product.products(for: productIDs)
            self.products = fetchedProducts.sorted { $0.price < $1.price }
        } catch {
            print("StoreKit: Failed product request: \(error.localizedDescription)")
        }
    }
    
    // MARK: - Purchase Product
    @MainActor
    public func purchase(_ product: Product) async throws -> Bool {
        let result = try await product.purchase()
        switch result {
        case .success(let verification):
            let transaction = try checkVerified(verification)
            await updateCustomerProductStatus()
            await transaction.finish()
            Haptic.success()
            return true
        case .userCancelled:
            return false
        case .pending:
            return false
        @unknown default:
            return false
        }
    }
    
    // MARK: - Restore Purchases
    @MainActor
    public func restorePurchases() async {
        try? await AppStore.sync()
        await updateCustomerProductStatus()
    }
    
    // MARK: - Update Entitlements
    @MainActor
    public func updateCustomerProductStatus() async {
        var purchased = Set<String>()
        for await result in Transaction.currentEntitlements {
            do {
                let transaction = try checkVerified(result)
                purchased.insert(transaction.productID)
            } catch {
                print("StoreKit verification failure")
            }
        }
        self.purchasedProductIDs = purchased
        self.isProUser = !purchased.isEmpty
    }
    
    // MARK: - Transaction Verification
    private func checkVerified<T>(_ result: VerificationResult<T>) throws -> T {
        switch result {
        case .unverified:
            throw StoreKitError.failedVerification
        case .verified(let safe):
            return safe
        }
    }
    
    // MARK: - Background Listener
    private func listenForTransactions() -> Task<Void, Error> {
        return Task.detached {
            for await result in Transaction.updates {
                do {
                    let transaction = try self.checkVerified(result)
                    await self.updateCustomerProductStatus()
                    await transaction.finish()
                } catch {
                    print("Transaction failed verification")
                }
            }
        }
    }
    
    public enum StoreKitError: LocalizedError {
        case failedVerification
        public var errorDescription: String? {
            "StoreKit transaction verification failed."
        }
    }
}
