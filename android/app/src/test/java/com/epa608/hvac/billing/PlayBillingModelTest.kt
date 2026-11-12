package com.epa608.hvac.billing

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit tests for Google Play Billing data structures, product SKU mappings,
 * fallback pricing models, and reactive billing states.
 */
class PlayBillingModelTest {

    @Test
    fun testProductIdentifiersAndAliases() {
        assertEquals("epa608_lifetime_5999", PlayBillingManager.PRODUCT_LIFETIME)
        assertEquals("epa608_annual_4999", PlayBillingManager.PRODUCT_ANNUAL)
        assertEquals("epa608_monthly_1999", PlayBillingManager.PRODUCT_MONTHLY)

        assertEquals("com.epa608.hvac.lifetime", PlayBillingManager.ALIAS_LIFETIME)
        assertEquals("com.epa608.hvac.annual", PlayBillingManager.ALIAS_ANNUAL)
        assertEquals("com.epa608.hvac.monthly", PlayBillingManager.ALIAS_MONTHLY)
    }

    @Test
    fun testFallbackPricingConstants() {
        assertEquals("$59.99", PlayBillingManager.FALLBACK_PRICE_LIFETIME)
        assertEquals("$49.99/yr", PlayBillingManager.FALLBACK_PRICE_ANNUAL)
        assertEquals("$19.99/mo", PlayBillingManager.FALLBACK_PRICE_MONTHLY)
    }

    @Test
    fun testBillingStateDefaults() {
        val defaultState = BillingState()
        assertFalse(defaultState.isProUser)
        assertTrue(defaultState.purchasedProductIds.isEmpty())
        assertTrue(defaultState.productDetailsMap.isEmpty())
        assertFalse(defaultState.isLoading)
        assertFalse(defaultState.isPurchasing)
        assertFalse(defaultState.isPending)
        assertNull(defaultState.connectionError)
    }

    @Test
    fun testBillingStateTransitions() {
        val state = BillingState(
            isProUser = true,
            purchasedProductIds = setOf(PlayBillingManager.PRODUCT_LIFETIME),
            isLoading = false
        )
        assertTrue(state.isProUser)
        assertTrue(state.purchasedProductIds.contains(PlayBillingManager.PRODUCT_LIFETIME))

        val updated = state.copy(isPurchasing = true)
        assertTrue(updated.isPurchasing)
        assertTrue(updated.isProUser)
    }

    @Test
    fun testBillingEvents() {
        val successEvent = BillingEvent.PurchaseSuccess(PlayBillingManager.PRODUCT_LIFETIME)
        assertEquals(PlayBillingManager.PRODUCT_LIFETIME, successEvent.productId)

        val pendingEvent = BillingEvent.PurchasePending(PlayBillingManager.PRODUCT_ANNUAL)
        assertEquals(PlayBillingManager.PRODUCT_ANNUAL, pendingEvent.productId)

        val errorEvent = BillingEvent.PurchaseError("Payment declined")
        assertEquals("Payment declined", errorEvent.message)

        val restoreEvent = BillingEvent.RestoreSuccess(2)
        assertEquals(2, restoreEvent.count)

        assertEquals(BillingEvent.PurchaseCanceled, BillingEvent.PurchaseCanceled)
        assertEquals(BillingEvent.ItemAlreadyOwned, BillingEvent.ItemAlreadyOwned)
        assertEquals(BillingEvent.RestoreEmpty, BillingEvent.RestoreEmpty)
    }
}
