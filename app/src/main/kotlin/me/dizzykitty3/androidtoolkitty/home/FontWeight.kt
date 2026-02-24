package me.dizzykitty3.androidtoolkitty.home

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.runtime.Composable
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.ui.home.FontWeightTestActivity
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard

@Composable
fun FontWeight() {
    val haptic = LocalHapticFeedback.current
    BaseCard(
        title = R.string.font_weight_test,
        icon = Icons.Outlined.FontDownload,
        hasShowMore = true,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            val intent = Intent(appContext, FontWeightTestActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            appContext.startActivity(intent)
        }) { }
}
