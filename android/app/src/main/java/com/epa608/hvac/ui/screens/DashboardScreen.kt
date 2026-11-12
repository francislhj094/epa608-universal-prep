package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.billing.PlayBillingManager
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.IndustrialTechnicalText
import com.epa608.hvac.ui.theme.minimumButtonHeight
import com.epa608.hvac.ui.theme.minimumTouchTarget

/**
 * Quick Drill filter modes for targeted study sessions.
 */
enum class QuickDrillMode {
    RAPID_FIRE,
    WEAK_AREAS,
    SAVED
}

/**
 * Dashboard Screen: High-contrast Technician Command Center.
 *
 * Implements:
 * 1. Overall exam readiness dial with EPA 70% threshold indicator.
 * 2. Rapid drills (Rapid Fire, Weak Areas, Saved Bookmarks).
 * 3. Modular 40 CFR Part 82 section breakdown cards (Core, Type I, Type II, Type III).
 * 4. Pro/Upgrade status pill badge.
 * 5. Strict adherence to Android Accessibility standards (min 48x48dp touch targets).
 */
@Composable
fun DashboardScreen(
    onNavigateToQuiz: (EPASection, QuickDrillMode?) -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    onNavigateToCertificate: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }
    val billingManager = remember { PlayBillingManager.getInstance(context) }

    val readinessScore by engine.overallReadinessScore.collectAsState()
    val completedIds by engine.completedQuestionIds.collectAsState()
    val correctIds by engine.correctQuestionIds.collectAsState()
    val bookmarkedIds by engine.bookmarkedQuestionIds.collectAsState()
    val billingState by billingManager.billingState.collectAsState()
    val isPro = billingState.isProUser
    val isRooftop by engine.isRooftopMode.collectAsState()

    val missedCount = (completedIds.size - correctIds.size).coerceAtLeast(0)
    val savedCount = bookmarkedIds.size

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialColors.Charcoal),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 16.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Top Header
        item {
            DashboardHeader(
                isPro = isPro,
                isRooftop = isRooftop,
                onToggleRooftop = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    engine.toggleRooftopMode()
                },
                onUpgradeClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onNavigateToPaywall()
                }
            )
        }

        // Exam Readiness Dial Card
        item {
            ReadinessDialCard(
                scorePercentage = readinessScore
            )
        }

        // Official EPA Universal Pass Certificate Banner
        item {
            OfficialCertificateBanner(
                onViewCertificate = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onNavigateToCertificate()
                }
            )
        }

        // Quick Drills Section
        item {
            QuickDrillsSection(
                missedCount = missedCount,
                savedCount = savedCount,
                onFlashcardsSelected = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onNavigateToFlashcards()
                },
                onDrillSelected = { mode ->
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    val section = when (mode) {
                        QuickDrillMode.RAPID_FIRE -> EPASection.UNIVERSAL
                        QuickDrillMode.WEAK_AREAS -> EPASection.CORE
                        QuickDrillMode.SAVED -> EPASection.TYPE_I
                    }
                    onNavigateToQuiz(section, mode)
                }
            )
        }

        // Exam Sections Breakdown (40 CFR Part 82)
        item {
            Text(
                text = "EXAM SECTIONS (40 CFR PART 82)",
                style = MaterialTheme.typography.titleSmall,
                color = IndustrialColors.TextMuted,
                letterSpacing = 1.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }

        val examSections = listOf(
            EPASection.CORE,
            EPASection.TYPE_I,
            EPASection.TYPE_II,
            EPASection.TYPE_III
        )

        items(examSections.size) { index ->
            val section = examSections[index]
            val sectionReadiness = engine.readiness(section)
            SectionCard(
                section = section,
                readinessScore = sectionReadiness,
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onNavigateToQuiz(section, null)
                }
            )
        }
    }
}

// MARK: - Header
@Composable
private fun DashboardHeader(
    isPro: Boolean,
    isRooftop: Boolean,
    onToggleRooftop: () -> Unit,
    onUpgradeClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = "EPA 608 UNIVERSAL",
                style = MaterialTheme.typography.labelMedium,
                color = IndustrialColors.SafetyAmber,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )
            Text(
                text = "Technician Hub",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = IndustrialColors.TextPrimary
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Rooftop Sunlight Mode Quick Toggle Pill
            Box(
                modifier = Modifier
                    .minimumTouchTarget()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isRooftop) IndustrialColors.SafetyAmber.copy(alpha = 0.2f)
                        else IndustrialColors.ElevatedSurface
                    )
                    .border(
                        width = 1.dp,
                        color = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.CardBorder,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(
                        role = Role.Button,
                        onClick = onToggleRooftop
                    )
                    .padding(horizontal = 10.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = if (isRooftop) Icons.Default.WbSunny else Icons.Default.BrightnessMedium,
                        contentDescription = "Rooftop Sunlight Glare Mode",
                        tint = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (isRooftop) "SUN" else "ROOF",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // PRO / UPGRADE Pill Badge with minimum 48x48dp touch target
            Box(
                modifier = Modifier
                    .minimumTouchTarget()
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isPro) IndustrialColors.PassGreen.copy(alpha = 0.15f)
                        else IndustrialColors.CyanAccent.copy(alpha = 0.15f)
                    )
                    .border(
                        width = 1.dp,
                        color = if (isPro) IndustrialColors.PassGreen else IndustrialColors.CyanAccent,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable(
                        role = Role.Button,
                        onClick = onUpgradeClick
                    )
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = if (isPro) IndustrialColors.PassGreen else IndustrialColors.CyanAccent,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (isPro) "PRO" else "UPGRADE",
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isPro) IndustrialColors.PassGreen else IndustrialColors.CyanAccent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// MARK: - Readiness Card
@Composable
private fun ReadinessDialCard(
    scorePercentage: Double
) {
    val isPassing = scorePercentage >= 70.0
    val dialColor = if (isPassing) IndustrialColors.PassGreen else IndustrialColors.SafetyAmber

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Circular Dial Canvas
            Box(
                modifier = Modifier.size(86.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(86.dp)) {
                    val strokeWidth = 8.dp.toPx()
                    val arcSize = size.width - strokeWidth

                    // Track
                    drawCircle(
                        color = IndustrialColors.CardBorder,
                        radius = arcSize / 2,
                        style = Stroke(width = strokeWidth)
                    )

                    // Progress Arc
                    val sweep = ((scorePercentage / 100.0) * 360f).toFloat().coerceIn(0f, 360f)
                    drawArc(
                        color = dialColor,
                        startAngle = -90f,
                        sweepAngle = sweep,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                Text(
                    text = "${scorePercentage.toInt()}%",
                    style = IndustrialTechnicalText.PercentageDial,
                    color = IndustrialColors.TextPrimary
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (isPassing) "Passing Zone" else "Prep Required",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = dialColor
                )
                Text(
                    text = "Official EPA passing threshold is 70% (minimum 18/25 per section).",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IndustrialColors.TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

// MARK: - Official Universal Pass Certificate Banner
@Composable
private fun OfficialCertificateBanner(
    onViewCertificate: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onViewCertificate() }
            .border(
                width = 1.dp,
                color = if (IndustrialColors.isRooftopMode) IndustrialColors.CardBorder else Color(0xFFD97706).copy(alpha = 0.5f),
                shape = RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (IndustrialColors.isRooftopMode) Color(0xFFFEF3C7).copy(alpha = 0.4f) else Color(0xFF1E160A)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD97706).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    tint = Color(0xFFD97706),
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "UNIVERSAL PASS CERTIFICATE",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFD97706),
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Official Proof of Exam Readiness",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
                Text(
                    text = "Shareable with employers & union apprenticeships",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = IndustrialColors.TextSecondary
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "View Certificate",
                tint = Color(0xFFD97706),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// MARK: - Quick Drills Section
@Composable
private fun QuickDrillsSection(
    missedCount: Int,
    savedCount: Int,
    onFlashcardsSelected: () -> Unit,
    onDrillSelected: (QuickDrillMode) -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "QUICK DRILLS & FLASHCARDS",
            style = MaterialTheme.typography.titleSmall,
            color = IndustrialColors.TextMuted,
            letterSpacing = 1.sp
        )

        // Row 1: Flashcards and Rapid Fire
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DrillCard(
                title = "Flashcards",
                description = "Truck Break Flip",
                icon = Icons.Default.Layers,
                accentColor = IndustrialColors.CyanAccent,
                modifier = Modifier.weight(1f),
                onClick = onFlashcardsSelected
            )

            DrillCard(
                title = "Rapid Fire",
                description = "10 Random Qs",
                icon = Icons.Default.ElectricBolt,
                accentColor = IndustrialColors.SafetyAmber,
                modifier = Modifier.weight(1f),
                onClick = { onDrillSelected(QuickDrillMode.RAPID_FIRE) }
            )
        }

        // Row 2: Weak Areas and Saved
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DrillCard(
                title = "Weak Areas",
                description = "$missedCount Missed",
                icon = Icons.Default.Warning,
                accentColor = IndustrialColors.HazardRed,
                modifier = Modifier.weight(1f),
                onClick = { onDrillSelected(QuickDrillMode.WEAK_AREAS) }
            )

            DrillCard(
                title = "Saved",
                description = "$savedCount Saved",
                icon = Icons.Default.Bookmark,
                accentColor = IndustrialColors.PassGreen,
                modifier = Modifier.weight(1f),
                onClick = { onDrillSelected(QuickDrillMode.SAVED) }
            )
        }
    }
}

@Composable
private fun DrillCard(
    title: String,
    description: String,
    icon: ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .minimumButtonHeight()
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = accentColor,
                modifier = Modifier.size(22.dp)
            )

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = IndustrialColors.TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

// MARK: - Section Card
@Composable
private fun SectionCard(
    section: EPASection,
    readinessScore: Double,
    onClick: () -> Unit
) {
    val sectionColor = IndustrialColors.sectionColor(section)
    val isPassing = readinessScore >= 70.0

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .minimumButtonHeight()
            .clip(RoundedCornerShape(14.dp))
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = section.rawValue,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Black,
                            color = sectionColor
                        )
                        Text(
                            text = "•",
                            color = IndustrialColors.TextMuted,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialColors.TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = section.subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = IndustrialColors.TextSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${readinessScore.toInt()}%",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = if (isPassing) IndustrialColors.PassGreen else IndustrialColors.TextSecondary
                    )
                )
            }

            // Custom Linear Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(IndustrialColors.CardBorder)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = (readinessScore / 100.0).toFloat().coerceIn(0f, 1f))
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(sectionColor)
                )
            }
        }
    }
}
