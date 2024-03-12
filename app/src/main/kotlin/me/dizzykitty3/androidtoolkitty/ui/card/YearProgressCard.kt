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
import me.dizzykitty3.androidtoolkitty.ui.component.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.DateUtils

@Composable
fun YearProgressCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.HourglassTop,
        title = LocalContext.current.getString(R.string.year_progress),
        isExpand = true
    ) {
        var isShowPercentage by remember { mutableStateOf(true) }
        Column(
            modifier = Modifier.clickable {
                isShowPercentage = !isShowPercentage
            }
        ) {
            CustomSpacerPadding()
            CustomAnimatedProgressIndicator()
            CustomSpacerPadding()
            val textToShow =
                if (isShowPercentage)
                    "${(DateUtils.displayYearProgressPercentage(DateUtils.calculateYearProgress()))}%"
                else
                    "${DateUtils.calculateTotalDaysInYear() - DateUtils.calculateDaysPassed()} ${
                        c.getString(
                            R.string.days_remaining
                        )
                    }"
            Text(
                text = textToShow,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
