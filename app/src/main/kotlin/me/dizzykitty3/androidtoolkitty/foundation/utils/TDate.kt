package me.dizzykitty3.androidtoolkitty.foundation.utils

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object TDate {
    @JvmStatic
    fun daysPassed(): Long =
        daysFromStartOfYear(LocalDate.now())

    @JvmStatic
    fun totalDaysInYear(): Long =
        daysFromStartOfYear(LocalDate.of(LocalDate.now().year, 12, 31)) + 1

    private fun daysFromStartOfYear(endDate: LocalDate): Long =
        LocalDate.of(endDate.year, 1, 1).until(endDate, ChronoUnit.DAYS)

    @JvmStatic
    fun yearProgress(): Float =
        daysPassed().toFloat() / totalDaysInYear().toFloat()

    @JvmStatic
    fun yearProgressPercentage(progress: Float): String =
        (progress * 100).toString().substring(0, 4)
}