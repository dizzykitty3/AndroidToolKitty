package me.dizzykitty3.androidtoolkitty.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import dagger.hilt.android.AndroidEntryPoint
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen

@AndroidEntryPoint
class HapticTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ) { innerPadding ->
                    Box(
                        Modifier.padding(
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        )
                    ) {
                        Screen(screenTitle = R.string.haptic_test) {
                            HapticTestComposable()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HapticTestComposable() {
    BaseCard(R.string.haptic_test) {
        val haptic = LocalHapticFeedback.current
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.Confirm) }) { Text("Confirm") }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ContextClick) }) {
            Text(
                "ContextClick"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.GestureEnd) }) {
            Text(
                "GestureEnd"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate) }) {
            Text(
                "GestureThresholdActivate"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }) {
            Text(
                "LongPress"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.Reject) }) { Text("Reject") }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick) }) {
            Text(
                "SegmentFrequentTick"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.SegmentTick) }) {
            Text(
                "SegmentTick"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }) {
            Text(
                "TextHandleMove"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ToggleOff) }) {
            Text(
                "ToggleOff"
            )
        }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ToggleOn) }) { Text("ToggleOn") }
        TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.VirtualKey) }) {
            Text(
                "VirtualKey"
            )
        }
    }
}
