package com.epa608.hvac

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import com.epa608.hvac.billing.PlayBillingManager
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.EPASection
import com.epa608.hvac.ui.navigation.AppNavigation
import com.epa608.hvac.ui.screens.FlashcardDeckFilter
import com.epa608.hvac.ui.screens.QuickDrillMode
import com.epa608.hvac.ui.theme.EPA608Theme
import com.epa608.hvac.ui.theme.IndustrialColors

/**
 * Root Activity for EPA 608 HVAC Practice Test Android.
 *
 * Implements:
 * 1. Edge-to-edge window configuration with high-contrast system bars.
 * 2. Eager initialization of EPAEngine and Google Play Billing 7.0+ Manager.
 * 3. Root Industrial Dark Theme (`EPA608Theme`) and `AppNavigation`.
 * 4. Deep linking & Intent navigation for notifications, ASO widgets, and QA automation.
 */
class MainActivity : ComponentActivity() {

    private val navState = mutableStateOf(NavigationIntentState())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configure edge-to-edge status & navigation bars
        enableEdgeToEdge()

        // Keep screen on and wake device over lockscreen during field testing
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        }

        // Initialize singletons with Application context
        EPAEngine.initialize(this)
        PlayBillingManager.getInstance(this)

        handleIntent(intent)

        setContent {
            val state by navState
            val engine = androidx.compose.runtime.remember { EPAEngine.getInstance(this) }
            val isRooftop by engine.isRooftopMode.collectAsState()
            IndustrialColors.isRooftopMode = isRooftop

            EPA608Theme(isRooftopMode = isRooftop) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = IndustrialColors.Charcoal
                ) {
                    AppNavigation(
                        initialTab = state.tab,
                        initialSection = state.section,
                        initialDrillMode = state.drillMode,
                        initialShowFlashcards = state.showFlashcards,
                        initialFlashcardsFlipped = state.showFlashcardsFlipped,
                        initialFlashcardsFilter = state.flashcardsFilter,
                        initialFlashcardsCompleted = state.showFlashcardsCompleted,
                        initialShowCertificate = state.showCertificate,
                        autoStartExam = state.startExam,
                        autoSubmitExam = state.submitExam,
                        autoCompleteQuiz = state.completeQuiz,
                        fieldToolIndex = state.toolIndex,
                        leakCharge = state.leakCharge,
                        leakAdded = state.leakAdded,
                        leakDays = state.leakDays
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        if (intent == null) return

        if (intent.hasExtra("grant_pro")) {
            val grant = intent.getBooleanExtra("grant_pro", false)
            PlayBillingManager.getInstance(this).setProUserForTesting(grant)
        }

        if (intent.getBooleanExtra("reset_progress", false)) {
            EPAEngine.getInstance(this).resetProgress()
        }

        if (intent.getBooleanExtra("mark_diagnostic_completed", false)) {
            EPAEngine.getInstance(this).markDiagnosticCompleted()
        }

        if (intent.hasExtra("rooftop") || intent.hasExtra("theme")) {
            val isRooftop = intent.getBooleanExtra("rooftop", false) ||
                    intent.getStringExtra("rooftop")?.toBoolean() == true ||
                    intent.getStringExtra("theme")?.equals("rooftop", ignoreCase = true) == true
            EPAEngine.getInstance(this).setRooftopMode(isRooftop)
        }

        val tab = when (intent.getStringExtra("tab")?.lowercase()) {
            "dashboard", "study" -> 0
            "exam", "examsim" -> 1
            "tools", "field_tools" -> 2
            "settings", "profile" -> 3
            else -> intent.getIntExtra("tab", navState.value.tab)
        }
        val startExam = intent.getBooleanExtra("start_exam", false)
        val submitExam = intent.getBooleanExtra("submit_exam", false)
        val completeQuiz = intent.getBooleanExtra("complete_quiz", false)
        val toolIndex = intent.getIntExtra("tool_index", 0)
        val leakCharge = intent.getStringExtra("leak_charge") ?: "120"
        val leakAdded = intent.getStringExtra("leak_added") ?: "15"
        val leakDays = intent.getStringExtra("leak_days") ?: "90"
        val sectionStr = intent.getStringExtra("section")
        val drillStr = intent.getStringExtra("drill")

        val section = sectionStr?.let {
            try {
                EPASection.valueOf(it.uppercase())
            } catch (e: Exception) {
                null
            }
        }

        val drill = drillStr?.let {
            when (it.lowercase()) {
                "rapid" -> QuickDrillMode.RAPID_FIRE
                "weak" -> QuickDrillMode.WEAK_AREAS
                "saved" -> QuickDrillMode.SAVED
                else -> null
            }
        }

        val showFlashcards = intent.getBooleanExtra("flashcards", false) ||
                intent.getBooleanExtra("show_flashcards", false) ||
                intent.getStringExtra("flashcards")?.toBoolean() == true ||
                intent.getStringExtra("show_flashcards")?.toBoolean() == true ||
                drillStr?.equals("flashcards", ignoreCase = true) == true
        val showFlashcardsFlipped = intent.getBooleanExtra("flip", false) ||
                intent.getStringExtra("flip")?.toBoolean() == true
        val showFlashcardsCompleted = intent.getBooleanExtra("completed", false) ||
                intent.getStringExtra("completed")?.toBoolean() == true
        val flashcardsFilterStr = intent.getStringExtra("filter")
        val flashcardsFilter = flashcardsFilterStr?.let {
            try {
                FlashcardDeckFilter.valueOf(it.uppercase())
            } catch (e: Exception) {
                FlashcardDeckFilter.ALL
            }
        } ?: FlashcardDeckFilter.ALL

        val nameExtra = intent.getStringExtra("name") ?: intent.getStringExtra("technician_name")
        if (!nameExtra.isNullOrBlank()) {
            EPAEngine.getInstance(this).setTechnicianName(nameExtra)
        }

        val showCertificate = intent.getBooleanExtra("certificate", false) ||
                intent.getBooleanExtra("cert", false) ||
                intent.getStringExtra("certificate")?.toBoolean() == true ||
                intent.getStringExtra("view")?.equals("certificate", ignoreCase = true) == true ||
                intent.getStringExtra("drill")?.equals("certificate", ignoreCase = true) == true

        navState.value = NavigationIntentState(
            tab = tab,
            showFlashcards = showFlashcards,
            showFlashcardsFlipped = showFlashcardsFlipped,
            showFlashcardsCompleted = showFlashcardsCompleted,
            flashcardsFilter = flashcardsFilter,
            showCertificate = showCertificate,
            startExam = startExam,
            submitExam = submitExam,
            completeQuiz = completeQuiz,
            toolIndex = toolIndex,
            leakCharge = leakCharge,
            leakAdded = leakAdded,
            leakDays = leakDays,
            section = section,
            drillMode = drill
        )
    }
}

data class NavigationIntentState(
    val tab: Int = 0,
    val showFlashcards: Boolean = false,
    val showFlashcardsFlipped: Boolean = false,
    val showFlashcardsCompleted: Boolean = false,
    val flashcardsFilter: FlashcardDeckFilter = FlashcardDeckFilter.ALL,
    val showCertificate: Boolean = false,
    val startExam: Boolean = false,
    val submitExam: Boolean = false,
    val completeQuiz: Boolean = false,
    val toolIndex: Int = 0,
    val leakCharge: String = "120",
    val leakAdded: String = "15",
    val leakDays: String = "90",
    val section: EPASection? = null,
    val drillMode: QuickDrillMode? = null
)
