package me.dizzykitty3.androidtoolkitty.data.utils

import androidx.annotation.CheckResult
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtil {
    val daysPassed: Long
        get() = daysFromStartOfYear(LocalDate.now())

    val totalDaysInYear: Long
        get() = daysFromStartOfYear(LocalDate.of(LocalDate.now().year, 12, 31)) + 1

    private fun daysFromStartOfYear(endDate: LocalDate): Long =
        LocalDate.of(endDate.year, 1, 1).until(endDate, ChronoUnit.DAYS)

    val yearProgress: Float
        get() = daysPassed.toFloat() / totalDaysInYear.toFloat()

    fun yearProgressPercentage(progress: Float): String =
        (progress * 100).toString().substring(0, 4)

    val isNotWeekend: Boolean
        @CheckResult get() = LocalDate.now().dayOfWeek != DayOfWeek.SATURDAY && LocalDate.now().dayOfWeek != DayOfWeek.SUNDAY
}