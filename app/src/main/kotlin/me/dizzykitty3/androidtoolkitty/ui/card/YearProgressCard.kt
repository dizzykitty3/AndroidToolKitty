package me.dizzykitty3.androidtoolkitty.ui.card

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
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.util.DateUtil.daysPassed
import me.dizzykitty3.androidtoolkitty.foundation.util.DateUtil.totalDaysInYear
import me.dizzykitty3.androidtoolkitty.foundation.util.DateUtil.yearProgress
import me.dizzykitty3.androidtoolkitty.foundation.util.DateUtil.yearProgressPercentage

@Composable
fun YearProgressCard() {
    CustomCard(
        icon = Icons.Outlined.HourglassTop,
        title = R.string.year_progress
    ) {
        var isShowPercentage by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.clickable { isShowPercentage = !isShowPercentage }
        ) {
            CustomAnimatedProgressIndicator()

            val textToShow =
                if (isShowPercentage)
                    "${(yearProgressPercentage(yearProgress()))}%"
                else
                    "${(yearProgressPercentage(yearProgress()))}% · ${
                        appContext.resources.getQuantityString(
                            R.plurals.days_remaining,
                            (totalDaysInYear() - daysPassed()).toInt(),
                            totalDaysInYear() - daysPassed()
                        )
                    }"
            Text(
                text = textToShow,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}