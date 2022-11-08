package ru.yakaska.mireadaimyo.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yakaska.mireadaimyo.util.CalendarUtil
import ru.yakaska.mireadaimyo.data.model.Schedule
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.repository.ScheduleRepository
import java.time.LocalDate
import java.time.Month

data class ScheduleUiState(
    val schedule: List<Lesson> = emptyList(),
    val currentDay: Int = CalendarUtil.getCurrentDayWeek(),
    val currentWeek: Int = CalendarUtil.getCurrentWeek()
)

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState = _uiState.asStateFlow()

    private val schedule = scheduleRepository.getSchedule("ИВБО-07-21")

    private val groups = scheduleRepository.getAllGroups()

    init {
        showSchedule(LocalDate.of(2022, Month.NOVEMBER, 19))
    }

    fun showSchedule(date: LocalDate) {
        // TODO: get day and week from selected date
        val day = date.dayOfWeek.value - 1
        val week = CalendarUtil.getCurrentWeek(date)
        if (day == 6) {
            _uiState.update { it.copy(schedule = emptyList()) }
        } else {
            viewModelScope.launch {
                schedule.collect { schedule ->
                    _uiState.update {
                        it.copy(schedule = getLessonsByWeek(week, schedule)[day])
                    }
                }
            }
        }
    }

    private fun getLessonsByWeek(week: Int, schedule: Schedule): List<List<Lesson>> {
        val lessonsInWeek = mutableListOf<MutableList<Lesson>>()
        for (i in 1..6) {
            lessonsInWeek.add(mutableListOf())
            for (lessons in schedule.schedule[i]!!.lessons) {
                for (lesson in lessons) {
                    if (lesson.weeks.contains(week)) lessonsInWeek[i - 1].add(lesson)
                }
            }
        }
        return lessonsInWeek
    }

}