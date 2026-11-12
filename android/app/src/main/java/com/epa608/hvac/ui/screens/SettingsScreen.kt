package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.billing.PlayBillingManager
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.minimumButtonHeight
import com.epa608.hvac.ui.theme.minimumTouchTarget
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Settings & Technician Profile Screen.
 *
 * Implements:
 * 1. License status card with 100% Pass Guarantee badge or Upgrade CTA.
 * 2. Study schedule Target Exam Date picker dialog.
 * 3. One-click Restore Google Play Purchases.
 * 4. Regulatory, Support, Privacy Policy, and EULA legal links.
 * 5. Full progress reset with destructive confirmation dialog.
 * 6. Minimum 48x48dp touch targets on all interactive items.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateToPaywall: () -> Unit,
    onNavigateToCertificate: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val uriHandler = LocalUriHandler.current
    val coroutineScope = rememberCoroutineScope()

    val engine = remember { EPAEngine.getInstance(context) }
    val billingManager = remember { PlayBillingManager.getInstance(context) }

    val billingState by billingManager.billingState.collectAsState()
    val isPro = billingState.isProUser
    val isRooftop by engine.isRooftopMode.collectAsState()
    val targetExamDateMs by engine.targetExamDateEpochMs.collectAsState()

    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }
    var showRestoreSuccessDialog by remember { mutableStateOf(false) }

    val dateFormat = remember { SimpleDateFormat("MMMM d, yyyy", Locale.US) }
    val formattedExamDate = remember(targetExamDateMs) { dateFormat.format(Date(targetExamDateMs)) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialColors.Charcoal)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header
        item {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "TECHNICIAN PROFILE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.SafetyAmber,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Settings & Pass Guarantee",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary
                )
            }
        }

        // License Status Card
        item {
            LicenseStatusCard(
                isPro = isPro,
                onUpgrade = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onNavigateToPaywall()
                }
            )
        }

        // Official EPA Universal Pass Certificate Row
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onNavigateToCertificate()
                    },
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
                border = BorderStroke(1.dp, IndustrialColors.CardBorder)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD97706).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = null,
                            tint = Color(0xFFD97706),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Universal Pass Certificate",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialColors.TextPrimary
                        )
                        Text(
                            text = "Official shareable verification for employers & UA unions",
                            style = MaterialTheme.typography.bodySmall,
                            color = IndustrialColors.TextSecondary
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IndustrialColors.PassGreen.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "VERIFIED",
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Black,
                            color = IndustrialColors.PassGreen
                        )
                    }
                }
            }
        }

        // Study Schedule Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "STUDY SCHEDULE",
                    style = MaterialTheme.typography.titleSmall,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumButtonHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable(
                            role = Role.Button,
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                showDatePickerDialog = true
                            }
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CalendarToday,
                                contentDescription = null,
                                tint = IndustrialColors.CyanAccent,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "Target Exam Date",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = IndustrialColors.TextPrimary
                                )
                                Text(
                                    text = formattedExamDate,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = IndustrialColors.SafetyAmber
                                )
                            }
                        }

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = IndustrialColors.TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }

        // Display & Job-Site Environment Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "JOB-SITE ENVIRONMENT",
                    style = MaterialTheme.typography.titleSmall,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
                    )
                ) {
                    SettingsSwitchRow(
                        icon = Icons.Default.WbSunny,
                        title = "Rooftop Sunlight Mode",
                        subtitle = "21:1 pure black-on-white high contrast for direct rooftop sunlight glare & sunglasses",
                        checked = isRooftop,
                        onCheckedChange = {
                            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            engine.toggleRooftopMode()
                        }
                    )
                }
            }
        }

        // Legal, Support, and Restore Purchases Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "RESOURCES & POLICIES",
                    style = MaterialTheme.typography.titleSmall,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
                    )
                ) {
                    Column {
                        SettingsActionRow(
                            icon = Icons.Default.Refresh,
                            title = "Restore Google Play Purchases",
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                coroutineScope.launch {
                                    billingManager.restorePurchases()
                                    showRestoreSuccessDialog = true
                                }
                            }
                        )

                        HorizontalDivider(color = IndustrialColors.CardBorder)

                        SettingsActionRow(
                            icon = Icons.Default.SupportAgent,
                            title = "Technician Support & Help",
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                uriHandler.openUri("https://epa608prep.com/support.html")
                            }
                        )

                        HorizontalDivider(color = IndustrialColors.CardBorder)

                        SettingsActionRow(
                            icon = Icons.Default.Policy,
                            title = "Privacy Policy",
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                uriHandler.openUri("https://epa608prep.com/privacy.html")
                            }
                        )

                        HorizontalDivider(color = IndustrialColors.CardBorder)

                        SettingsActionRow(
                            icon = Icons.Default.Description,
                            title = "Terms of Use (EULA)",
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                uriHandler.openUri("https://epa608prep.com/terms.html")
                            }
                        )
                    }
                }
            }
        }

        // Reset Study History Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        showResetDialog = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.HazardRed.copy(alpha = 0.12f),
                        contentColor = IndustrialColors.HazardRed
                    )
                ) {
                    Text(
                        text = "Reset All Study History",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "EPA 608 Universal HVAC Prep · Build 2027.1\nCompliant with 40 CFR Part 82 Regulations",
                    style = MaterialTheme.typography.bodySmall,
                    color = IndustrialColors.TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp,
                    modifier = Modifier.clickable {
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        billingManager.setProUserForTesting(!isPro)
                    }
                )
            }
        }
    }

    // Material 3 DatePickerDialog for Target Exam Date
    if (showDatePickerDialog) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = targetExamDateMs)
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selectedMs ->
                            engine.setTargetExamDate(selectedMs)
                        }
                        showDatePickerDialog = false
                    },
                    modifier = Modifier.minimumButtonHeight()
                ) {
                    Text("Select Date", color = IndustrialColors.CyanAccent, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDatePickerDialog = false },
                    modifier = Modifier.minimumButtonHeight()
                ) {
                    Text("Cancel", color = IndustrialColors.TextMuted)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = IndustrialColors.Navy
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    containerColor = IndustrialColors.Navy,
                    titleContentColor = IndustrialColors.TextPrimary,
                    headlineContentColor = IndustrialColors.TextPrimary,
                    selectedDayContainerColor = IndustrialColors.CyanAccent,
                    selectedDayContentColor = Color.Black,
                    todayDateBorderColor = IndustrialColors.SafetyAmber,
                    dayContentColor = IndustrialColors.TextPrimary
                )
            )
        }
    }

    // Destructive Reset Progress Confirmation Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            containerColor = IndustrialColors.Navy,
            title = {
                Text(
                    text = "Reset All Study Progress?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
            },
            text = {
                Text(
                    text = "This permanently deletes all your question attempts, diagnostic scores, and saved bookmarks.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IndustrialColors.TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResetDialog = false
                        engine.resetProgress()
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.HazardRed,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.minimumButtonHeight()
                ) {
                    Text("Reset Everything", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showResetDialog = false },
                    modifier = Modifier.minimumButtonHeight()
                ) {
                    Text("Cancel", color = IndustrialColors.TextMuted)
                }
            }
        )
    }

    // Restore Purchases Notification Dialog
    if (showRestoreSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showRestoreSuccessDialog = false },
            containerColor = IndustrialColors.Navy,
            title = {
                Text(
                    text = "Purchases Restored",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
            },
            text = {
                Text(
                    text = if (isPro)
                        "Your Universal License is active. 100% Pass Guarantee protected."
                    else
                        "No existing active purchase was found on this Google Play account.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IndustrialColors.TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { showRestoreSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = IndustrialColors.CyanAccent, contentColor = Color.Black),
                    modifier = Modifier.minimumButtonHeight()
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

// MARK: - License Status Card
@Composable
private fun LicenseStatusCard(
    isPro: Boolean,
    onUpgrade: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            if (isPro) IndustrialColors.PassGreen.copy(alpha = 0.15f)
                            else IndustrialColors.CyanAccent.copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPro) Icons.Default.CheckCircle else Icons.Default.Lock,
                        contentDescription = null,
                        tint = if (isPro) IndustrialColors.PassGreen else IndustrialColors.CyanAccent,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = if (isPro) "Universal License Active" else "Free Study Version",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialColors.TextPrimary
                    )
                    Text(
                        text = if (isPro) "100% Pass Guarantee Protected" else "Limited access to 2027 question banks",
                        style = MaterialTheme.typography.bodySmall,
                        color = IndustrialColors.TextSecondary
                    )
                }
            }

            if (!isPro) {
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onUpgrade,
                    modifier = Modifier
                        .minimumButtonHeight()
                        .height(44.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.CyanAccent,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Upgrade",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// MARK: - Settings Action Row (min 48dp height)
@Composable
private fun SettingsActionRow(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .minimumButtonHeight()
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = IndustrialColors.CyanAccent,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = IndustrialColors.TextPrimary
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = IndustrialColors.TextMuted,
            modifier = Modifier.size(14.dp)
        )
    }
}

// MARK: - Settings Switch Row (min 48dp height)
@Composable
private fun SettingsSwitchRow(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .minimumButtonHeight()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) IndustrialColors.SafetyAmber else IndustrialColors.CyanAccent,
                modifier = Modifier.size(22.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = IndustrialColors.TextSecondary
                )
            }
        }

        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = IndustrialColors.SafetyAmber,
                uncheckedThumbColor = IndustrialColors.TextMuted,
                uncheckedTrackColor = IndustrialColors.ElevatedSurface
            )
        )
    }
}
