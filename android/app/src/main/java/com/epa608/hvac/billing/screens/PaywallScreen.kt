package com.epa608.hvac.billing.screens

import android.app.Activity
import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.billing.BillingEvent
import com.epa608.hvac.billing.PlayBillingManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// MARK: - WCAG AAA Compliant Industrial Palette
private object PaywallTheme {
    val Background = Color(0xFF0A0E17)
    val CardSurface = Color(0xFF141E2E)
    val CardBorder = Color(0xFF223147)
    val ElevatedSurface = Color(0xFF1A263A)

    val CyanAccent = Color(0xFF00B4D8)
    val DeepBlue = Color(0xFF0077B6)
    val SafetyAmber = Color(0xFFFFB703)
    val HazardRed = Color(0xFFEF476F)
    val PassGreen = Color(0xFF06D6A0)

    val TextPrimary = Color(0xFFF8FAFC)
    val TextSecondary = Color(0xFF94A3B8)
    val TextMuted = Color(0xFFCBD5E1)
}

/**
 * Material 3 High-Converting Paywall Screen matching the iOS version.
 *
 * Implements:
 * 1. 100% Pass Guarantee hero shield with glowing circular aura.
 * 2. 4 high-value feature rows (650+ Questions, A2L Mastered, 80-Q Simulator, IRC 162 Deductible).
 * 3. Dynamic pricing cards querying Google Play Billing Library 7.0+ (Lifetime, Annual with 3-Day Trial, Monthly).
 * 4. Employer IRC Section 162 tax deductible note.
 * 5. One-click Restore Purchases button.
 * 6. 100% Google Play Policy compliance (Subscription disclosure, trial terms, auto-renew explanation, cancel link).
 */
@Composable
fun PaywallScreen(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val uriHandler = LocalUriHandler.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val billingManager = remember { PlayBillingManager.getInstance(context) }
    val billingState by billingManager.billingState.collectAsState()

    var selectedProductId by remember { mutableStateOf(PlayBillingManager.PRODUCT_LIFETIME) }
    var errorDialogMessage by remember { mutableStateOf<String?>(null) }

    // Listen for Billing Events (Success, Pending, Errors, Restore)
    LaunchedEffect(Unit) {
        billingManager.billingEvent.collectLatest { event ->
            when (event) {
                is BillingEvent.PurchaseSuccess -> {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Welcome to EPA 608 Universal Pro!")
                    }
                    onDismiss()
                }
                is BillingEvent.ItemAlreadyOwned -> {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Active Pro license verified!")
                    }
                    onDismiss()
                }
                is BillingEvent.PurchasePending -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Purchase pending. Pro access will unlock once Google Play completes processing.")
                    }
                }
                is BillingEvent.PurchaseCanceled -> {
                    // Silent dismissal of checkout sheet
                }
                is BillingEvent.PurchaseError -> {
                    errorDialogMessage = event.message
                }
                is BillingEvent.RestoreSuccess -> {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Success: ${event.count} active purchase(s) restored.")
                    }
                    onDismiss()
                }
                is BillingEvent.RestoreEmpty -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("No previous active purchases found on this Google Play account.")
                    }
                }
            }
        }
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PaywallTheme.Background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 32.dp)
        ) {
            // MARK: - Header Close Button (Accessible 48dp minimum touch target)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onDismiss()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(PaywallTheme.CardSurface)
                            .border(1.dp, PaywallTheme.CardBorder, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Paywall",
                            tint = PaywallTheme.TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // MARK: - Hero Shield & 100% Pass Guarantee Header
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Checkered Hero Shield Container
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(PaywallTheme.CyanAccent.copy(alpha = 0.12f))
                        .border(1.5.dp, PaywallTheme.CyanAccent.copy(alpha = 0.35f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    CheckeredShieldVectorIcon(
                        modifier = Modifier.size(42.dp),
                        color = PaywallTheme.CyanAccent
                    )
                }

                Text(
                    text = "100% PASS GUARANTEE",
                    color = PaywallTheme.SafetyAmber,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )

                Text(
                    text = "Pass Your EPA 608 Exam.\nFirst Attempt Guaranteed.",
                    color = PaywallTheme.TextPrimary,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 32.sp
                )

                Text(
                    text = "Updated for the 2026/2027 AIM Act with mandatory A2L refrigerant safety protocols.",
                    color = PaywallTheme.TextSecondary,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp,
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MARK: - Feature Rows
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                FeatureRow(
                    icon = { CheckmarkSealVectorIcon(modifier = Modifier.size(20.dp), color = PaywallTheme.CyanAccent) },
                    title = "650+ Verified 2027 Questions",
                    desc = "Core, Type I, Type II, and Type III master banks."
                )
                FeatureRow(
                    icon = { FlameVectorIcon(modifier = Modifier.size(20.dp), color = PaywallTheme.CyanAccent) },
                    title = "A2L Refrigerants Mastered",
                    desc = "Covers R-454B & R-32 flammability and DOT cylinder rules."
                )
                FeatureRow(
                    icon = { TimerVectorIcon(modifier = Modifier.size(20.dp), color = PaywallTheme.CyanAccent) },
                    title = "Realistic 80-Question Exam Simulator",
                    desc = "Simulates proctored testing conditions with 70% passing cutoffs."
                )
                FeatureRow(
                    icon = { TaxDollarVectorIcon(modifier = Modifier.size(20.dp), color = PaywallTheme.CyanAccent) },
                    title = "100% Tax Deductible Tool",
                    desc = "Expenseable under IRC Section 162 or employer tool allowances."
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MARK: - Dynamic Google Play Pricing Cards
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // 1. Lifetime Card (Most Popular)
                PricingCard(
                    productId = PlayBillingManager.PRODUCT_LIFETIME,
                    title = "Universal Lifetime Access",
                    subtitle = "One-time purchase · Own it forever",
                    price = billingManager.getFormattedPrice(PlayBillingManager.PRODUCT_LIFETIME),
                    badge = "MOST POPULAR",
                    badgeColor = PaywallTheme.SafetyAmber,
                    isSelected = selectedProductId == PlayBillingManager.PRODUCT_LIFETIME,
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        selectedProductId = PlayBillingManager.PRODUCT_LIFETIME
                    }
                )

                // 2. Annual Card (3-Day Free Trial)
                PricingCard(
                    productId = PlayBillingManager.PRODUCT_ANNUAL,
                    title = "Annual Pass",
                    subtitle = billingManager.getSubscriptionTrialDescription(PlayBillingManager.PRODUCT_ANNUAL)
                        ?: "3-Day Free Trial · Then $49.99/yr",
                    price = billingManager.getFormattedPrice(PlayBillingManager.PRODUCT_ANNUAL),
                    badge = "3-DAY TRIAL",
                    badgeColor = PaywallTheme.CyanAccent,
                    isSelected = selectedProductId == PlayBillingManager.PRODUCT_ANNUAL,
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        selectedProductId = PlayBillingManager.PRODUCT_ANNUAL
                    }
                )

                // 3. Monthly Card
                PricingCard(
                    productId = PlayBillingManager.PRODUCT_MONTHLY,
                    title = "Monthly Pass",
                    subtitle = "Billed monthly · Cancel anytime",
                    price = billingManager.getFormattedPrice(PlayBillingManager.PRODUCT_MONTHLY),
                    badge = null,
                    badgeColor = PaywallTheme.CyanAccent,
                    isSelected = selectedProductId == PlayBillingManager.PRODUCT_MONTHLY,
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        selectedProductId = PlayBillingManager.PRODUCT_MONTHLY
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // MARK: - Dynamic Call to Action Button
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val ctaText = when (selectedProductId) {
                    PlayBillingManager.PRODUCT_ANNUAL -> "Start 3-Day Free Trial →"
                    PlayBillingManager.PRODUCT_MONTHLY -> "Start Monthly Subscription →"
                    else -> "Unlock Universal Lifetime Access →"
                }

                Button(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        val activity = context as? Activity
                        if (activity != null) {
                            billingManager.launchBillingFlow(activity, selectedProductId)
                        } else {
                            errorDialogMessage = "Unable to launch Google Play billing window."
                        }
                    },
                    enabled = !billingState.isPurchasing,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PaywallTheme.CyanAccent,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    if (billingState.isPurchasing) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.Black,
                            strokeWidth = 2.5.dp
                        )
                    } else {
                        Text(
                            text = ctaText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // MARK: - Pass Guarantee Protection Notice
                Text(
                    text = "Pass Guarantee Protection: Complete 100% of the prep bank, and if you don't pass your EPA 608 exam, submit your score sheet for full guarantee verification subject to terms.",
                    color = PaywallTheme.TextSecondary,
                    fontSize = 11.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // MARK: - Employer IRC Section 162 Tax Deductible Callout
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(PaywallTheme.CardSurface)
                        .border(1.dp, PaywallTheme.CardBorder, RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        TaxDollarVectorIcon(
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp),
                            color = PaywallTheme.SafetyAmber
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = "IRC SECTION 162 TAX DEDUCTIBLE TOOL",
                                color = PaywallTheme.SafetyAmber,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Certification study materials and exam preparation for trade professionals qualify as ordinary and necessary business expenses under IRC Section 162, or are eligible for employer tool/training reimbursement.",
                                color = PaywallTheme.TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // MARK: - Mandatory Google Play Subscriptions Policy Disclosures
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Payment will be charged to your Google Play account at confirmation of purchase. Subscriptions automatically renew unless canceled at least 24 hours prior to the end of the current billing cycle. You may manage or cancel subscriptions anytime in Google Play Account Settings > Payments & subscriptions.",
                        color = PaywallTheme.TextSecondary,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 15.sp
                    )

                    // Footer Action Links (Restore, Privacy, Terms)
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Restore Purchases",
                            color = PaywallTheme.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                billingManager.restorePurchases()
                            }
                        )

                        Text(
                            text = "•",
                            color = PaywallTheme.TextSecondary,
                            fontSize = 12.sp
                        )

                        Text(
                            text = "Privacy Policy",
                            color = PaywallTheme.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                uriHandler.openUri("https://epa608prep.com/privacy.html")
                            }
                        )

                        Text(
                            text = "•",
                            color = PaywallTheme.TextSecondary,
                            fontSize = 12.sp
                        )

                        Text(
                            text = "Terms of Service",
                            color = PaywallTheme.TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable {
                                uriHandler.openUri("https://epa608prep.com/terms.html")
                            }
                        )
                    }
                }
            }
        }

        // Host for feedback notifications
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        // Error Dialog
        if (errorDialogMessage != null) {
            AlertDialog(
                onDismissRequest = { errorDialogMessage = null },
                title = {
                    Text(
                        text = "Purchase Notice",
                        fontWeight = FontWeight.Bold,
                        color = PaywallTheme.TextPrimary
                    )
                },
                text = {
                    Text(
                        text = errorDialogMessage ?: "Unable to complete purchase. Check internet connection.",
                        color = PaywallTheme.TextSecondary
                    )
                },
                confirmButton = {
                    TextButton(onClick = { errorDialogMessage = null }) {
                        Text("OK", color = PaywallTheme.CyanAccent)
                    }
                },
                containerColor = PaywallTheme.ElevatedSurface,
                shape = RoundedCornerShape(14.dp)
            )
        }
    }
}

// MARK: - Feature Row Component
@Composable
private fun FeatureRow(
    icon: @Composable () -> Unit,
    title: String,
    desc: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .padding(top = 2.dp),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = title,
                color = PaywallTheme.TextPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = desc,
                color = PaywallTheme.TextSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )
        }
    }
}

// MARK: - Pricing Card Component
@Composable
private fun PricingCard(
    productId: String,
    title: String,
    subtitle: String,
    price: String,
    badge: String?,
    badgeColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(if (isSelected) PaywallTheme.ElevatedSurface else PaywallTheme.CardSurface)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) PaywallTheme.CyanAccent else PaywallTheme.CardBorder,
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(badgeColor)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = badge,
                            color = Color.Black,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                }

                Text(
                    text = title,
                    color = PaywallTheme.TextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = subtitle,
                    color = PaywallTheme.TextSecondary,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = price,
                color = if (isSelected) PaywallTheme.CyanAccent else PaywallTheme.TextPrimary,
                fontSize = 19.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

// MARK: - Self-Contained Vector Icons for Field Tech Theme
@Composable
private fun CheckeredShieldVectorIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer Shield
        val shieldPath = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w, h * 0.22f)
            cubicTo(w, h * 0.68f, w * 0.5f, h * 0.96f, w * 0.5f, h)
            cubicTo(w * 0.5f, h * 0.96f, 0f, h * 0.68f, 0f, h * 0.22f)
            close()
        }
        drawPath(
            path = shieldPath,
            color = color,
            style = Stroke(width = w * 0.08f)
        )

        // Checkered Quadrants
        val halfW = w * 0.5f
        val halfH = h * 0.5f

        // Top Left quadrant fill
        val q1Path = Path().apply {
            moveTo(w * 0.15f, h * 0.25f)
            lineTo(halfW, h * 0.25f)
            lineTo(halfW, halfH)
            lineTo(w * 0.1f, halfH)
            close()
        }
        drawPath(path = q1Path, color = color.copy(alpha = 0.35f))

        // Bottom Right quadrant fill
        val q2Path = Path().apply {
            moveTo(halfW, halfH)
            lineTo(w * 0.85f, halfH)
            lineTo(halfW, h * 0.85f)
            close()
        }
        drawPath(path = q2Path, color = color.copy(alpha = 0.35f))

        // Dividing Cross
        drawLine(
            color = color,
            start = Offset(halfW, h * 0.1f),
            end = Offset(halfW, h * 0.88f),
            strokeWidth = w * 0.04f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(w * 0.1f, halfH),
            end = Offset(w * 0.9f, halfH),
            strokeWidth = w * 0.04f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun CheckmarkSealVectorIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        drawCircle(
            color = color.copy(alpha = 0.2f),
            radius = w * 0.45f
        )
        drawCircle(
            color = color,
            radius = w * 0.45f,
            style = Stroke(width = w * 0.09f)
        )

        val checkPath = Path().apply {
            moveTo(w * 0.3f, h * 0.5f)
            lineTo(w * 0.45f, h * 0.65f)
            lineTo(w * 0.72f, h * 0.35f)
        }
        drawPath(
            path = checkPath,
            color = color,
            style = Stroke(
                width = w * 0.1f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round
            )
        )
    }
}

@Composable
private fun TimerVectorIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer stopwatch ring
        drawCircle(
            color = color,
            radius = w * 0.4f,
            center = Offset(w * 0.5f, h * 0.55f),
            style = Stroke(width = w * 0.08f)
        )

        // Top button
        drawLine(
            color = color,
            start = Offset(w * 0.4f, h * 0.05f),
            end = Offset(w * 0.6f, h * 0.05f),
            strokeWidth = w * 0.08f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(w * 0.5f, h * 0.05f),
            end = Offset(w * 0.5f, h * 0.15f),
            strokeWidth = w * 0.06f,
            cap = StrokeCap.Round
        )

        // Hands
        drawLine(
            color = color,
            start = Offset(w * 0.5f, h * 0.55f),
            end = Offset(w * 0.5f, h * 0.3f),
            strokeWidth = w * 0.08f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = color,
            start = Offset(w * 0.5f, h * 0.55f),
            end = Offset(w * 0.68f, h * 0.55f),
            strokeWidth = w * 0.08f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun TaxDollarVectorIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Circular recycle arrows
        drawArc(
            color = color,
            startAngle = 45f,
            sweepAngle = 250f,
            useCenter = false,
            style = Stroke(width = w * 0.08f, cap = StrokeCap.Round)
        )

        // Arrow head
        val arrowPath = Path().apply {
            moveTo(w * 0.8f, h * 0.6f)
            lineTo(w * 0.95f, h * 0.72f)
            lineTo(w * 0.8f, h * 0.84f)
        }
        drawPath(
            path = arrowPath,
            color = color,
            style = Stroke(width = w * 0.08f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Dollar sign stem
        drawLine(
            color = color,
            start = Offset(w * 0.5f, h * 0.22f),
            end = Offset(w * 0.5f, h * 0.78f),
            strokeWidth = w * 0.07f,
            cap = StrokeCap.Round
        )

        // Dollar 'S' curve
        val sPath = Path().apply {
            moveTo(w * 0.62f, h * 0.35f)
            cubicTo(w * 0.4f, h * 0.25f, w * 0.38f, h * 0.45f, w * 0.5f, h * 0.5f)
            cubicTo(w * 0.62f, h * 0.55f, w * 0.6f, h * 0.75f, w * 0.38f, h * 0.65f)
        }
        drawPath(
            path = sPath,
            color = color,
            style = Stroke(width = w * 0.07f, cap = StrokeCap.Round)
        )
    }
}

@Composable
private fun FlameVectorIcon(modifier: Modifier = Modifier, color: Color) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val path = Path().apply {
            moveTo(w * 0.5f, 0f)
            cubicTo(w * 0.75f, h * 0.25f, w, h * 0.55f, w * 0.85f, h * 0.8f)
            cubicTo(w * 0.72f, h * 1.0f, w * 0.28f, h * 1.0f, w * 0.15f, h * 0.8f)
            cubicTo(0f, h * 0.55f, w * 0.25f, h * 0.25f, w * 0.5f, 0f)
            close()
        }
        drawPath(path = path, color = color)
    }
}
