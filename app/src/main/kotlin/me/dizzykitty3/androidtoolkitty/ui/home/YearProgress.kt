package me.dizzykitty3.androidtoolkitty.ui.home

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
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.utils.DateUtil.daysPassed
import me.dizzykitty3.androidtoolkitty.utils.DateUtil.totalDaysInYear
import me.dizzykitty3.androidtoolkitty.utils.DateUtil.yearProgress
import me.dizzykitty3.androidtoolkitty.utils.DateUtil.yearProgressPercentage

@Preview
@Composable
fun YearProgress() {
    Card(
        icon = Icons.Outlined.HourglassTop,
        title = R.string.year_progress
    ) {
        val view = LocalView.current
        var isShowPercentage by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                isShowPercentage = !isShowPercentage
            }
        ) {
            CustomAnimatedProgressIndicator()

            val textToShow = if (isShowPercentage) percentage() else percentageAndRemaining()
            Text(
                text = textToShow,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

private fun percentage(): String = "${(yearProgressPercentage(yearProgress))}%"

private fun percentageAndRemaining(): String = "${percentage()}% · ${
    appContext.resources.getQuantityString(
        R.plurals.days_remaining,
        (totalDaysInYear - daysPassed).toInt(),
        totalDaysInYear - daysPassed
    )
}"