package ru.yakaska.mireadaimyo.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month
import java.time.temporal.TemporalAdjusters

// TODO: create tests

object CalendarUtil {
    private const val maxWeek = 17

    // Понедельник - 1, Воскресенье - 7
    fun getCurrentDayWeek() = LocalDate.now().dayOfWeek.value

    fun getCurrentWeek(currentDate: LocalDate? = null): Int {
        val date = currentDate ?: LocalDate.now()
        val startDate = getSemesterStart(currentDate)

        if (date.isBefore(startDate)) {
            return 1
        }

        var week: Int = weekNumber(date) - weekNumber(startDate)
        week++
        return week
    }

    fun getDaysInWeek(week: Int, currentDate: LocalDate?): List<LocalDate> {
        val weekDays = mutableListOf<LocalDate>()

        val semesterStart = getSemesterStart(currentDate)

        val firstDayOfWeek = semesterStart.minusDays((semesterStart.dayOfWeek.value - 1).toLong())

        val firstDayOfChosenWeek = firstDayOfWeek.plusDays(((week - 1) * 7).toLong())

        for (i in 0 until 7) {
            weekDays.add(firstDayOfChosenWeek)
            firstDayOfChosenWeek.plusDays(1)
        }
        return weekDays
    }


    fun getSemesterStart(currentDate: LocalDate?) = getCurrentSemesterStart(currentDate)

    fun getSemesterEnd(currentDate: LocalDate?) = getDaysInWeek(
        maxWeek, getCurrentSemesterStart(currentDate)
    ).last()

    // TODO: maybe sunday can be 0 in calculation
    private fun numOfWeeks(year: Int): Int {
        val december28 = LocalDate.of(year, Month.DECEMBER, 28)
        val dayOfDec28 = december28.dayOfYear
        return (dayOfDec28 - december28.dayOfWeek.value + 10) / 7
    }

    // TODO: maybe sunday can be 0 in calculation
    private fun weekNumber(date: LocalDate): Int {
        val dayOfYear = date.dayOfYear
        var weekOfYear = (dayOfYear - date.dayOfWeek.value + 10) / 7
        if (weekOfYear < 1) {
            weekOfYear = numOfWeeks(date.year - 1)
        } else if (weekOfYear > numOfWeeks(date.year)) {
            weekOfYear = 1
        }
        return weekOfYear
    }

    private fun getFirstMondayOfMonth(year: Int, month: Int): LocalDate = LocalDate.of(
        year, Month.of(month), 1
    ).with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))

    private fun getCorrectedDate(date: LocalDate): LocalDate {
        return if (date.dayOfWeek == DayOfWeek.SATURDAY || date.dayOfWeek == DayOfWeek.SUNDAY) {
            getFirstMondayOfMonth(date.year, date.month.value)
        } else {
            date
        }
    }

    private fun getExpectedSemesterStart(currentDate: LocalDate): LocalDate {
        return if (currentDate.month >= Month.SEPTEMBER || currentDate.month == Month.AUGUST && currentDate.dayOfMonth >= 25) {
            LocalDate.of(currentDate.year, Month.SEPTEMBER, 1)
        } else {
            LocalDate.of(currentDate.year, Month.FEBRUARY, 9)
        }
    }

    private fun getCurrentSemesterStart(currentDate: LocalDate?): LocalDate {
        val date = currentDate ?: LocalDate.now()
        val expectedStart = getExpectedSemesterStart(date)
        return getCorrectedDate(expectedStart)
    }
}

