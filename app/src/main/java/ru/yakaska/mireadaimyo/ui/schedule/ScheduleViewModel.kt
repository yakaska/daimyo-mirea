package ru.yakaska.mireadaimyo.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.yakaska.mireadaimyo.data.model.Schedule
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.repository.ScheduleRepository
import ru.yakaska.mireadaimyo.util.CalendarUtil
import java.time.LocalDate

data class ScheduleUiState(
    val schedule: List<Lesson> = emptyList(),
    val currentDay: Int = CalendarUtil.getCurrentDayWeek(),
    val currentWeek: Int = CalendarUtil.getCurrentWeek(),
    val selectedDay: LocalDate = LocalDate.now()
)

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleUiState())
    val uiState = _uiState.asStateFlow()

    private val schedule = scheduleRepository.getSchedule("КВБО-03-21")

    private val groups = scheduleRepository.getAllGroups()

    init {
        showSchedule(LocalDate.now())
    }

    fun showSchedule(date: LocalDate) {
        val day = date.dayOfWeek.value - 1
        val week = CalendarUtil.getCurrentWeek(date)
        if (day == 6) {
            _uiState.update {
                it.copy(
                    schedule = emptyList(),
                    selectedDay = date
                )
            }
        } else {
            viewModelScope.launch {
                schedule.collect { schedule ->
                    _uiState.update {
                        it.copy(
                            schedule = getLessonsByWeek(week, schedule)[day],
                            selectedDay = date
                        )
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