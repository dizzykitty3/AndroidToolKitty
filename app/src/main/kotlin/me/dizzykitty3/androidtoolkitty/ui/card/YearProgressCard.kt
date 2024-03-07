package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
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
import me.dizzykitty3.androidtoolkitty.util.Utils.calculateDaysPassed
import me.dizzykitty3.androidtoolkitty.util.Utils.calculateTotalDaysInYear
import me.dizzykitty3.androidtoolkitty.util.Utils.calculateYearProgress
import me.dizzykitty3.androidtoolkitty.util.Utils.displayYearProgressPercentage

@Composable
fun YearProgressCard() {
    CustomCard(title = LocalContext.current.getString(R.string.year_progress)) {
        var isShowPercentage by remember { mutableStateOf(true) }
        CustomSpacerPadding()
        CustomAnimatedProgressIndicator()
        CustomSpacerPadding()
        val textToShow =
            if (isShowPercentage)
                "${(displayYearProgressPercentage(calculateYearProgress()))}%"
            else
                "${calculateDaysPassed()} / ${calculateTotalDaysInYear()} / ${calculateTotalDaysInYear() - calculateDaysPassed()} days remaining"
        Text(
            text = textToShow,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isShowPercentage = !isShowPercentage
                }
        )
    }
}
