package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.YearProgressIndicator

@Composable
fun YearProgress() {
    Card(R.string.year_progress, Icons.Outlined.HourglassTop) {
        val view = LocalView.current
        var state by remember { mutableStateOf(true) }

        Column(Modifier.clickable {
            view.hapticFeedback()
            state = !state
        }) {
            YearProgressIndicator()
            if (state) Text(percentage())
            else Text(remainingDays())
        }
    }
}

private fun percentage(): String = "${(yearProgressPercentage(yearProgress))}%"

private fun remainingDays(): String = appContext.resources.getQuantityString(
    R.plurals.days_remaining,
    (totalDaysInYear - daysPassed).toInt(),
    totalDaysInYear - daysPassed
)