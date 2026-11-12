package com.epa608.hvac.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.billing.screens.DiagnosticOnboardingScreen
import com.epa608.hvac.billing.screens.PaywallScreen
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.ui.screens.CertificateScreen
import com.epa608.hvac.ui.screens.DashboardScreen
import com.epa608.hvac.ui.screens.ExamSimulatorScreen
import com.epa608.hvac.ui.screens.FieldToolsScreen
import com.epa608.hvac.ui.screens.FlashcardDeckFilter
import com.epa608.hvac.ui.screens.FlashcardsScreen
import com.epa608.hvac.ui.screens.QuickDrillMode
import com.epa608.hvac.ui.screens.QuizScreen
import com.epa608.hvac.ui.screens.SettingsScreen
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.minimumTouchTarget

/**
 * Bottom Navigation Tab Definition.
 */
sealed class NavTab(val title: String, val icon: ImageVector, val tag: Int) {
    data object Study : NavTab("Study", Icons.AutoMirrored.Filled.MenuBook, 0)
    data object ExamSim : NavTab("Exam Sim", Icons.AutoMirrored.Filled.Assignment, 1)
    data object FieldTools : NavTab("Field Tools", Icons.Default.Build, 2)
    data object Profile : NavTab("Profile", Icons.Default.Person, 3)

    companion object {
        val allTabs = listOf(Study, ExamSim, FieldTools, Profile)
    }
}

/**
 * Root Application Navigation for EPA 608 HVAC Practice Test Android.
 *
 * Implements:
 * 1. BottomNavigationBar with 4 core technician disciplines (Study, Exam Sim, Field Tools, Profile).
 * 2. Day-0 Diagnostic Onboarding launch condition matching Swift ContentView.onAppear.
 * 3. High-converting Paywall modal overlay.
 * 4. Active Quiz full-screen presentation with modular drill filters.
 * 5. Minimum 48x48dp touch targets on navigation items per Android Accessibility Guidelines.
 */
@Composable
fun AppNavigation(
    initialTab: Int = 0,
    initialSection: EPASection? = null,
    initialDrillMode: QuickDrillMode? = null,
    initialShowFlashcards: Boolean = false,
    initialFlashcardsFlipped: Boolean = false,
    initialFlashcardsFilter: FlashcardDeckFilter = FlashcardDeckFilter.ALL,
    initialFlashcardsCompleted: Boolean = false,
    initialShowCertificate: Boolean = false,
    autoStartExam: Boolean = false,
    autoSubmitExam: Boolean = false,
    autoCompleteQuiz: Boolean = false,
    fieldToolIndex: Int = 0,
    leakCharge: String = "120",
    leakAdded: String = "15",
    leakDays: String = "90",
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val engine = remember { EPAEngine.getInstance(context) }
    val hasCompletedDiagnostic by engine.hasCompletedDiagnostic.collectAsState()

    var selectedTab by remember { mutableIntStateOf(initialTab) }
    var showPaywall by remember { mutableStateOf(false) }
    var showDiagnosticOnboarding by remember { mutableStateOf(false) }
    var showFlashcards by remember { mutableStateOf(initialShowFlashcards) }
    var flashcardsFlipped by remember { mutableStateOf(initialFlashcardsFlipped) }
    var flashcardsFilter by remember { mutableStateOf(initialFlashcardsFilter) }
    var flashcardsCompleted by remember { mutableStateOf(initialFlashcardsCompleted) }
    var showCertificate by remember { mutableStateOf(initialShowCertificate) }

    // Active Quiz presentation state
    var activeQuizSection by remember { mutableStateOf<EPASection?>(initialSection) }
    var activeQuizDrillMode by remember { mutableStateOf<QuickDrillMode?>(initialDrillMode) }

    // Sync external navigation triggers
    LaunchedEffect(initialTab) {
        selectedTab = initialTab
    }
    LaunchedEffect(initialShowFlashcards, initialFlashcardsFlipped, initialFlashcardsFilter, initialFlashcardsCompleted) {
        if (initialShowFlashcards) {
            showFlashcards = true
            flashcardsFlipped = initialFlashcardsFlipped
            flashcardsFilter = initialFlashcardsFilter
            flashcardsCompleted = initialFlashcardsCompleted
        }
    }
    LaunchedEffect(initialShowCertificate) {
        if (initialShowCertificate) {
            showCertificate = true
        }
    }
    LaunchedEffect(initialSection, initialDrillMode) {
        if (initialSection != null || initialDrillMode != null) {
            activeQuizSection = initialSection ?: EPASection.CORE
            activeQuizDrillMode = initialDrillMode
        }
    }

    // Check Day-0 Diagnostic condition on first composition
    LaunchedEffect(hasCompletedDiagnostic) {
        if (!hasCompletedDiagnostic) {
            showDiagnosticOnboarding = true
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = IndustrialColors.Charcoal,
        bottomBar = {
            if (activeQuizSection == null && !showPaywall && !showDiagnosticOnboarding && !showFlashcards && !showCertificate) {
                IndustrialBottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = if (activeQuizSection == null && !showPaywall && !showDiagnosticOnboarding && !showFlashcards && !showCertificate) innerPadding.calculateBottomPadding() else 0.dp)
        ) {
            // Main 4 Tabs
            when (selectedTab) {
                0 -> DashboardScreen(
                    onNavigateToQuiz = { section, drillMode ->
                        activeQuizSection = section
                        activeQuizDrillMode = drillMode
                    },
                    onNavigateToFlashcards = { showFlashcards = true },
                    onNavigateToPaywall = { showPaywall = true },
                    onNavigateToCertificate = { showCertificate = true }
                )
                1 -> ExamSimulatorScreen(
                    onNavigateToPaywall = { showPaywall = true },
                    onNavigateToCertificate = { showCertificate = true },
                    autoStartForTesting = autoStartExam,
                    autoSubmitForTesting = autoSubmitExam
                )
                2 -> FieldToolsScreen(
                    initialToolIndex = fieldToolIndex,
                    initialCharge = leakCharge,
                    initialAdded = leakAdded,
                    initialDays = leakDays
                )
                3 -> SettingsScreen(
                    onNavigateToPaywall = { showPaywall = true },
                    onNavigateToCertificate = { showCertificate = true }
                )
            }

            // Full-screen Quiz Overlay
            if (activeQuizSection != null) {
                QuizScreen(
                    section = activeQuizSection!!,
                    drillMode = activeQuizDrillMode,
                    autoCompleteForTesting = autoCompleteQuiz,
                    onDismiss = {
                        activeQuizSection = null
                        activeQuizDrillMode = null
                    },
                    onNavigateToPaywall = { showPaywall = true }
                )
            }

            // Full-screen Swipeable Flashcards Mode Overlay
            if (showFlashcards) {
                FlashcardsScreen(
                    initialFilter = flashcardsFilter,
                    initialFlipped = flashcardsFlipped,
                    initialCompleted = flashcardsCompleted,
                    onDismiss = { showFlashcards = false },
                    onNavigateToPaywall = { showPaywall = true }
                )
            }

            // Full-screen Official EPA Universal Certificate Overlay
            if (showCertificate) {
                CertificateScreen(
                    onDismiss = { showCertificate = false }
                )
            }

            // High-Converting Paywall Screen Modal
            AnimatedVisibility(
                visible = showPaywall,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                PaywallScreen(
                    onDismiss = { showPaywall = false }
                )
            }

            // Day-0 Diagnostic Onboarding Test Modal
            AnimatedVisibility(
                visible = showDiagnosticOnboarding,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                DiagnosticOnboardingScreen(
                    onDismiss = { showDiagnosticOnboarding = false },
                    onNavigateToPaywall = {
                        showDiagnosticOnboarding = false
                        showPaywall = true
                    }
                )
            }
        }
    }
}

/**
 * Industrial Bottom Navigation Bar with WCAG AAA contrast and min 48x48dp touch targets.
 */
@Composable
private fun IndustrialBottomNavBar(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        containerColor = IndustrialColors.Navy,
        tonalElevation = 8.dp
    ) {
        NavTab.allTabs.forEach { tab ->
            val isSelected = selectedTab == tab.tag

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(tab.tag) },
                icon = {
                    Icon(
                        imageVector = tab.icon,
                        contentDescription = tab.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Medium
                    )
                },
                modifier = Modifier.minimumTouchTarget(),
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = IndustrialColors.CyanAccent,
                    selectedTextColor = IndustrialColors.CyanAccent,
                    indicatorColor = IndustrialColors.ElevatedSurface,
                    unselectedIconColor = IndustrialColors.TextSecondary,
                    unselectedTextColor = IndustrialColors.TextSecondary
                )
            )
        }
    }
}
