package me.dizzykitty3.androidtoolkitty.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtils {
    @JvmStatic
    fun calculateDaysPassed(): Long {
        return calculateDaysFromStartOfYear(LocalDate.now())
    }

    @JvmStatic
    fun calculateTotalDaysInYear(): Long {
        val currentDate = LocalDate.now()
        return calculateDaysFromStartOfYear(LocalDate.of(currentDate.year, 12, 31)) + 1
    }

    private fun calculateDaysFromStartOfYear(endDate: LocalDate): Long {
        val startOfYear = LocalDate.of(endDate.year, 1, 1)
        return startOfYear.until(endDate, ChronoUnit.DAYS)
    }

    @JvmStatic
    fun calculateYearProgress(): Float {
        return calculateDaysPassed().toFloat() / calculateTotalDaysInYear().toFloat()
    }

    @JvmStatic
    fun displayYearProgressPercentage(progress: Float): String {
        return (progress * 100).toString().substring(0, 4)
    }
}