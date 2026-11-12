package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.OutlinedFlag
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.epa608.hvac.domain.ExamAttempt
import com.epa608.hvac.domain.Question
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.IndustrialTechnicalText
import com.epa608.hvac.ui.theme.minimumButtonHeight
import com.epa608.hvac.ui.theme.minimumTouchTarget
import kotlinx.coroutines.delay

/**
 * Modular Section Score summary for official EPA Universal scoring.
 */
data class ModularSectionScore(
    val section: EPASection,
    val correctCount: Int,
    val totalCount: Int,
    val scorePercentage: Double,
    val passed: Boolean
)

/**
 * 80-Question Timed Universal Exam Simulator Screen.
 *
 * Implements:
 * 1. 120-minute (7200 seconds) background-resilient countdown proctor timer.
 * 2. 80-question distribution across all 4 mandatory EPA sections (20 Core, 20 Type I, 20 Type II, 20 Type III).
 * 3. Question flagging and free navigation across all questions.
 * 4. Statutory modular scoring: mandates >= 70% passing cutoff in ALL FOUR sections individually.
 * 5. Full compliance with Android Accessibility Standards (min 48x48dp touch targets).
 */
@Composable
fun ExamSimulatorScreen(
    onNavigateToPaywall: () -> Unit,
    onNavigateToCertificate: () -> Unit = {},
    autoStartForTesting: Boolean = false,
    autoSubmitForTesting: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }
    val billingManager = remember { PlayBillingManager.getInstance(context) }

    val billingState by billingManager.billingState.collectAsState()
    val isPro = billingState.isProUser

    var isExamActive by remember { mutableStateOf(false) }
    var examQuestions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    val userAnswers = remember { mutableStateMapOf<String, Int>() }
    val flaggedQuestionIds = remember { mutableStateOf(setOf<String>()) }
    var examAttemptResult by remember { mutableStateOf<ExamAttempt?>(null) }
    var modularScores by remember { mutableStateOf<List<ModularSectionScore>>(emptyList()) }
    var showSubmitConfirmationDialog by remember { mutableStateOf(false) }

    // Background-resilient timestamp tracking
    var examStartTimeEpochMs by remember { mutableLongStateOf(0L) }
    val totalExamDurationSeconds = 7200L // 120 Minutes
    var remainingSeconds by remember { mutableLongStateOf(totalExamDurationSeconds) }

    // Auto-submit proctored exam for verification testing
    LaunchedEffect(autoSubmitForTesting) {
        if (autoSubmitForTesting) {
            val sampleAttempt = ExamAttempt(
                id = "test_attempt_1",
                timestamp = System.currentTimeMillis(),
                section = EPASection.CORE,
                score = 85.0,
                passed = true,
                timeElapsedSeconds = 3450
            )
            val sampleModular = listOf(
                ModularSectionScore(EPASection.CORE, 17, 20, 85.0, true),
                ModularSectionScore(EPASection.TYPE_I, 16, 20, 80.0, true),
                ModularSectionScore(EPASection.TYPE_II, 18, 20, 90.0, true),
                ModularSectionScore(EPASection.TYPE_III, 17, 20, 85.0, true)
            )
            examAttemptResult = sampleAttempt
            modularScores = sampleModular
            isExamActive = false
        }
    }

    // Auto-start proctored exam for verification testing
    LaunchedEffect(autoStartForTesting) {
        if (autoStartForTesting && !isExamActive && examAttemptResult == null && !autoSubmitForTesting) {
            val corePool = HVACQuestionBank.coreQuestions.shuffled().take(20)
            val t1Pool = HVACQuestionBank.type1Questions.shuffled().take(20)
            val t2Pool = HVACQuestionBank.type2Questions.shuffled().take(20)
            val t3Pool = HVACQuestionBank.type3Questions.shuffled().take(20)

            val fullExamPool = (corePool + t1Pool + t2Pool + t3Pool).shuffled()
            examQuestions = fullExamPool
            currentQuestionIndex = 0
            userAnswers.clear()
            flaggedQuestionIds.value = emptySet()
            examStartTimeEpochMs = System.currentTimeMillis()
            remainingSeconds = totalExamDurationSeconds
            isExamActive = true
        }
    }

    // Proctor Timer Loop
    LaunchedEffect(isExamActive, examStartTimeEpochMs) {
        if (isExamActive && examStartTimeEpochMs > 0L) {
            while (isExamActive) {
                val elapsedSeconds = (System.currentTimeMillis() - examStartTimeEpochMs) / 1000L
                val remaining = (totalExamDurationSeconds - elapsedSeconds).coerceAtLeast(0L)
                remainingSeconds = remaining

                if (remaining <= 0L) {
                    // Time expired: auto submit
                    finishExam(
                        questions = examQuestions,
                        userAnswers = userAnswers,
                        elapsedSeconds = totalExamDurationSeconds.toInt(),
                        engine = engine,
                        onResult = { attempt, scores ->
                            examAttemptResult = attempt
                            modularScores = scores
                            isExamActive = false
                        }
                    )
                    break
                }
                delay(1000L)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(IndustrialColors.Charcoal)
    ) {
        when {
            examAttemptResult != null -> {
                ExamResultScreen(
                    attempt = examAttemptResult!!,
                    modularScores = modularScores,
                    onReturn = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        examAttemptResult = null
                        isExamActive = false
                    },
                    onNavigateToCertificate = onNavigateToCertificate
                )
            }
            isExamActive && examQuestions.isNotEmpty() -> {
                ActiveExamScreen(
                    questions = examQuestions,
                    currentIndex = currentQuestionIndex,
                    remainingSeconds = remainingSeconds.toInt(),
                    userAnswers = userAnswers,
                    flaggedQuestionIds = flaggedQuestionIds.value,
                    onQuestionChange = { index ->
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        currentQuestionIndex = index
                    },
                    onOptionSelect = { qId, optIndex ->
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        userAnswers[qId] = optIndex
                    },
                    onFlagToggle = { qId ->
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        val current = flaggedQuestionIds.value
                        flaggedQuestionIds.value = if (current.contains(qId)) {
                            current - qId
                        } else {
                            current + qId
                        }
                    },
                    onSubmitClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        showSubmitConfirmationDialog = true
                    }
                )
            }
            else -> {
                StartExamScreen(
                    isPro = isPro,
                    onStartExam = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        // Assemble 80 questions (20 Core, 20 Type I, 20 Type II, 20 Type III)
                        val corePool = HVACQuestionBank.coreQuestions.shuffled().take(20)
                        val t1Pool = HVACQuestionBank.type1Questions.shuffled().take(20)
                        val t2Pool = HVACQuestionBank.type2Questions.shuffled().take(20)
                        val t3Pool = HVACQuestionBank.type3Questions.shuffled().take(20)

                        val fullExamPool = (corePool + t1Pool + t2Pool + t3Pool).shuffled()
                        examQuestions = fullExamPool
                        currentQuestionIndex = 0
                        userAnswers.clear()
                        flaggedQuestionIds.value = emptySet()
                        examStartTimeEpochMs = System.currentTimeMillis()
                        remainingSeconds = totalExamDurationSeconds
                        isExamActive = true
                    },
                    onUnlockPro = onNavigateToPaywall
                )
            }
        }

        // Submit Confirmation Dialog
        if (showSubmitConfirmationDialog) {
            val answeredCount = userAnswers.size
            val unansweredCount = (examQuestions.size - answeredCount).coerceAtLeast(0)

            AlertDialog(
                onDismissRequest = { showSubmitConfirmationDialog = false },
                containerColor = IndustrialColors.Navy,
                title = {
                    Text(
                        text = "Submit Universal Exam?",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialColors.TextPrimary
                    )
                },
                text = {
                    Text(
                        text = "You have answered $answeredCount of ${examQuestions.size} questions." +
                                if (unansweredCount > 0) " There are $unansweredCount unanswered questions." else "",
                        style = MaterialTheme.typography.bodyMedium,
                        color = IndustrialColors.TextSecondary
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showSubmitConfirmationDialog = false
                            val elapsed = ((System.currentTimeMillis() - examStartTimeEpochMs) / 1000L).toInt()
                            finishExam(
                                questions = examQuestions,
                                userAnswers = userAnswers,
                                elapsedSeconds = elapsed,
                                engine = engine,
                                onResult = { attempt, scores ->
                                    examAttemptResult = attempt
                                    modularScores = scores
                                    isExamActive = false
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = IndustrialColors.SafetyAmber, contentColor = Color.Black),
                        modifier = Modifier.minimumButtonHeight()
                    ) {
                        Text("Confirm & Score Exam", fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = { showSubmitConfirmationDialog = false },
                        modifier = Modifier.minimumButtonHeight()
                    ) {
                        Text("Keep Reviewing", color = IndustrialColors.TextMuted)
                    }
                }
            )
        }
    }
}

// MARK: - Start Exam View
@Composable
private fun StartExamScreen(
    isPro: Boolean,
    onStartExam: () -> Unit,
    onUnlockPro: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .size(86.dp)
                    .clip(CircleShape)
                    .background(IndustrialColors.CyanAccent.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Assignment,
                    contentDescription = null,
                    tint = IndustrialColors.CyanAccent,
                    modifier = Modifier.size(42.dp)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "UNIVERSAL PROCTORED SIMULATOR",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.SafetyAmber,
                    letterSpacing = 2.sp
                )

                Text(
                    text = "Official 80-Question Exam",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary
                )

                Text(
                    text = "Accurately simulates the proctored EPA Section 608 Universal Certification testing environment under statutory time pressure.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = IndustrialColors.TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Informational Spec Rows
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                ExamSpecRow("Format", "80 Multiple Choice (20 per Section)")
                ExamSpecRow("Time Allowed", "120 Minutes (2 Hours)")
                ExamSpecRow("Passing Grade", "70% Minimum per Section")
                ExamSpecRow("2027 Standards", "A2L Refrigerants & AIM Act")
            }
        }

        // Action CTA
        Button(
            onClick = if (isPro) onStartExam else onUnlockPro,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = IndustrialColors.CyanAccent,
                contentColor = Color.Black
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!isPro) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = if (isPro) "Begin Timed Simulation →" else "Unlock Universal Exam Simulator",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun ExamSpecRow(title: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(IndustrialColors.CardBorder)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = IndustrialColors.TextSecondary
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.TextPrimary
            )
        }
    }
}

// MARK: - Active Exam View
@Composable
private fun ActiveExamScreen(
    questions: List<Question>,
    currentIndex: Int,
    remainingSeconds: Int,
    userAnswers: Map<String, Int>,
    flaggedQuestionIds: Set<String>,
    onQuestionChange: (Int) -> Unit,
    onOptionSelect: (String, Int) -> Unit,
    onFlagToggle: (String) -> Unit,
    onSubmitClick: () -> Unit
) {
    val currentQuestion = questions[currentIndex]
    val isFlagged = flaggedQuestionIds.contains(currentQuestion.id)
    val isWarningTime = remainingSeconds < 600 // Less than 10 minutes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Proctor Header Bar (Timer + Flag + Submit)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Countdown Timer
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Timer,
                    contentDescription = null,
                    tint = if (isWarningTime) IndustrialColors.HazardRed else IndustrialColors.SafetyAmber,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = formatTimerString(remainingSeconds),
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = if (isWarningTime) IndustrialColors.HazardRed else IndustrialColors.TextPrimary
                    )
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val context = LocalContext.current
                val view = LocalView.current
                val engine = remember { EPAEngine.getInstance(context) }
                val isRooftop by engine.isRooftopMode.collectAsState()

                IconButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        engine.toggleRooftopMode()
                    },
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        imageVector = if (isRooftop) Icons.Default.WbSunny else Icons.Default.BrightnessMedium,
                        contentDescription = "Toggle Rooftop Sunlight Mode",
                        tint = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Flag Button (>= 48x48dp touch target)
                OutlinedButton(
                    onClick = { onFlagToggle(currentQuestion.id) },
                    modifier = Modifier.minimumButtonHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isFlagged) IndustrialColors.HazardRed.copy(alpha = 0.15f)
                        else IndustrialColors.Navy
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (isFlagged) IndustrialColors.HazardRed else IndustrialColors.CardBorder
                    )
                ) {
                    Icon(
                        imageVector = if (isFlagged) Icons.Default.Flag else Icons.Default.OutlinedFlag,
                        contentDescription = "Flag Question",
                        tint = if (isFlagged) IndustrialColors.HazardRed else IndustrialColors.TextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isFlagged) "Flagged" else "Flag",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isFlagged) IndustrialColors.HazardRed else IndustrialColors.TextSecondary
                    )
                }

                // Submit Button (>= 48x48dp touch target)
                Button(
                    onClick = onSubmitClick,
                    modifier = Modifier.minimumButtonHeight(),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.SafetyAmber,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = "Submit",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Question Progress Tracker
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Q${currentIndex + 1} of ${questions.size}",
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = IndustrialColors.TextSecondary
                )
            )

            Text(
                text = currentQuestion.section.rawValue,
                style = TextStyle(
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = IndustrialColors.sectionColor(currentQuestion.section)
                )
            )
        }

        // Scrollable Stem & Options
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = currentQuestion.questionText,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.TextPrimary,
                lineHeight = 26.sp
            )

            val optionLetters = listOf("A", "B", "C", "D")
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                currentQuestion.options.forEachIndexed { optIdx, optText ->
                    val isSelected = userAnswers[currentQuestion.id] == optIdx

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumButtonHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(
                                role = Role.RadioButton,
                                onClick = { onOptionSelect(currentQuestion.id, optIdx) }
                            ),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) IndustrialColors.ElevatedSurface
                            else IndustrialColors.Navy
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
                                .padding(14.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(
                                        if (isSelected) IndustrialColors.CyanAccent
                                        else IndustrialColors.ElevatedSurface
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = optionLetters.getOrElse(optIdx) { "$optIdx" },
                                    style = IndustrialTechnicalText.OptionLetter,
                                    color = if (isSelected) Color.Black else IndustrialColors.TextSecondary
                                )
                            }

                            Text(
                                text = optText,
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (isSelected) IndustrialColors.TextPrimary else IndustrialColors.TextSecondary,
                                modifier = Modifier.weight(1f),
                                lineHeight = 22.sp
                            )
                        }
                    }
                }
            }
        }

        // Bottom Navigation Bar (Previous / Next / Finish)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { onQuestionChange(currentIndex - 1) },
                enabled = currentIndex > 0,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = IndustrialColors.Navy),
                border = BorderStroke(
                    width = 1.dp,
                    color = IndustrialColors.CardBorder
                )
            ) {
                Text(
                    text = "← Previous",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (currentIndex > 0) IndustrialColors.TextPrimary else IndustrialColors.TextMuted
                )
            }

            Button(
                onClick = {
                    if (currentIndex + 1 < questions.size) {
                        onQuestionChange(currentIndex + 1)
                    } else {
                        onSubmitClick()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndustrialColors.CyanAccent,
                    contentColor = Color.Black
                )
            ) {
                Text(
                    text = if (currentIndex + 1 < questions.size) "Next →" else "Finish",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

// MARK: - Modular Section Scoring Exam Result Screen
@Composable
private fun ExamResultScreen(
    attempt: ExamAttempt,
    modularScores: List<ModularSectionScore>,
    onReturn: () -> Unit,
    onNavigateToCertificate: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(
                    if (attempt.passed) IndustrialColors.PassGreen.copy(alpha = 0.15f)
                    else IndustrialColors.HazardRed.copy(alpha = 0.15f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (attempt.passed) Icons.Default.Verified else Icons.Default.Warning,
                contentDescription = null,
                tint = if (attempt.passed) IndustrialColors.PassGreen else IndustrialColors.HazardRed,
                modifier = Modifier.size(54.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (attempt.passed) "EPA Universal Certified" else "Examination Not Cleared",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = IndustrialColors.TextPrimary,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (attempt.passed)
                    "Congratulations! You met the statutory 70% passing threshold across Core, Type I, Type II, and Type III."
                else
                    "You achieved ${attempt.score.toInt()}% overall. Federal EPA Universal regulations mandate achieving at least 70% in all four disciplines individually.",
                style = MaterialTheme.typography.bodyMedium,
                color = IndustrialColors.TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
        }

        // Modular Breakdown Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "MODULAR DISCIPLINE BREAKDOWN",
                style = MaterialTheme.typography.labelMedium,
                color = IndustrialColors.TextMuted,
                letterSpacing = 1.sp
            )

            modularScores.forEach { sectionScore ->
                ModularScoreRow(sectionScore)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        if (attempt.passed) {
            Button(
                onClick = onNavigateToCertificate,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD97706),
                    contentColor = Color.Black
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Claim & Share Official Certificate 🎖️",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black
                )
            }
        }

        Button(
            onClick = onReturn,
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
                text = "Return to Simulator",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ModularScoreRow(score: ModularSectionScore) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = IndustrialColors.Navy),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(
                if (score.passed) IndustrialColors.PassGreen.copy(alpha = 0.5f)
                else IndustrialColors.HazardRed.copy(alpha = 0.5f)
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = score.section.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
                Text(
                    text = "${score.correctCount} of ${score.totalCount} correct",
                    style = MaterialTheme.typography.bodySmall,
                    color = IndustrialColors.TextSecondary
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "${score.scorePercentage.toInt()}%",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = if (score.passed) IndustrialColors.PassGreen else IndustrialColors.HazardRed
                    )
                )

                Text(
                    text = if (score.passed) "PASS" else "FAIL",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Black,
                    color = if (score.passed) IndustrialColors.PassGreen else IndustrialColors.HazardRed,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(
                            if (score.passed) IndustrialColors.PassGreen.copy(alpha = 0.15f)
                            else IndustrialColors.HazardRed.copy(alpha = 0.15f)
                        )
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// MARK: - Evaluation & Scoring Logic
private fun finishExam(
    questions: List<Question>,
    userAnswers: Map<String, Int>,
    elapsedSeconds: Int,
    engine: EPAEngine,
    onResult: (ExamAttempt, List<ModularSectionScore>) -> Unit
) {
    val sections = listOf(EPASection.CORE, EPASection.TYPE_I, EPASection.TYPE_II, EPASection.TYPE_III)
    val modularList = mutableListOf<ModularSectionScore>()

    var allSectionsPassed = true
    var totalCorrect = 0

    for (sec in sections) {
        val secQuestions = questions.filter { it.section == sec }
        val totalCount = secQuestions.size
        var correctCount = 0

        for (q in secQuestions) {
            val selected = userAnswers[q.id]
            if (selected != null && q.isCorrect(selected)) {
                correctCount++
            }
        }

        val pct = if (totalCount > 0) (correctCount.toDouble() / totalCount.toDouble()) * 100.0 else 0.0
        val isPassed = pct >= 70.0 // 70% modular threshold

        if (!isPassed) {
            allSectionsPassed = false
        }
        totalCorrect += correctCount

        modularList.add(
            ModularSectionScore(
                section = sec,
                correctCount = correctCount,
                totalCount = totalCount,
                scorePercentage = pct,
                passed = isPassed
            )
        )
    }

    val totalPercentage = if (questions.isNotEmpty()) {
        (totalCorrect.toDouble() / questions.size.toDouble()) * 100.0
    } else 0.0

    val attempt = ExamAttempt(
        section = EPASection.UNIVERSAL,
        score = totalPercentage,
        passed = allSectionsPassed,
        timeElapsedSeconds = elapsedSeconds
    )

    engine.recordAttempt(attempt)
    onResult(attempt, modularList)
}

private fun formatTimerString(seconds: Int): String {
    val hrs = seconds / 3600
    val mins = (seconds % 3600) / 60
    val secs = seconds % 60
    return String.format("%02d:%02d:%02d", hrs, mins, secs)
}
