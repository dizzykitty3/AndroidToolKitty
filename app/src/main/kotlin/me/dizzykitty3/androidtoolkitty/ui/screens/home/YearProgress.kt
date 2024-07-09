package me.dizzykitty3.androidtoolkitty.ui.screens.home

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil.daysPassed
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil.totalDaysInYear
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil.yearProgress
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil.yearProgressPercentage
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.CustomAnimatedProgressIndicator

@Composable
fun YearProgress() {
    Card(
        icon = Icons.Outlined.HourglassTop,
        title = R.string.year_progress
    ) {
        val view = LocalView.current
        var percentageOnly by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                percentageOnly = !percentageOnly
            }
        ) {
            CustomAnimatedProgressIndicator()

            Text(
                text = if (percentageOnly) percentage() else percentageAndRemaining(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun percentage(): String = "${(yearProgressPercentage(yearProgress))}%"

private fun remainingDays(): String = appContext.resources.getQuantityString(
    R.plurals.days_remaining,
    (totalDaysInYear - daysPassed).toInt(),
    totalDaysInYear - daysPassed
)

private fun percentageAndRemaining(): String = "${percentage()} · ${remainingDays()}"