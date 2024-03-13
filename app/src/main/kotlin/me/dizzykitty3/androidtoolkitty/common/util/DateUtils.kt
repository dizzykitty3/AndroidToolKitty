package me.dizzykitty3.androidtoolkitty.common.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtils {
    @JvmStatic
    fun calculateDaysPassed(): Long = calculateDaysFromStartOfYear(LocalDate.now())
    @JvmStatic
    fun calculateTotalDaysInYear(): Long =
        calculateDaysFromStartOfYear(LocalDate.of(LocalDate.now().year, 12, 31)) + 1

    private fun calculateDaysFromStartOfYear(endDate: LocalDate): Long =
        LocalDate.of(endDate.year, 1, 1).until(endDate, ChronoUnit.DAYS)
    @JvmStatic
    fun calculateYearProgress(): Float =
        calculateDaysPassed().toFloat() / calculateTotalDaysInYear().toFloat()
    @JvmStatic
    fun displayYearProgressPercentage(progress: Float): String =
        (progress * 100).toString().substring(0, 4)
}