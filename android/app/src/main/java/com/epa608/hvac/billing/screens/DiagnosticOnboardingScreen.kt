package com.epa608.hvac.billing.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.epa608.hvac.data.HVACQuestionBank
import com.epa608.hvac.domain.DiagnosticResult
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question

// MARK: - Theme Color Constants (WCAG AAA Compliant Industrial Field Theme)
private object HvacColors {
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

    fun sectionColor(section: EPASection): Color {
        return when (section) {
            EPASection.CORE -> Color(0xFF38BDF8)
            EPASection.TYPE_I -> Color(0xFF2DD4BF)
            EPASection.TYPE_II -> Color(0xFF4ADE80)
            EPASection.TYPE_III -> Color(0xFFC084FC)
            EPASection.UNIVERSAL -> Color(0xFFFBBF24)
        }
    }
}

/**
 * 10-Question Diagnostic Shock Test Screen in Jetpack Compose.
 *
 * Implements:
 * 1. Step 0: Welcome screen introducing AIM Act 2026/2027 A2L mandate.
 * 2. Steps 1-10: Timed diagnostic questions covering Core, Type I, Type II, and Type III.
 * 3. Step 11: Shock Failure Evaluation Screen demonstrating the loss aversion
 *    of failing a $150+ testing fee and providing 1-click upgrade to the 100% Pass Guarantee Paywall.
 */
@Composable
fun DiagnosticOnboardingScreen(
    onDismiss: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }
    var currentStep by remember { mutableIntStateOf(0) } // 0 = Welcome, 1..10 = Questions, 11 = Result
    val selectedAnswers = remember { mutableStateMapOf<String, Int>() }
    var diagnosticResult by remember { mutableStateOf<DiagnosticResult?>(null) }
    var showPaywallDialog by remember { mutableStateOf(false) }

    val questions = remember { HVACQuestionBank.diagnosticQuestions }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(HvacColors.Background)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        when {
            currentStep == 0 -> {
                WelcomeView(
                    onStartClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        currentStep = 1
                    }
                )
            }
            currentStep in 1..questions.size -> {
                val currentQuestion = questions[currentStep - 1]
                QuestionView(
                    stepIndex = currentStep,
                    totalSteps = questions.size,
                    question = currentQuestion,
                    selectedOption = selectedAnswers[currentQuestion.id],
                    onOptionSelected = { optionIdx ->
                        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                        selectedAnswers[currentQuestion.id] = optionIdx
                    },
                    onNextClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        if (currentStep < questions.size) {
                            currentStep++
                        } else {
                            // Evaluate Diagnostic Score via EPAEngine and persist
                            val result = engine.evaluateDiagnostic(selectedAnswers)
                            diagnosticResult = result
                            currentStep = questions.size + 1
                        }
                    }
                )
            }
            currentStep > questions.size && diagnosticResult != null -> {
                ResultsView(
                    result = diagnosticResult!!,
                    onUnlockGuarantee = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                        engine.markDiagnosticCompleted()
                        onNavigateToPaywall()
                    },
                    onContinueFree = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        engine.markDiagnosticCompleted()
                        onDismiss()
                    }
                )
            }
        }

        // Full Screen Paywall Sheet / Dialog
        if (showPaywallDialog) {
            Dialog(
                onDismissRequest = { showPaywallDialog = false },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    decorFitsSystemWindows = false
                )
            ) {
                PaywallScreen(
                    onDismiss = { showPaywallDialog = false }
                )
            }
        }
    }
}

// MARK: - Step 0: Welcome Screen
@Composable
private fun WelcomeView(
    onStartClick: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Hero Gauge Icon with Circular Aura
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(HvacColors.CardSurface)
                .border(2.dp, HvacColors.CyanAccent.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            GaugeVectorIcon(
                modifier = Modifier.size(48.dp),
                color = HvacColors.CyanAccent
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Headline & Badging
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "2027 EPA 608 HVAC",
                color = HvacColors.SafetyAmber,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Text(
                text = "Official Diagnostic Test",
                color = HvacColors.TextPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = "10 verified questions testing 2026/2027 AIM Act A2L refrigerants, leak trigger thresholds, and evacuation levels.",
                color = HvacColors.TextSecondary,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Metric Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            MetricPill(
                title = "Questions",
                value = "10",
                modifier = Modifier.weight(1f)
            )
            MetricPill(
                title = "Passing Score",
                value = "70%",
                modifier = Modifier.weight(1f)
            )
            MetricPill(
                title = "Time Limit",
                value = "5 Min",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        // Start CTA Button (56dp height touch target)
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HvacColors.CyanAccent,
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                text = "Start Free Diagnostic Test",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun MetricPill(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = HvacColors.CardSurface),
        shape = RoundedCornerShape(12.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(HvacColors.CardBorder))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = value,
                color = HvacColors.CyanAccent,
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                fontFamily = FontFamily.Monospace
            )
            Text(
                text = title,
                color = HvacColors.TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// MARK: - Steps 1-10: Question View
@Composable
private fun QuestionView(
    stepIndex: Int,
    totalSteps: Int,
    question: Question,
    selectedOption: Int?,
    onOptionSelected: (Int) -> Unit,
    onNextClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val progressAnim by animateFloatAsState(
        targetValue = stepIndex.toFloat() / totalSteps.toFloat(),
        animationSpec = tween(durationMillis = 300),
        label = "progress_anim"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Spacer(modifier = Modifier.height(16.dp))

            // Progress Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question $stepIndex of $totalSteps",
                    color = HvacColors.TextSecondary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = FontFamily.Monospace
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(HvacColors.sectionColor(question.section).copy(alpha = 0.2f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = question.section.rawValue,
                        color = HvacColors.sectionColor(question.section),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Animated Progress Bar
            LinearProgressIndicator(
                progress = { progressAnim },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = HvacColors.CyanAccent,
                trackColor = HvacColors.CardBorder,
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Question Stem & Options Scroll Area
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // A2L AIM Act Mandate Warning Flag
                if (question.isA2L) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(HvacColors.SafetyAmber.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        FlameVectorIcon(
                            modifier = Modifier.size(14.dp),
                            color = HvacColors.SafetyAmber
                        )
                        Text(
                            text = "2026/2027 AIM ACT MANDATE (A2L)",
                            color = HvacColors.SafetyAmber,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = question.questionText,
                    color = HvacColors.TextPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 26.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Options List
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    question.options.forEachIndexed { index, optionText ->
                        val isSelected = selectedOption == index
                        OptionCard(
                            text = optionText,
                            isSelected = isSelected,
                            onClick = { onOptionSelected(index) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Bottom Action Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Button(
                onClick = onNextClick,
                enabled = selectedOption != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HvacColors.CyanAccent,
                    contentColor = Color.Black,
                    disabledContainerColor = HvacColors.CardBorder,
                    disabledContentColor = HvacColors.TextSecondary.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (stepIndex < totalSteps) "Next Question →" else "Calculate My Score",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun OptionCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) HvacColors.ElevatedSurface else HvacColors.CardSurface)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) HvacColors.CyanAccent else HvacColors.CardBorder,
                shape = RoundedCornerShape(12.dp)
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
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Radio circle
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) HvacColors.CyanAccent else HvacColors.CardBorder,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(HvacColors.CyanAccent)
                    )
                }
            }

            Text(
                text = text,
                color = if (isSelected) HvacColors.TextPrimary else HvacColors.TextSecondary,
                fontSize = 15.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                lineHeight = 22.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

// MARK: - Step 11: Results Screen (The Shock Conversion Step)
@Composable
private fun ResultsView(
    result: DiagnosticResult,
    onUnlockGuarantee: () -> Unit,
    onContinueFree: () -> Unit
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Large Circular Gauge Badge
        val badgeColor = if (result.passed) HvacColors.PassGreen else HvacColors.HazardRed
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(badgeColor.copy(alpha = 0.15f))
                .border(2.dp, badgeColor.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "${result.scorePercentage.toInt()}%",
                    color = badgeColor,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = FontFamily.Monospace
                )
                Text(
                    text = if (result.passed) "PASSING" else "FAILED",
                    color = badgeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Loss-Aversion Psychological Shock Copy
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = if (result.passed) "Promising Baseline" else "Warning: Certification at Risk",
                color = HvacColors.TextPrimary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (result.passed) {
                    "You cleared the 70% bar, but proctored exams contain 80 questions with higher variance."
                } else {
                    "You scored ${result.scorePercentage.toInt()}%. The official EPA passing score is 70%. Failing your exam wastes $150+ in testing fees and delays your commercial career."
                },
                color = HvacColors.TextSecondary,
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Critical Knowledge Gaps Callout
        if (result.weakSections.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = HvacColors.CardSurface),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(HvacColors.CardBorder))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "CRITICAL KNOWLEDGE GAPS DETECTED:",
                        color = HvacColors.SafetyAmber,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )

                    result.weakSections.forEach { section ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Knowledge Gap",
                                tint = HvacColors.HazardRed,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = section.title,
                                color = HvacColors.TextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(32.dp))

        // Bottom CTAs
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = onUnlockGuarantee,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HvacColors.CyanAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ShieldCheckVectorIcon(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black
                    )
                    Text(
                        text = "Unlock 100% Pass Guarantee →",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onContinueFree)
                    .padding(vertical = 12.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = "Continue to Free Study Hub",
                    color = HvacColors.TextSecondary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

// MARK: - 10-Question Official Diagnostic Bank
object DiagnosticQuestionsBank {

    val questions: List<Question> = listOf(
        Question(
            id = "diag-1",
            section = EPASection.CORE,
            questionText = "Under the EPA AIM Act (40 CFR Part 84) and AHRI Guideline N, how are ASHRAE A2L mildly flammable refrigerants (such as R-454B and R-32) distinguished on recovery cylinders?",
            options = listOf(
                "Recovery cylinders must have a solid red top with blue lettering",
                "Recovery cylinders feature a red band/shoulder and left-handed reverse valve threads (CGA-164)",
                "A2L cylinders are identical to standard cylinders and require no physical distinction",
                "A2L refrigerants cannot be recovered into cylinders and must be vented directly"
            ),
            correctIndex = 1,
            explanation = "Per DOT and EPA guidelines under 40 CFR Part 84 and CGA-164, A2L recovery cylinders feature a red shoulder/band and left-hand (reverse) threads to prevent cross-contamination with A1 equipment.",
            citation = "40 CFR § 84.54 / CGA-164",
            isA2L = true
        ),
        Question(
            id = "diag-2",
            section = EPASection.CORE,
            questionText = "What is the primary atmospheric hazard caused by chlorine atoms released from CFC and HCFC molecules?",
            options = listOf(
                "They increase atmospheric humidity, leading to acid rain",
                "A single chlorine atom can catalytically destroy up to 100,000 ozone molecules in the stratosphere",
                "They combine with carbon dioxide to deplete ground-level oxygen",
                "They react with nitrogen to create toxic photochemical smog"
            ),
            correctIndex = 1,
            explanation = "Chlorine in CFCs and HCFCs does not rain out because it is insoluble in water. Upon reaching the stratosphere, UV rays break off chlorine atoms, each capable of breaking apart up to 100,000 ozone (O3) molecules.",
            citation = "Clean Air Act Title VI / 40 CFR Part 82",
            isA2L = false
        ),
        Question(
            id = "diag-3",
            section = EPASection.TYPE_I,
            questionText = "When using a self-contained (active) recovery unit on a small appliance with an inoperative compressor, what percentage of refrigerant must be recovered?",
            options = listOf(
                "70% of the factory nameplate charge",
                "80% of the factory nameplate charge (or 4 inches of Hg vacuum)",
                "90% of the factory nameplate charge (or 4 inches of Hg vacuum)",
                "100% of the factory nameplate charge with zero remaining vapor"
            ),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.156(a)(2), if the small appliance compressor is NOT operational, technicians must recover at least 80% of the charge (or pull a 4-inch Hg vacuum). If the compressor runs, the threshold is 90%.",
            citation = "40 CFR § 82.156(a)(2)",
            isA2L = false
        ),
        Question(
            id = "diag-4",
            section = EPASection.TYPE_I,
            questionText = "Why must temporary piercing saddle valves be removed or permanently brazed over after completing service on a small appliance?",
            options = listOf(
                "Because they alter the internal electrical resistance of the compressor motor",
                "Because piercing valve rubber gaskets degrade and develop chronic leaks over time",
                "Because EPA regulations prohibit piercing valves from ever being used in the field",
                "Because they restrict the flow of refrigerant during normal operation"
            ),
            correctIndex = 1,
            explanation = "Solderless piercing valves rely on neoprene/rubber gaskets that dry out, harden, and leak refrigerant into the atmosphere over time. EPA rules mandate they be removed or sealed after servicing.",
            citation = "40 CFR § 82.156",
            isA2L = false
        ),
        Question(
            id = "diag-5",
            section = EPASection.TYPE_II,
            questionText = "Under current EPA Section 608 rules (40 CFR § 82.157(c)(2)), what is the annual leak rate trigger threshold for Commercial Refrigeration systems containing 50 or more pounds of refrigerant?",
            options = listOf(
                "10%",
                "20%",
                "30%",
                "35%"
            ),
            correctIndex = 1,
            explanation = "Under 40 CFR § 82.157(c)(2)(ii), the threshold for commercial refrigeration containing 50+ lbs is 20%. Comfort cooling is 10%, and industrial process refrigeration is 30%.",
            citation = "40 CFR § 82.157(c)(2)(ii)",
            isA2L = false
        ),
        Question(
            id = "diag-6",
            section = EPASection.TYPE_II,
            questionText = "Under 40 CFR § 82.156 Table 1, prior to opening a high-pressure appliance containing LESS than 200 lbs of R-22 or R-410A for a major repair using post-1993 recovery equipment, what vacuum level must be achieved?",
            options = listOf(
                "0 inches of Hg vacuum (0 psig)",
                "4 inches of Hg vacuum",
                "10 inches of Hg vacuum",
                "15 inches of Hg vacuum"
            ),
            correctIndex = 0,
            explanation = "Table 1 in 40 CFR § 82.156 mandates 0 inches of Hg vacuum (0 psig) for high-pressure appliances containing less than 200 pounds of refrigerant serviced with post-1993 equipment. 10 inches is required only for systems containing 200+ lbs.",
            citation = "40 CFR § 82.156 Table 1",
            isA2L = false
        ),
        Question(
            id = "diag-7",
            section = EPASection.TYPE_II,
            questionText = "Which of the following operations constitutes a 'major repair' under EPA Section 608 definitions (40 CFR § 82.152)?",
            options = listOf(
                "Replacing a Schrader valve core",
                "Replacing a thermostatic expansion valve (TXV)",
                "Replacing the compressor, condenser, evaporator coil, or auxiliary heat exchanger",
                "Replacing a liquid line filter drier"
            ),
            correctIndex = 2,
            explanation = "EPA defines a 'major repair' strictly as the removal or replacement of the compressor, condenser, evaporator coil, or auxiliary heat exchanger coil.",
            citation = "40 CFR § 82.152",
            isA2L = false
        ),
        Question(
            id = "diag-8",
            section = EPASection.TYPE_III,
            questionText = "At what pressure does the safety rupture disc on a low-pressure centrifugal chiller discharge to prevent vessel explosion?",
            options = listOf(
                "5 psig",
                "15 psig",
                "30 psig",
                "50 psig"
            ),
            correctIndex = 1,
            explanation = "Low-pressure chillers are designed to operate in a vacuum and have thin evaporator shell walls. The standard rupture disc setting is strictly 15 psig (piped outdoors per ASHRAE 15).",
            citation = "ASHRAE Standard 15 / 40 CFR § 82.156",
            isA2L = false
        ),
        Question(
            id = "diag-9",
            section = EPASection.TYPE_III,
            questionText = "When recharging a low-pressure chiller (e.g. R-123) after a complete evacuation, why must refrigerant vapor be charged first before introducing liquid?",
            options = listOf(
                "Liquid refrigerant will ruin the oil filter element",
                "Liquid refrigerant expanding in a deep vacuum flash boils, dropping temperature below 32°F and freezing water inside the chiller tubes",
                "Vapor refrigerant is heavier than liquid refrigerant in low-pressure systems",
                "EPA regulations strictly prohibit charging liquid refrigerant into any HVAC system"
            ),
            correctIndex = 1,
            explanation = "Low-pressure liquid refrigerant boils at low temperatures under vacuum. Introducing liquid into a deep vacuum causes rapid flash boiling that drops temperatures below 32°F, freezing and bursting the water tubes.",
            citation = "40 CFR § 82.156(b)",
            isA2L = false
        ),
        Question(
            id = "diag-10",
            section = EPASection.CORE,
            questionText = "What DOT specification markings must appear on an EPA-approved refrigerant recovery cylinder?",
            options = listOf(
                "DOT-2P or DOT-2Q",
                "DOT-4BA or DOT-4BW",
                "DOT-3AL or DOT-3AA",
                "DOT-UN1978 only"
            ),
            correctIndex = 1,
            explanation = "Department of Transportation (DOT) regulations mandate that refillable refrigerant recovery cylinders meet DOT-4BA or DOT-4BW standards and undergo hydrostatic pressure recertification every 5 years.",
            citation = "49 CFR § 178.51 & 40 CFR § 82.158",
            isA2L = false
        )
    )

    fun evaluate(answers: Map<String, Int>, questionList: List<Question>): DiagnosticResult {
        var correctCount = 0
        val sectionTotals = mutableMapOf<EPASection, Pair<Int, Int>>() // correct, total

        for (q in questionList) {
            val isCorrect = answers[q.id] == q.correctIndex
            if (isCorrect) correctCount++

            val existing = sectionTotals[q.section] ?: Pair(0, 0)
            sectionTotals[q.section] = Pair(
                if (isCorrect) existing.first + 1 else existing.first,
                existing.second + 1
            )
        }

        val total = questionList.size
        val percentage = if (total > 0) (correctCount.toDouble() / total.toDouble()) * 100.0 else 0.0
        val passed = percentage >= DiagnosticResult.PASSING_THRESHOLD_PERCENT

        val weakSections = mutableListOf<EPASection>()
        for ((sec, score) in sectionTotals) {
            val secPct = if (score.second > 0) (score.first.toDouble() / score.second.toDouble()) * 100.0 else 0.0
            if (secPct < DiagnosticResult.PASSING_THRESHOLD_PERCENT) {
                weakSections.add(sec)
            }
        }

        return DiagnosticResult(
            scorePercentage = percentage,
            correctCount = correctCount,
            totalQuestions = total,
            passed = passed,
            weakSections = weakSections
        )
    }
}

// MARK: - Crisp Vector Graphic Drawing Helpers (Self-Contained Icons)
@Composable
private fun GaugeVectorIcon(modifier: Modifier = Modifier, color: Color) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val strokeWidth = size.width * 0.08f
        val radius = (size.minDimension - strokeWidth) / 2f
        val centerOffset = androidx.compose.ui.geometry.Offset(size.width / 2f, size.height / 2f)

        // Arc gauge
        drawArc(
            color = color,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )

        // Center needle pivot
        drawCircle(
            color = color,
            radius = size.width * 0.12f,
            center = centerOffset
        )

        // Needle
        val needleLength = radius * 0.75f
        val angleRad = Math.toRadians(210.0) // pointed past midpoint
        val endX = centerOffset.x + (needleLength * Math.cos(angleRad)).toFloat()
        val endY = centerOffset.y + (needleLength * Math.sin(angleRad)).toFloat()

        drawLine(
            color = color,
            start = centerOffset,
            end = androidx.compose.ui.geometry.Offset(endX, endY),
            strokeWidth = strokeWidth * 0.9f,
            cap = StrokeCap.Round
        )
    }
}

@Composable
private fun FlameVectorIcon(modifier: Modifier = Modifier, color: Color) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, 0f)
            cubicTo(w * 0.75f, h * 0.25f, w, h * 0.55f, w * 0.85f, h * 0.8f)
            cubicTo(w * 0.72f, h * 1.0f, w * 0.28f, h * 1.0f, w * 0.15f, h * 0.8f)
            cubicTo(0f, h * 0.55f, w * 0.25f, h * 0.25f, w * 0.5f, 0f)
            close()
        }
        drawPath(path = path, color = color)
    }
}

@Composable
private fun ShieldCheckVectorIcon(modifier: Modifier = Modifier, color: Color) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val shieldPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w, h * 0.2f)
            cubicTo(w, h * 0.65f, w * 0.5f, h * 0.95f, w * 0.5f, h)
            cubicTo(w * 0.5f, h * 0.95f, 0f, h * 0.65f, 0f, h * 0.2f)
            close()
        }
        drawPath(
            path = shieldPath,
            color = color,
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = w * 0.1f)
        )

        // Check mark inside
        val checkPath = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.3f, h * 0.5f)
            lineTo(w * 0.45f, h * 0.65f)
            lineTo(w * 0.7f, h * 0.35f)
        }
        drawPath(
            path = checkPath,
            color = color,
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = w * 0.1f,
                cap = StrokeCap.Round,
                join = androidx.compose.ui.graphics.StrokeJoin.Round
            )
        )
    }
}
