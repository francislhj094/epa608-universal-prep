package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.billing.PlayBillingManager
import com.epa608.hvac.data.HVACQuestionBank
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.IndustrialTechnicalText
import com.epa608.hvac.ui.theme.minimumButtonHeight
import com.epa608.hvac.ui.theme.minimumTouchTarget

/**
 * Quiz Screen: Question viewer with instant green/red statutory feedback.
 *
 * Implements:
 * 1. 2026/2027 AIM Act A2L mandate indicator badge with flame icon.
 * 2. Exact 40 CFR and ASHRAE legal citations.
 * 3. Instant green/red feedback on option selection.
 * 4. Technical rationale explanation card.
 * 5. Pro gating (5 questions free vs full master pool for Pro).
 * 6. Minimum 48x48dp touch targets on all interactive controls.
 */
@Composable
fun QuizScreen(
    section: EPASection,
    drillMode: QuickDrillMode? = null,
    onDismiss: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    autoCompleteForTesting: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }
    val billingManager = remember { PlayBillingManager.getInstance(context) }

    val billingState by billingManager.billingState.collectAsState()
    val isPro = billingState.isProUser

    val completedIds by engine.completedQuestionIds.collectAsState()
    val correctIds by engine.correctQuestionIds.collectAsState()
    val bookmarkedIds by engine.bookmarkedQuestionIds.collectAsState()

    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var hasAnswered by remember { mutableStateOf(false) }
    var correctAnswersCount by remember { mutableIntStateOf(0) }
    var isCompleted by remember { mutableStateOf(autoCompleteForTesting) }

    // Load and filter questions
    LaunchedEffect(section, drillMode, isPro) {
        val rawPool: List<Question> = when (drillMode) {
            QuickDrillMode.RAPID_FIRE -> HVACQuestionBank.studyQuestions.shuffled().take(10)
            QuickDrillMode.WEAK_AREAS -> {
                val missed = HVACQuestionBank.studyQuestions.filter {
                    completedIds.contains(it.id) && !correctIds.contains(it.id)
                }
                if (missed.isNotEmpty()) missed else HVACQuestionBank.coreQuestions
            }
            QuickDrillMode.SAVED -> {
                val saved = HVACQuestionBank.allQuestions.filter { bookmarkedIds.contains(it.id) }
                if (saved.isNotEmpty()) saved else HVACQuestionBank.type1Questions
            }
            null -> HVACQuestionBank.getQuestionsForSection(section)
        }

        // Free users get 5 sample questions; Pro users get full question bank
        questions = if (isPro || drillMode == QuickDrillMode.RAPID_FIRE) {
            rawPool
        } else {
            rawPool.take(5)
        }
        currentIndex = 0
        selectedOptionIndex = null
        hasAnswered = false
        correctAnswersCount = if (autoCompleteForTesting) (questions.size * 0.8).toInt().coerceAtLeast(1) else 0
        isCompleted = autoCompleteForTesting
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialColors.Charcoal)
    ) {
        if (questions.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = IndustrialColors.CyanAccent)
            }
        } else if (isCompleted) {
            QuizCompletionView(
                correctCount = correctAnswersCount,
                totalCount = questions.size,
                isPro = isPro,
                onDismiss = onDismiss,
                onUpgrade = onNavigateToPaywall
            )
        } else {
            ActiveQuizView(
                question = questions[currentIndex],
                currentIndex = currentIndex,
                totalQuestions = questions.size,
                selectedOptionIndex = selectedOptionIndex,
                hasAnswered = hasAnswered,
                isBookmarked = bookmarkedIds.contains(questions[currentIndex].id),
                onBookmarkToggle = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    engine.toggleBookmark(questions[currentIndex].id)
                },
                onOptionSelect = { optionIndex ->
                    if (!hasAnswered) {
                        selectedOptionIndex = optionIndex
                        hasAnswered = true
                        val isCorrect = optionIndex == questions[currentIndex].correctIndex
                        if (isCorrect) {
                            correctAnswersCount++
                            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        } else {
                            view.performHapticFeedback(HapticFeedbackConstants.REJECT)
                        }
                        engine.recordAnswer(questions[currentIndex].id, isCorrect)
                    }
                },
                onNext = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    if (currentIndex + 1 < questions.size) {
                        currentIndex++
                        selectedOptionIndex = null
                        hasAnswered = false
                    } else {
                        isCompleted = true
                    }
                },
                onClose = {
                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                    onDismiss()
                }
            )
        }
    }
}

// MARK: - Active Quiz View
@Composable
private fun ActiveQuizView(
    question: Question,
    currentIndex: Int,
    totalQuestions: Int,
    selectedOptionIndex: Int?,
    hasAnswered: Boolean,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    onOptionSelect: (Int) -> Unit,
    onNext: () -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Top Bar with minimum 48x48dp touch targets
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onClose,
                modifier = Modifier.minimumTouchTarget()
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Quiz",
                    tint = IndustrialColors.TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = question.citation,
                style = IndustrialTechnicalText.CitationBadge,
                maxLines = 1
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                val isRooftop by engine.isRooftopMode.collectAsState()
                IconButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        engine.toggleRooftopMode()
                    },
                    modifier = Modifier.minimumTouchTarget()
                ) {
                    Icon(
                        imageVector = if (isRooftop) Icons.Default.WbSunny else Icons.Default.BrightnessMedium,
                        contentDescription = "Toggle Rooftop Sunlight Mode",
                        tint = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                IconButton(
                    onClick = onBookmarkToggle,
                    modifier = Modifier.minimumTouchTarget()
                ) {
                    Icon(
                        imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark Question",
                        tint = if (isBookmarked) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        // Progress Counter & Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${currentIndex + 1} of $totalQuestions",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        color = IndustrialColors.TextSecondary
                    )
                )

                Text(
                    text = question.section.rawValue,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = IndustrialColors.sectionColor(question.section)
                    )
                )
            }

            LinearProgressIndicator(
                progress = { (currentIndex + 1).toFloat() / totalQuestions.toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = IndustrialColors.CyanAccent,
                trackColor = IndustrialColors.CardBorder,
            )
        }

        // Scrollable Question Stem & Options
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // A2L Mandate Badge
            if (question.isA2L) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(IndustrialColors.SafetyAmber.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = IndustrialColors.SafetyAmber,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AIM ACT A2L MANDATE",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Black,
                        color = IndustrialColors.SafetyAmber
                    )
                }
            }

            // Question Text
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.TextPrimary,
                lineHeight = 26.sp
            )

            // Multiple Choice Options (A, B, C, D)
            val optionLetters = listOf("A", "B", "C", "D")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                question.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOptionIndex == index
                    val isCorrectOption = index == question.correctIndex

                    val (containerColor, borderColor) = when {
                        hasAnswered -> {
                            if (isCorrectOption) {
                                Pair(IndustrialColors.PassGreen.copy(alpha = 0.15f), IndustrialColors.PassGreen)
                            } else if (isSelected) {
                                Pair(IndustrialColors.HazardRed.copy(alpha = 0.15f), IndustrialColors.HazardRed)
                            } else {
                                Pair(IndustrialColors.Navy, IndustrialColors.CardBorder)
                            }
                        }
                        isSelected -> Pair(IndustrialColors.ElevatedSurface, IndustrialColors.CyanAccent)
                        else -> Pair(IndustrialColors.Navy, IndustrialColors.CardBorder)
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumButtonHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(
                                enabled = !hasAnswered,
                                role = Role.RadioButton,
                                onClick = { onOptionSelect(index) }
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        border = CardDefaults.outlinedCardBorder().copy(
                            brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Letter box
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(IndustrialColors.ElevatedSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = optionLetters.getOrElse(index) { "$index" },
                                    style = IndustrialTechnicalText.OptionLetter,
                                    color = if (hasAnswered && isCorrectOption) IndustrialColors.PassGreen
                                    else IndustrialColors.TextSecondary
                                )
                            }

                            Text(
                                text = optionText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = IndustrialColors.TextPrimary,
                                modifier = Modifier.weight(1f),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }

            // Technical Rationale Explanation Drawer
            AnimatedVisibility(
                visible = hasAnswered,
                enter = fadeIn() + slideInVertically()
            ) {
                val isCorrect = selectedOptionIndex == question.correctIndex
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
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
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Warning,
                                contentDescription = null,
                                tint = if (isCorrect) IndustrialColors.PassGreen else IndustrialColors.HazardRed,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isCorrect) "CORRECT" else "INCORRECT",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black,
                                color = if (isCorrect) IndustrialColors.PassGreen else IndustrialColors.HazardRed
                            )
                        }

                        Text(
                            text = question.explanation,
                            style = MaterialTheme.typography.bodyMedium,
                            color = IndustrialColors.TextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }

        // Bottom Advance Button (min 48dp height)
        if (hasAnswered) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.CyanAccent,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = if (currentIndex + 1 < totalQuestions) "Next Question →" else "View Results",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// MARK: - Completion View
@Composable
private fun QuizCompletionView(
    correctCount: Int,
    totalCount: Int,
    isPro: Boolean,
    onDismiss: () -> Unit,
    onUpgrade: () -> Unit
) {
    val scorePercentage = if (totalCount > 0) (correctCount.toDouble() / totalCount.toDouble()) * 100.0 else 0.0
    val isPassed = scorePercentage >= 70.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        if (isPassed) IndustrialColors.PassGreen.copy(alpha = 0.15f)
                        else IndustrialColors.HazardRed.copy(alpha = 0.15f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isPassed) Icons.Default.Verified else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isPassed) IndustrialColors.PassGreen else IndustrialColors.HazardRed,
                    modifier = Modifier.size(54.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (isPassed) "Section Drill Passed" else "Needs Review",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary
                )

                Text(
                    text = "$correctCount of $totalCount correct (${scorePercentage.toInt()}%)",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isPassed) IndustrialColors.PassGreen else IndustrialColors.HazardRed
                    )
                )
            }

            if (!isPro) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Unlock Full 650+ Question Master Pool",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = IndustrialColors.SafetyAmber
                        )
                        Text(
                            text = "Free version includes 5 sample questions per section. Upgrade to Universal License for complete 2026/2027 AIM Act question banks.",
                            style = MaterialTheme.typography.bodySmall,
                            color = IndustrialColors.TextSecondary
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (!isPro) {
                Button(
                    onClick = onUpgrade,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.SafetyAmber,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Unlock Complete Universal Prep",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndustrialColors.CyanAccent,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = "Back to Dashboard",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
