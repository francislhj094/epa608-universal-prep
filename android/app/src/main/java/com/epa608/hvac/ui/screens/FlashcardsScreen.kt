package com.epa608.hvac.ui.screens

import android.view.HapticFeedbackConstants
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.data.HVACQuestionBank
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.domain.Question
import com.epa608.hvac.ui.theme.IndustrialColors
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

// MARK: - Deck Filter Mode
enum class FlashcardDeckFilter(val label: String) {
    ALL("All (410)"),
    CORE("Core (100)"),
    TYPE_I("Type I (100)"),
    TYPE_II("Type II (100)"),
    TYPE_III("Type III (100)"),
    SAVED("Saved")
}

/**
 * 7-Figure Swipeable Flashcards Mode ("Truck Break Drills").
 *
 * Implements:
 * 1. 3D Card Flip Animation (Y-axis 180° rotation with perspective).
 * 2. Gesture-based drag-to-dismiss (Swipe Right = Mastered, Swipe Left = Still Learning).
 * 3. Deck filtering by EPA section (All, Core, Type I, Type II, Type III, Saved).
 * 4. Shuffle & Bookmark quick actions.
 * 5. Full subterranean offline capability with zero latency.
 * 6. High-contrast industrial WCAG AAA color scheme.
 */
@Composable
fun FlashcardsScreen(
    onDismiss: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    initialFilter: FlashcardDeckFilter = FlashcardDeckFilter.ALL,
    initialFlipped: Boolean = false,
    initialCompleted: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }
    val scope = rememberCoroutineScope()

    val bookmarkedIds by engine.bookmarkedQuestionIds.collectAsState()

    var selectedFilter by remember { mutableStateOf(initialFilter) }
    var currentCardIndex by remember { mutableIntStateOf(0) }
    var isFlipped by remember { mutableStateOf(initialFlipped) }

    // Cards list for the active deck
    val activeDeck = remember { mutableStateListOf<Question>() }
    val stillLearningList = remember { mutableStateListOf<Question>() }
    val masteredList = remember { mutableStateListOf<Question>() }

    fun reloadDeck(filter: FlashcardDeckFilter, keepFlipped: Boolean = false) {
        val pool = when (filter) {
            FlashcardDeckFilter.ALL -> HVACQuestionBank.studyQuestions
            FlashcardDeckFilter.CORE -> HVACQuestionBank.coreQuestions
            FlashcardDeckFilter.TYPE_I -> HVACQuestionBank.type1Questions
            FlashcardDeckFilter.TYPE_II -> HVACQuestionBank.type2Questions
            FlashcardDeckFilter.TYPE_III -> HVACQuestionBank.type3Questions
            FlashcardDeckFilter.SAVED -> {
                val saved = HVACQuestionBank.studyQuestions.filter { bookmarkedIds.contains(it.id) }
                if (saved.isNotEmpty()) saved else HVACQuestionBank.coreQuestions
            }
        }
        activeDeck.clear()
        activeDeck.addAll(pool.shuffled())
        stillLearningList.clear()
        masteredList.clear()
        currentCardIndex = 0
        if (!keepFlipped) {
            isFlipped = false
        }
    }

    LaunchedEffect(selectedFilter) {
        reloadDeck(selectedFilter, keepFlipped = initialFlipped)
    }

    LaunchedEffect(initialFlipped) {
        isFlipped = initialFlipped
    }

    LaunchedEffect(initialCompleted, activeDeck.size) {
        if (initialCompleted && activeDeck.isNotEmpty()) {
            masteredList.clear()
            stillLearningList.clear()
            masteredList.addAll(activeDeck.take(28))
            stillLearningList.addAll(activeDeck.drop(28).take(7))
            currentCardIndex = activeDeck.size
        }
    }

    // Swipe animation state
    val dragOffsetX = remember { Animatable(0f) }
    val dragOffsetY = remember { Animatable(0f) }

    // 3D Flip animation
    val flipRotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing),
        label = "cardFlip"
    )

    val currentQuestion = activeDeck.getOrNull(currentCardIndex)
    val isDeckFinished = currentCardIndex >= activeDeck.size && activeDeck.isNotEmpty()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = IndustrialColors.Charcoal
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // MARK: - Top App Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                        onDismiss()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back to Hub",
                        tint = IndustrialColors.TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "TRUCK BREAK FLASHCARDS",
                        style = MaterialTheme.typography.labelSmall,
                        color = IndustrialColors.SafetyAmber,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = if (!isDeckFinished && activeDeck.isNotEmpty()) {
                            "Card ${currentCardIndex + 1} of ${activeDeck.size}"
                        } else {
                            "Deck Completed"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = IndustrialColors.TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row {
                    val isRooftop by engine.isRooftopMode.collectAsState()
                    IconButton(
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            engine.toggleRooftopMode()
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = if (isRooftop) Icons.Default.WbSunny else Icons.Default.BrightnessMedium,
                            contentDescription = "Toggle Rooftop Sunlight Mode",
                            tint = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary
                        )
                    }

                    // Shuffle button
                    IconButton(
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            reloadDeck(selectedFilter)
                        },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle Deck",
                            tint = IndustrialColors.CyanAccent
                        )
                    }

                    // Bookmark toggle
                    if (currentQuestion != null) {
                        val isBookmarked = bookmarkedIds.contains(currentQuestion.id)
                        IconButton(
                            onClick = {
                                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                                engine.toggleBookmark(currentQuestion.id)
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                contentDescription = "Bookmark",
                                tint = if (isBookmarked) IndustrialColors.SafetyAmber else IndustrialColors.TextMuted
                            )
                        }
                    }
                }
            }

            // MARK: - Section Filter Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedFilter.ordinal,
                containerColor = IndustrialColors.Charcoal,
                contentColor = IndustrialColors.CyanAccent,
                edgePadding = 16.dp,
                divider = {}
            ) {
                FlashcardDeckFilter.values().forEach { filter ->
                    val isSelected = selectedFilter == filter
                    Tab(
                        selected = isSelected,
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            selectedFilter = filter
                        },
                        text = {
                            Text(
                                text = filter.label,
                                color = if (isSelected) IndustrialColors.CyanAccent else IndustrialColors.TextMuted,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }

            // Linear Progress Indicator
            val progress = if (activeDeck.isNotEmpty()) {
                (currentCardIndex.toFloat() / activeDeck.size.toFloat()).coerceIn(0f, 1f)
            } else 0f

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = IndustrialColors.CyanAccent,
                trackColor = IndustrialColors.ElevatedSurface,
                strokeCap = StrokeCap.Round
            )

            // MARK: - Flashcard Body Area
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                if (isDeckFinished || activeDeck.isEmpty()) {
                    DeckCompletedView(
                        totalCards = activeDeck.size,
                        masteredCount = masteredList.size,
                        stillLearningCount = stillLearningList.size,
                        onReviewLearning = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            activeDeck.clear()
                            activeDeck.addAll(stillLearningList)
                            stillLearningList.clear()
                            masteredList.clear()
                            currentCardIndex = 0
                            isFlipped = false
                        },
                        onRestartAll = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            reloadDeck(selectedFilter)
                        },
                        onDone = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            onDismiss()
                        }
                    )
                } else if (currentQuestion != null) {
                    // Action Handlers
                    fun advanceCard(mastered: Boolean) {
                        view.performHapticFeedback(
                            if (mastered) HapticFeedbackConstants.VIRTUAL_KEY else HapticFeedbackConstants.CONFIRM
                        )
                        if (mastered) {
                            masteredList.add(currentQuestion)
                            engine.recordAnswer(currentQuestion.id, true)
                        } else {
                            stillLearningList.add(currentQuestion)
                            engine.recordAnswer(currentQuestion.id, false)
                        }

                        scope.launch {
                            val targetX = if (mastered) 1200f else -1200f
                            dragOffsetX.animateTo(targetX, tween(250))
                            currentCardIndex++
                            isFlipped = false
                            dragOffsetX.snapTo(0f)
                            dragOffsetY.snapTo(0f)
                        }
                    }

                    // Interactive Swipeable Card with 3D Flip
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .offset {
                                IntOffset(
                                    dragOffsetX.value.roundToInt(),
                                    dragOffsetY.value.roundToInt()
                                )
                            }
                            .graphicsLayer {
                                rotationZ = (dragOffsetX.value / 35f).coerceIn(-25f, 25f)
                                cameraDistance = 16 * density
                            }
                            .pointerInput(currentCardIndex) {
                                detectHorizontalDragGestures(
                                    onDragEnd = {
                                        if (dragOffsetX.value > 250f) {
                                            advanceCard(mastered = true)
                                        } else if (dragOffsetX.value < -250f) {
                                            advanceCard(mastered = false)
                                        } else {
                                            scope.launch {
                                                dragOffsetX.animateTo(0f, tween(200))
                                            }
                                        }
                                    },
                                    onHorizontalDrag = { _, dragAmount ->
                                        scope.launch {
                                            dragOffsetX.snapTo(dragOffsetX.value + dragAmount)
                                        }
                                    }
                                )
                            }
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = {
                                    view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                                    isFlipped = !isFlipped
                                }
                            )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    rotationY = flipRotation
                                    cameraDistance = 16 * density
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = IndustrialColors.Navy
                            ),
                            shape = RoundedCornerShape(20.dp),
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = androidx.compose.ui.graphics.SolidColor(
                                    if (isFlipped) IndustrialColors.PassGreen.copy(alpha = 0.5f)
                                    else IndustrialColors.CardBorder
                                )
                            )
                        ) {
                            if (flipRotation <= 90f) {
                                // Front Face
                                FlashcardFrontView(
                                    question = currentQuestion,
                                    cardIndex = currentCardIndex + 1,
                                    totalCount = activeDeck.size
                                )
                            } else {
                                // Back Face (Rotated 180° so text is not reversed)
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .graphicsLayer { rotationY = 180f }
                                ) {
                                    FlashcardBackView(
                                        question = currentQuestion
                                    )
                                }
                            }
                        }

                        // Drag Stamp Overlay (STILL LEARNING vs KNOW IT)
                        val dragX = dragOffsetX.value
                        if (abs(dragX) > 60f) {
                            val isRight = dragX > 0f
                            val stampColor = if (isRight) IndustrialColors.PassGreen else IndustrialColors.HazardRed
                            val stampText = if (isRight) "I KNOW THIS ✓" else "STILL LEARNING ✗"
                            val alpha = (abs(dragX) / 300f).coerceIn(0f, 0.95f)

                            Box(
                                modifier = Modifier
                                    .align(if (isRight) Alignment.TopStart else Alignment.TopEnd)
                                    .padding(24.dp)
                                    .graphicsLayer { rotationZ = if (isRight) -15f else 15f }
                                    .border(3.dp, stampColor.copy(alpha = alpha), RoundedCornerShape(8.dp))
                                    .background(stampColor.copy(alpha = alpha * 0.2f))
                                    .padding(horizontal = 14.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = stampText,
                                    color = stampColor.copy(alpha = alpha),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 1.sp
                                )
                            }
                        }
                    }
                }
            }

            // MARK: - Bottom Tactile Controls (Only visible during active cards)
            if (!isDeckFinished && currentQuestion != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Red: Still Learning Button (Swipe Left Action)
                    Button(
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                            stillLearningList.add(currentQuestion)
                            engine.recordAnswer(currentQuestion.id, false)
                            scope.launch {
                                dragOffsetX.animateTo(-1200f, tween(250))
                                currentCardIndex++
                                isFlipped = false
                                dragOffsetX.snapTo(0f)
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndustrialColors.HazardRed.copy(alpha = 0.15f),
                            contentColor = IndustrialColors.HazardRed
                        ),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(2.dp, IndustrialColors.HazardRed.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Still Learning",
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    // Center: Flip Action Button
                    Button(
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            isFlipped = !isFlipped
                        },
                        modifier = Modifier
                            .height(52.dp)
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndustrialColors.ElevatedSurface,
                            contentColor = IndustrialColors.TextPrimary
                        ),
                        shape = RoundedCornerShape(26.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, IndustrialColors.CardBorder)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Flip",
                                tint = IndustrialColors.CyanAccent,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = if (isFlipped) "Show Question" else "Tap to Flip",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Green: I Know This Button (Swipe Right Action)
                    Button(
                        onClick = {
                            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
                            masteredList.add(currentQuestion)
                            engine.recordAnswer(currentQuestion.id, true)
                            scope.launch {
                                dragOffsetX.animateTo(1200f, tween(250))
                                currentCardIndex++
                                isFlipped = false
                                dragOffsetX.snapTo(0f)
                            }
                        },
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = IndustrialColors.PassGreen.copy(alpha = 0.15f),
                            contentColor = IndustrialColors.PassGreen
                        ),
                        shape = CircleShape,
                        border = androidx.compose.foundation.BorderStroke(2.dp, IndustrialColors.PassGreen.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "I Know This",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }
        }
    }
}

// MARK: - Card Front View
@Composable
private fun FlashcardFrontView(
    question: Question,
    cardIndex: Int,
    totalCount: Int
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Badges Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Section Badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(IndustrialColors.CyanAccent.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = question.section.rawValue.uppercase(),
                        color = IndustrialColors.CyanAccent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                // A2L Badge
                if (question.isA2L) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(IndustrialColors.SafetyAmber.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "A2L AIM ACT",
                            color = IndustrialColors.SafetyAmber,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Question Stem
            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleLarge,
                color = IndustrialColors.TextPrimary,
                fontWeight = FontWeight.Bold,
                lineHeight = 32.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Tap to Flip Prompt
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(IndustrialColors.ElevatedSurface)
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = IndustrialColors.TextMuted,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = "Tap card or button to reveal answer",
                color = IndustrialColors.TextMuted,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// MARK: - Card Back View
@Composable
private fun FlashcardBackView(
    question: Question
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            // Header: Correct Answer Badge
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(IndustrialColors.PassGreen),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Text(
                    text = "STATUTORY CORRECT ANSWER",
                    color = IndustrialColors.PassGreen,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            // Correct Answer Text
            Text(
                text = question.correctAnswerText,
                style = MaterialTheme.typography.titleMedium,
                color = IndustrialColors.TextPrimary,
                fontWeight = FontWeight.Black,
                lineHeight = 26.sp
            )

            // Technical Rationale & Thermodynamics
            Text(
                text = question.explanation,
                style = MaterialTheme.typography.bodyMedium,
                color = IndustrialColors.TextSecondary,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Official Citation Footer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(IndustrialColors.ElevatedSurface)
                .border(1.dp, IndustrialColors.CardBorder, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = "LEGAL CITATION: ${question.citation}",
                color = IndustrialColors.CyanAccent,
                fontSize = 12.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// MARK: - Deck Completed View
@Composable
private fun DeckCompletedView(
    totalCards: Int,
    masteredCount: Int,
    stillLearningCount: Int,
    onReviewLearning: () -> Unit,
    onRestartAll: () -> Unit,
    onDone: () -> Unit
) {
    val reviewedTotal = masteredCount + stillLearningCount
    val retentionPct = if (reviewedTotal > 0) {
        ((masteredCount.toFloat() / reviewedTotal.toFloat()) * 100f).roundToInt()
    } else if (totalCards > 0) {
        ((masteredCount.toFloat() / totalCards.toFloat()) * 100f).roundToInt()
    } else 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Large Circular Retention Gauge
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(IndustrialColors.Navy)
                .border(
                    width = 4.dp,
                    color = if (retentionPct >= 70) IndustrialColors.PassGreen else IndustrialColors.SafetyAmber,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "$retentionPct%",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary
                )
                Text(
                    text = "RETENTION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Truck Drill Complete!",
            style = MaterialTheme.typography.headlineSmall,
            color = IndustrialColors.TextPrimary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$masteredCount Mastered · $stillLearningCount Still Learning",
            style = MaterialTheme.typography.bodyMedium,
            color = IndustrialColors.TextSecondary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (stillLearningCount > 0) {
                Button(
                    onClick = onReviewLearning,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = IndustrialColors.SafetyAmber,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Review Still Learning ($stillLearningCount)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = onRestartAll,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndustrialColors.CyanAccent,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Restart Full Deck",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onDone,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndustrialColors.ElevatedSurface,
                    contentColor = IndustrialColors.TextPrimary
                ),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, IndustrialColors.CardBorder)
            ) {
                Text(
                    text = "Return to Technician Hub",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
