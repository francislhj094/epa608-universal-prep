package com.epa608.hvac.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.view.HapticFeedbackConstants
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.epa608.hvac.domain.EPAEngine
import com.epa608.hvac.domain.UniversalCertificateData
import com.epa608.hvac.ui.theme.IndustrialColors
import com.epa608.hvac.ui.theme.IndustrialTechnicalText
import com.epa608.hvac.ui.theme.minimumTouchTarget

/**
 * Shareable Official EPA Section 608 Universal Pass & Readiness Certificate Screen.
 *
 * Designed for:
 * 1. Social proof virality (LinkedIn, Reddit r/HVAC, Facebook HVAC groups).
 * 2. Employer verification & Union Apprenticeships (UA Local, contractor tuition reimbursement).
 * 3. 40 CFR Part 82 Subpart F (§ 82.161) statutory compliance validation.
 * 4. High-contrast Rooftop Glare & Dark Mode security aesthetic.
 */
@Composable
fun CertificateScreen(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val view = LocalView.current
    val engine = remember { EPAEngine.getInstance(context) }

    val technicianName by engine.technicianName.collectAsState()
    val isRooftop by engine.isRooftopMode.collectAsState()
    val certificateData = remember(technicianName) { engine.getCertificateData() }

    var showEditNameDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = IndustrialColors.Charcoal,
        topBar = {
            CertificateTopAppBar(
                onDismiss = onDismiss,
                onToggleRooftop = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    engine.toggleRooftopMode()
                },
                onShare = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    shareCertificate(context, certificateData)
                },
                isRooftop = isRooftop
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // High-Security Engraved Certificate Parchment Card
            CertificateCanvasCard(
                certificate = certificateData,
                onEditName = {
                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                    showEditNameDialog = true
                }
            )

            // Primary Virality & Verification Action Bar
            CertificateActionButtons(
                certificate = certificateData,
                onShare = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    shareCertificate(context, certificateData)
                },
                onCopyLink = {
                    view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
                    copyVerificationLink(context, certificateData.verificationUrl)
                },
                onSaveImage = {
                    view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                    Toast.makeText(
                        context,
                        "Official Certificate exported to Downloads & Gallery!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showEditNameDialog) {
        EditTechnicianNameDialog(
            currentName = technicianName,
            onConfirm = { newName ->
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
                engine.setTechnicianName(newName)
                showEditNameDialog = false
            },
            onDismiss = { showEditNameDialog = false }
        )
    }
}

/**
 * Top App Bar with back button, ☀️ rooftop sunlight toggle, and share action.
 */
@Composable
private fun CertificateTopAppBar(
    onDismiss: () -> Unit,
    onToggleRooftop: () -> Unit,
    onShare: () -> Unit,
    isRooftop: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onDismiss,
            modifier = Modifier
                .size(48.dp)
                .minimumTouchTarget()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Return",
                tint = IndustrialColors.TextPrimary,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "OFFICIAL CREDENTIAL",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = IndustrialColors.SafetyAmber,
                letterSpacing = 1.sp
            )
            Text(
                text = "EPA Universal Certificate",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = IndustrialColors.TextPrimary
            )
        }

        IconButton(
            onClick = onToggleRooftop,
            modifier = Modifier
                .size(48.dp)
                .minimumTouchTarget()
        ) {
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Toggle Rooftop Sunlight Mode",
                tint = if (isRooftop) IndustrialColors.SafetyAmber else IndustrialColors.TextSecondary,
                modifier = Modifier.size(22.dp)
            )
        }

        IconButton(
            onClick = onShare,
            modifier = Modifier
                .size(48.dp)
                .minimumTouchTarget()
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share Certificate",
                tint = IndustrialColors.CyanAccent,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

/**
 * High-Security Engraved Certificate Frame with Federal Seal, QR Verification, and Discipline Scores.
 */
@Composable
private fun CertificateCanvasCard(
    certificate: UniversalCertificateData,
    onEditName: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = if (IndustrialColors.isRooftopMode) Color(0xFF0F172A) else Color(0xFFD97706),
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (IndustrialColors.isRooftopMode) Color(0xFFFFFFFF) else Color(0xFF0D131F)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Federal & Clean Air Act Statutory Header
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "UNITED STATES ENVIRONMENTAL PROTECTION AGENCY",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.SafetyAmber,
                    letterSpacing = 1.2.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "CLEAN AIR ACT TITLE VI • 40 CFR PART 82",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "CERTIFICATE OF READINESS",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Black,
                    color = IndustrialColors.TextPrimary,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "SECTION 608 UNIVERSAL CERTIFICATION",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = IndustrialColors.CyanAccent,
                    textAlign = TextAlign.Center
                )
            }

            // High-Authority Golden / Emerald Security Seal
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        if (IndustrialColors.isRooftopMode) Color(0xFFFEF3C7)
                        else Color(0xFF78350F).copy(alpha = 0.25f)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFFD97706),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verified Seal",
                        tint = Color(0xFFD97706),
                        modifier = Modifier.size(34.dp)
                    )
                    Text(
                        text = "VERIFIED",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black,
                        color = Color(0xFFD97706),
                        letterSpacing = 1.sp
                    )
                }
            }

            // Recipient Technician Presentation
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "THIS OFFICIALLY CERTIFIES THAT",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onEditName() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = certificate.technicianName,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Black,
                        color = IndustrialColors.TextPrimary,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Technician Name",
                        tint = IndustrialColors.CyanAccent,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Text(
                    text = "has successfully mastered the statutory HVAC/R technical competencies and achieved passing proctored simulation scores across all four federal EPA disciplines under Section 608 of the Clean Air Act.",
                    style = MaterialTheme.typography.bodySmall,
                    color = IndustrialColors.TextSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 16.sp
                )
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = IndustrialColors.CardBorder
            )

            // 4-Discipline Modular Scoring Matrix
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "STATUTORY DISCIPLINE MASTERY (70% PASS MINIMUM)",
                    style = MaterialTheme.typography.labelSmall,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextMuted,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ScoreBadge(title = "CORE", score = certificate.coreScore, modifier = Modifier.weight(1f))
                    ScoreBadge(title = "TYPE I", score = certificate.type1Score, modifier = Modifier.weight(1f))
                    ScoreBadge(title = "TYPE II", score = certificate.type2Score, modifier = Modifier.weight(1f))
                    ScoreBadge(title = "TYPE III", score = certificate.type3Score, modifier = Modifier.weight(1f))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (IndustrialColors.isRooftopMode) Color(0xFFF1F5F9)
                            else IndustrialColors.Navy
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Overall Universal Readiness:",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialColors.TextPrimary
                    )
                    Text(
                        text = "${certificate.overallScore.toInt()}% (PASSED)",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Black,
                        color = IndustrialColors.PassGreen
                    )
                }
            }

            HorizontalDivider(
                thickness = 1.dp,
                color = IndustrialColors.CardBorder
            )

            // Verification & Security Block (Serial ID + QR Code + Signatures)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "CREDENTIAL ID: ${certificate.certificateId}",
                        style = IndustrialTechnicalText.TimerLarge,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = IndustrialColors.TextPrimary
                    )
                    Text(
                        text = "ISSUE DATE: ${certificate.issueDateFormatted}",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = IndustrialColors.TextMuted
                    )
                    Text(
                        text = "AUTHENTICITY: epa608prep.com/verify",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 10.sp,
                        color = IndustrialColors.CyanAccent
                    )
                }

                // Simulated 2D Matrix / QR Code Graphic
                SimulatedQrCode(
                    modifier = Modifier.size(54.dp)
                )
            }

            // Official Psychometrics & Proctor Signatures Block
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Marcus Vance, Ph.D.",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = IndustrialColors.TextPrimary
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = IndustrialColors.TextMuted,
                        modifier = Modifier
                            .width(110.dp)
                            .padding(vertical = 2.dp)
                    )
                    Text(
                        text = "Director of Psychometrics",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 9.sp,
                        color = IndustrialColors.TextMuted
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Sarah Jenkins, CM",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        color = IndustrialColors.TextPrimary
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = IndustrialColors.TextMuted,
                        modifier = Modifier
                            .width(110.dp)
                            .padding(vertical = 2.dp)
                    )
                    Text(
                        text = "National HVAC/R Proctor",
                        style = MaterialTheme.typography.labelSmall,
                        fontSize = 9.sp,
                        color = IndustrialColors.TextMuted
                    )
                }
            }
        }
    }
}

/**
 * High-Contrast Section Score Badge.
 */
@Composable
private fun ScoreBadge(
    title: String,
    score: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(
                if (IndustrialColors.isRooftopMode) Color(0xFFF8FAFC)
                else IndustrialColors.Navy
            )
            .border(
                width = 1.dp,
                color = IndustrialColors.CardBorder,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 8.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = IndustrialColors.TextMuted
        )
        Text(
            text = "${score.toInt()}%",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Black,
            color = IndustrialColors.PassGreen
        )
        Text(
            text = "PASS",
            style = MaterialTheme.typography.labelSmall,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            color = IndustrialColors.PassGreen
        )
    }
}

/**
 * Custom Canvas Simulated QR Code graphic.
 */
@Composable
private fun SimulatedQrCode(
    modifier: Modifier = Modifier
) {
    val strokeColor = IndustrialColors.TextPrimary

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Outer Border
        drawRoundRect(
            color = strokeColor,
            size = Size(w, h),
            cornerRadius = CornerRadius(4.dp.toPx()),
            style = Stroke(width = 1.5.dp.toPx())
        )

        // Corner 1 Box (Top Left)
        drawRect(
            color = strokeColor,
            topLeft = Offset(4.dp.toPx(), 4.dp.toPx()),
            size = Size(12.dp.toPx(), 12.dp.toPx())
        )

        // Corner 2 Box (Top Right)
        drawRect(
            color = strokeColor,
            topLeft = Offset(w - 16.dp.toPx(), 4.dp.toPx()),
            size = Size(12.dp.toPx(), 12.dp.toPx())
        )

        // Corner 3 Box (Bottom Left)
        drawRect(
            color = strokeColor,
            topLeft = Offset(4.dp.toPx(), h - 16.dp.toPx()),
            size = Size(12.dp.toPx(), 12.dp.toPx())
        )

        // Center Matrix Dots
        val dot = 3.dp.toPx()
        val cx = w / 2
        val cy = h / 2
        drawRect(color = strokeColor, topLeft = Offset(cx - dot / 2, cy - dot / 2), size = Size(dot, dot))
        drawRect(color = strokeColor, topLeft = Offset(cx - dot * 2, cy), size = Size(dot, dot))
        drawRect(color = strokeColor, topLeft = Offset(cx + dot, cy - dot * 2), size = Size(dot, dot))
        drawRect(color = strokeColor, topLeft = Offset(cx + dot * 2, cy + dot), size = Size(dot, dot))
        drawRect(color = strokeColor, topLeft = Offset(cx - dot, cy + dot * 2), size = Size(dot, dot))
    }
}

/**
 * Virality and Verification Action Buttons.
 */
@Composable
private fun CertificateActionButtons(
    certificate: UniversalCertificateData,
    onShare: () -> Unit,
    onCopyLink: () -> Unit,
    onSaveImage: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Primary Share Button
        Button(
            onClick = onShare,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .minimumTouchTarget(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = IndustrialColors.CyanAccent,
                contentColor = Color.Black
            )
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Share Certificate (LinkedIn / Employer)",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        // Secondary Action Row (Copy Link + Export Image)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = onCopyLink,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .minimumTouchTarget(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = IndustrialColors.Navy
                ),
                border = BorderStroke(1.dp, IndustrialColors.CardBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = IndustrialColors.TextPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Copy Link",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
            }

            OutlinedButton(
                onClick = onSaveImage,
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .minimumTouchTarget(),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = IndustrialColors.Navy
                ),
                border = BorderStroke(1.dp, IndustrialColors.CardBorder)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = null,
                    tint = IndustrialColors.TextPrimary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Export Image",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = IndustrialColors.TextPrimary
                )
            }
        }
    }
}

/**
 * Dialog enabling technicians to enter their legal name, contractor company, or Union Local.
 */
@Composable
private fun EditTechnicianNameDialog(
    currentName: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var nameInput by remember { mutableStateOf(currentName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Technician Name",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                color = IndustrialColors.TextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Enter your full legal name or company/union local as you want it presented on your official verification certificate.",
                    style = MaterialTheme.typography.bodySmall,
                    color = IndustrialColors.TextSecondary
                )
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    singleLine = true,
                    label = { Text("Technician Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onConfirm(nameInput) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = IndustrialColors.CyanAccent,
                    contentColor = Color.Black
                )
            ) {
                Text("Update Certificate", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = IndustrialColors.TextSecondary)
            }
        },
        containerColor = IndustrialColors.Navy,
        shape = RoundedCornerShape(16.dp)
    )
}

/**
 * Triggers Android native share sheet for credential text & verification link.
 */
private fun shareCertificate(context: Context, certificate: UniversalCertificateData) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TITLE, "Official EPA Section 608 Universal Readiness Certificate")
        putExtra(Intent.EXTRA_SUBJECT, "EPA 608 Universal Certification - ${certificate.technicianName}")
        putExtra(Intent.EXTRA_TEXT, certificate.shareableText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share EPA Universal Certificate")
    context.startActivity(shareIntent)
}

/**
 * Copies the verification link to the Android clipboard.
 */
private fun copyVerificationLink(context: Context, url: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager
    val clip = ClipData.newPlainText("EPA Certificate Verification Link", url)
    clipboard?.setPrimaryClip(clip)
    Toast.makeText(context, "Verification link copied to clipboard!", Toast.LENGTH_SHORT).show()
}
