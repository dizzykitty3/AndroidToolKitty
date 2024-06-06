package me.dizzykitty3.androidtoolkitty.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtil {
    fun daysPassed(): Long = daysFromStartOfYear(LocalDate.now())

    fun totalDaysInYear(): Long =
        daysFromStartOfYear(LocalDate.of(LocalDate.now().year, 12, 31)) + 1

    private fun daysFromStartOfYear(endDate: LocalDate): Long =
        LocalDate.of(endDate.year, 1, 1).until(endDate, ChronoUnit.DAYS)

    fun yearProgress(): Float = daysPassed().toFloat() / totalDaysInYear().toFloat()

    fun yearProgressPercentage(progress: Float): String =
        (progress * 100).toString().substring(0, 4)

    fun isNotWeekend(): Boolean =
        LocalDate.now().dayOfWeek != DayOfWeek.SATURDAY && LocalDate.now().dayOfWeek != DayOfWeek.SUNDAY
}