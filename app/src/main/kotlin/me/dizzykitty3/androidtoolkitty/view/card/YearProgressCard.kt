package me.dizzykitty3.androidtoolkitty.view.card

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
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.util.DateUtils.calculateDaysPassed
import me.dizzykitty3.androidtoolkitty.common.util.DateUtils.calculateTotalDaysInYear
import me.dizzykitty3.androidtoolkitty.common.util.DateUtils.calculateYearProgress
import me.dizzykitty3.androidtoolkitty.common.util.DateUtils.displayYearProgressPercentage

@Composable
fun YearProgressCard() {
    CustomCard(
        icon = Icons.Outlined.HourglassTop,
        title = R.string.year_progress
    ) {
        val context = LocalContext.current

        var isShowPercentage by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier.clickable { isShowPercentage = !isShowPercentage }
        ) {
            CustomSpacerPadding()

            CustomAnimatedProgressIndicator()

            CustomSpacerPadding()

            val textToShow =
                if (isShowPercentage)
                    "${(displayYearProgressPercentage(calculateYearProgress()))}%"
                else
                    context.resources.getQuantityString(
                        R.plurals.days_remaining,
                        (calculateTotalDaysInYear() - calculateDaysPassed()).toInt(),
                        calculateTotalDaysInYear() - calculateDaysPassed()
                    )
            Text(
                text = textToShow,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}