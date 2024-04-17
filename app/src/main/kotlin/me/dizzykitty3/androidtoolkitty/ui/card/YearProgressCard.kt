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
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.utils.TDate.daysPassed
import me.dizzykitty3.androidtoolkitty.foundation.utils.TDate.totalDaysInYear
import me.dizzykitty3.androidtoolkitty.foundation.utils.TDate.yearProgress
import me.dizzykitty3.androidtoolkitty.foundation.utils.TDate.yearProgressPercentage

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
            val context = LocalContext.current

            CustomAnimatedProgressIndicator()

            val textToShow =
                if (isShowPercentage)
                    "${(yearProgressPercentage(yearProgress()))}%"
                else
                    "${(yearProgressPercentage(yearProgress()))}% · ${
                        context.resources.getQuantityString(
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