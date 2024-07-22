package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.YearProgressIndicator
import me.dizzykitty3.androidtoolkitty.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.utils.DateUtil.toProgress

@Composable
fun YearProgress() {
    Card(R.string.year_progress, Icons.Outlined.HourglassTop) {
        YearProgressIndicator()
        Text(DateUtil.yearProgress.toProgress())
    }
}