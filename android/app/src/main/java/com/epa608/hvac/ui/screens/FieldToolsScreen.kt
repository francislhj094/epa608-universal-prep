package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.InvertColors
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.domain.ApplianceType
import com.epa608.hvac.domain.EvacuationTable
import com.epa608.hvac.domain.LeakRateCalculator
import com.epa608.hvac.domain.RefrigerantType
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.IndustrialTechnicalText
import com.epa608.hvac.ui.theme.minimumButtonHeight
import com.epa608.hvac.ui.theme.minimumTouchTarget
import java.util.Locale

/**
 * Job-Site Field Tools Screen:
 *
 * Implements:
 * 1. 100% 40 CFR § 82.156 Table 1 Compliant Evacuation Lookup.
 * 2. 40 CFR § 82.152 Clamped Annualized Leak Rate Calculator with 40 CFR § 82.157 trigger enforcement.
 * 3. Daily retention interactive Refrigerant Pressure-Temperature (P-T) Chart for R-410A, R-454B (A2L), R-32 (A2L), R-22, and R-134a.
 * 4. Strict Android Accessibility minimum 48x48dp touch targets on all interactive selectors and inputs.
 */
@Composable
fun FieldToolsScreen(
    initialToolIndex: Int = 0,
    initialCharge: String = "120",
    initialAdded: String = "15",
    initialDays: String = "90",
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    var selectedToolIndex by remember { mutableIntStateOf(initialToolIndex) } // 0 = Evacuation, 1 = Leak Calc, 2 = P-T Chart

    // Leak Calculator State
    var fullChargeLbs by remember { mutableStateOf(initialCharge) }
    var refrigerantAddedLbs by remember { mutableStateOf(initialAdded) }
    var daysSinceLastAddition by remember { mutableStateOf(initialDays) }
    var selectedApplianceType by remember { mutableStateOf(ApplianceType.COMMERCIAL_REFRIGERATION) }

    LaunchedEffect(initialToolIndex, initialCharge, initialAdded, initialDays) {
        selectedToolIndex = initialToolIndex
        fullChargeLbs = initialCharge
        refrigerantAddedLbs = initialAdded
        daysSinceLastAddition = initialDays
    }

    // P-T Chart State
    var selectedRefrigerant by remember { mutableStateOf(RefrigerantType.R454B) }
    var temperatureF by remember { mutableDoubleStateOf(40.0) } // Standard 40°F evaporator saturation

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialColors.Charcoal)
            .statusBarsPadding()
    ) {
        // Field Tools Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = "EPA 40 CFR PART 82 & 84",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.SafetyAmber,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Job-Site Tools",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary
                )
            }

            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = IndustrialColors.CyanAccent,
                modifier = Modifier.size(24.dp)
            )
        }

        // Segmented Tool Selector (min 48dp height per button)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(IndustrialColors.Navy)
                .border(1.dp, IndustrialColors.CardBorder, RoundedCornerShape(12.dp))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            ToolSelectorTab(
                title = "Evacuation",
                icon = Icons.Default.Speed,
                isSelected = selectedToolIndex == 0,
                modifier = Modifier.weight(1f),
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    selectedToolIndex = 0
                }
            )

            ToolSelectorTab(
                title = "Leak Calc",
                icon = Icons.Default.InvertColors,
                isSelected = selectedToolIndex == 1,
                modifier = Modifier.weight(1f),
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    selectedToolIndex = 1
                }
            )

            ToolSelectorTab(
                title = "P-T Chart",
                icon = Icons.Default.Thermostat,
                isSelected = selectedToolIndex == 2,
                modifier = Modifier.weight(1f),
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    selectedToolIndex = 2
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tool Content Area
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 100.dp)
        ) {
            when (selectedToolIndex) {
                0 -> {
                    // Evacuation Table 1 Lookup
                    item {
                        Text(
                            text = "EPA REQUIRED EVACUATION LEVELS (TABLE 1)",
                            style = MaterialTheme.typography.titleSmall,
                            color = IndustrialColors.TextMuted,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                    }

                    items(EvacuationTable.categories.size) { catIdx ->
                        val category = EvacuationTable.categories[catIdx]
                        EvacuationCategoryCard(category)
                    }
                }

                1 -> {
                    // Leak Rate Calculator
                    item {
                        val chargeVal = fullChargeLbs.toDoubleOrNull() ?: 0.0
                        val addedVal = refrigerantAddedLbs.toDoubleOrNull() ?: 0.0
                        val daysVal = daysSinceLastAddition.toDoubleOrNull() ?: 0.0

                        val leakRate = LeakRateCalculator.calculateLeakRate(chargeVal, addedVal, daysVal)
                        val isExceeded = LeakRateCalculator.isExceedingTrigger(
                            fullChargeLbs = chargeVal,
                            leakRatePercentage = leakRate,
                            applianceType = selectedApplianceType,
                            enforce15LbThreshold = true
                        )

                        LeakCalculatorContent(
                            calculatedRate = leakRate,
                            isExceeded = isExceeded,
                            applianceType = selectedApplianceType,
                            fullChargeText = fullChargeLbs,
                            refrigerantAddedText = refrigerantAddedLbs,
                            daysSinceAdditionText = daysSinceLastAddition,
                            onChargeChange = { fullChargeLbs = it },
                            onAddedChange = { refrigerantAddedLbs = it },
                            onDaysChange = { daysSinceLastAddition = it },
                            onApplianceTypeChange = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                selectedApplianceType = it
                            }
                        )
                    }
                }

                2 -> {
                    // Interactive Refrigerant P-T Chart
                    item {
                        PtChartContent(
                            selectedRefrigerant = selectedRefrigerant,
                            temperatureF = temperatureF,
                            onRefrigerantChange = {
                                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                                selectedRefrigerant = it
                            },
                            onTemperatureChange = { temperatureF = it }
                        )
                    }
                }
            }
        }
    }
}

// MARK: - Tool Selector Tab
@Composable
private fun ToolSelectorTab(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .minimumButtonHeight()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) IndustrialColors.CyanAccent else Color.Transparent)
            .clickable(
                role = Role.Tab,
                onClick = onClick
            )
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isSelected) Color.Black else IndustrialColors.TextSecondary,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.Black else IndustrialColors.TextSecondary
            )
        }
    }
}

// MARK: - Evacuation Card
@Composable
private fun EvacuationCategoryCard(
    category: EvacuationTable.EvacuationCategory
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = category.category,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.CyanAccent
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                category.specs.forEach { spec ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = spec.condition,
                            style = MaterialTheme.typography.bodyMedium,
                            color = IndustrialColors.TextSecondary,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = spec.requiredLevel,
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = IndustrialColors.TextPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}

// MARK: - Leak Calculator Content
@Composable
private fun LeakCalculatorContent(
    calculatedRate: Double,
    isExceeded: Boolean,
    applianceType: ApplianceType,
    fullChargeText: String,
    refrigerantAddedText: String,
    daysSinceAdditionText: String,
    onChargeChange: (String) -> Unit,
    onAddedChange: (String) -> Unit,
    onDaysChange: (String) -> Unit,
    onApplianceTypeChange: (ApplianceType) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        // Result Readout Banner
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(
                    if (isExceeded) IndustrialColors.HazardRed else IndustrialColors.CardBorder
                )
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = "ANNUALIZED LEAK RATE",
                            style = MaterialTheme.typography.labelMedium,
                            color = IndustrialColors.TextMuted,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = String.format(Locale.US, "%.1f%%", calculatedRate),
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Black,
                                fontSize = 38.sp,
                                color = if (isExceeded) IndustrialColors.HazardRed else IndustrialColors.PassGreen
                            )
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = "LEGAL THRESHOLD",
                            style = MaterialTheme.typography.labelMedium,
                            color = IndustrialColors.TextMuted,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = String.format(Locale.US, "%.0f%%", applianceType.triggerRate),
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                color = IndustrialColors.SafetyAmber
                            )
                        )
                    }
                }

                HorizontalDivider(color = IndustrialColors.CardBorder)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (isExceeded) Icons.Default.Warning else Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = if (isExceeded) IndustrialColors.HazardRed else IndustrialColors.PassGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isExceeded) "MANDATORY 30-DAY REPAIR REQUIRED (40 CFR § 82.157)"
                        else "Compliant: Below Statutory Leak Threshold",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isExceeded) IndustrialColors.HazardRed else IndustrialColors.PassGreen
                    )
                }
            }
        }

        // Appliance Category Picker (min 48dp height per row)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "APPLIANCE CATEGORY (15+ LBS AIM / 50+ LBS ODS)",
                style = MaterialTheme.typography.titleSmall,
                color = IndustrialColors.TextMuted,
                letterSpacing = 1.sp
            )

            ApplianceType.entries.forEach { type ->
                val isSelected = applianceType == type

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumButtonHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            role = Role.RadioButton,
                            onClick = { onApplianceTypeChange(type) }
                        ),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) IndustrialColors.ElevatedSurface else IndustrialColors.Navy
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (isSelected) IndustrialColors.CyanAccent else IndustrialColors.CardBorder
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Text(
                                text = type.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = IndustrialColors.TextPrimary
                            )
                            Text(
                                text = type.citation,
                                style = MaterialTheme.typography.bodySmall,
                                color = IndustrialColors.TextSecondary
                            )
                        }

                        Text(
                            text = String.format(Locale.US, "%.0f%%", type.triggerRate),
                            style = TextStyle(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = if (isSelected) IndustrialColors.CyanAccent else IndustrialColors.TextMuted
                            )
                        )
                    }
                }
            }
        }

        // Input Fields (with minimum 48dp touch targets)
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            LeakInputField(
                label = "Full Appliance Charge (lbs)",
                value = fullChargeText,
                placeholder = "e.g. 120",
                onValueChange = onChargeChange
            )

            LeakInputField(
                label = "Refrigerant Added (lbs)",
                value = refrigerantAddedText,
                placeholder = "e.g. 15",
                onValueChange = onAddedChange
            )

            LeakInputField(
                label = "Days Since Last Addition",
                value = daysSinceAdditionText,
                placeholder = "e.g. 90",
                onValueChange = onDaysChange
            )
        }
    }
}

@Composable
private fun LeakInputField(
    label: String,
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = IndustrialColors.TextSecondary
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = IndustrialColors.TextMuted) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = IndustrialColors.TextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .minimumButtonHeight(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = IndustrialColors.Navy,
                unfocusedContainerColor = IndustrialColors.Navy,
                focusedBorderColor = IndustrialColors.CyanAccent,
                unfocusedBorderColor = IndustrialColors.CardBorder,
                cursorColor = IndustrialColors.CyanAccent
            )
        )
    }
}

// MARK: - Interactive P-T Chart Content
@Composable
private fun PtChartContent(
    selectedRefrigerant: RefrigerantType,
    temperatureF: Double,
    onRefrigerantChange: (RefrigerantType) -> Unit,
    onTemperatureChange: (Double) -> Unit
) {
    val pressurePsig = selectedRefrigerant.saturationPressurePsig(temperatureF)

    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Text(
            text = "REFRIGERANT SELECTION",
            style = MaterialTheme.typography.titleSmall,
            color = IndustrialColors.TextMuted,
            letterSpacing = 1.sp
        )

        // Refrigerant Option Selectors (min 48dp height each)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RefrigerantType.entries.forEach { ref ->
                val isSelected = selectedRefrigerant == ref

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumButtonHeight()
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(
                            role = Role.RadioButton,
                            onClick = { onRefrigerantChange(ref) }
                        ),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) IndustrialColors.ElevatedSurface else IndustrialColors.Navy
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (isSelected) IndustrialColors.CyanAccent else IndustrialColors.CardBorder
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = ref.displayName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = IndustrialColors.TextPrimary
                            )

                            if (ref.isA2L) {
                                Text(
                                    text = "A2L",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Black,
                                    color = IndustrialColors.SafetyAmber,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(IndustrialColors.SafetyAmber.copy(alpha = 0.15f))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = IndustrialColors.CyanAccent,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // Pressure Readout Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "SATURATED VAPOR PRESSURE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextSecondary,
                    letterSpacing = 1.sp
                )

                Text(
                    text = String.format(Locale.US, "%.1f PSIG", pressurePsig),
                    style = IndustrialTechnicalText.GaugeValue
                )

                Text(
                    text = "Saturation Temperature: ${temperatureF.toInt()}°F",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = IndustrialColors.TextPrimary
                )
            }
        }

        // Temperature Slider Control
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
            border = CardDefaults.outlinedCardBorder().copy(
                brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Saturation Temperature:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = IndustrialColors.TextSecondary
                    )

                    Text(
                        text = "${temperatureF.toInt()}°F",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            color = IndustrialColors.SafetyAmber
                        )
                    )
                }

                Slider(
                    value = temperatureF.toFloat(),
                    onValueChange = { onTemperatureChange(it.toDouble()) },
                    valueRange = -20f..140f,
                    steps = 159,
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumButtonHeight(),
                    colors = SliderDefaults.colors(
                        thumbColor = IndustrialColors.CyanAccent,
                        activeTrackColor = IndustrialColors.CyanAccent,
                        inactiveTrackColor = IndustrialColors.CardBorder
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("-20°F", style = MaterialTheme.typography.bodySmall, color = IndustrialColors.TextMuted)
                    Text("40°F (Evap)", style = MaterialTheme.typography.bodySmall, color = IndustrialColors.CyanAccent)
                    Text("140°F", style = MaterialTheme.typography.bodySmall, color = IndustrialColors.TextMuted)
                }
            }
        }
    }
}
