package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Vibration
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_HAPTIC_FEEDBACK
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen

@Composable
fun HapticFeedback(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        title = R.string.haptic,
        icon = Icons.Outlined.Vibration,
        hasShowMore = true,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_HAPTIC_FEEDBACK)
        }) { }
}

@Composable
fun HapticFeedbackScreen(navController: NavHostController) {
    Screen(navController = navController) {
        Card(R.string.haptic) {
            val haptic = LocalHapticFeedback.current
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.Confirm) }) { Text("Confirm") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ContextClick) }) { Text("ContextClick") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.GestureEnd) }) { Text("GestureEnd") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.GestureThresholdActivate) }) { Text("GestureThresholdActivate") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress) }) { Text("LongPress") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.Reject) }) { Text("Reject") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.SegmentFrequentTick) }) { Text("SegmentFrequentTick") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.SegmentTick) }) { Text("SegmentTick") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove) }) { Text("TextHandleMove") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ToggleOff) }) { Text("ToggleOff") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.ToggleOn) }) { Text("ToggleOn") }
            TextButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.VirtualKey) }) { Text("VirtualKey") }
        }
    }
}