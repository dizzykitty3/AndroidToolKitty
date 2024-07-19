package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassTop
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil.toProgress
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.YearProgressIndicator

@Composable
fun YearProgress() {
    Card(R.string.year_progress, Icons.Outlined.HourglassTop) {
        YearProgressIndicator()
        Text(DateUtil.yearProgress.toProgress())
    }
}