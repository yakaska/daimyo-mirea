package ru.yakaska.mireadaimyo.ui.schedule.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.daysOfWeek
import ru.yakaska.mireadaimyo.util.CalendarUtil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Composable
fun ScheduleCalendar(
    selectedDate: LocalDate, modifier: Modifier = Modifier, onDayClick: (WeekDay) -> Unit
) {
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { CalendarUtil.getSemesterStart() }
    val endDate = remember { CalendarUtil.getSemesterEnd() }
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) }
    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first()
    )

    WeekCalendar(
        state = state,
        modifier = modifier,
        dayContent = { day ->
            ScheduleDay(
                day, isSelected = selectedDate == day.date, isFocused = currentDate == day.date
            ) {
                onDayClick(it)
            }
        },
        weekHeader = {
            CalendarHeader(daysOfWeek = daysOfWeek)
        },
    )
}

@Composable
private fun CalendarHeader(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            )
        }
    }
}

@Composable
private fun ScheduleDay(
    day: WeekDay, isSelected: Boolean, isFocused: Boolean, onDayClick: (WeekDay) -> Unit
) {
    val color = when {
        isSelected && isFocused -> MaterialTheme.colorScheme.primaryContainer
        isSelected -> MaterialTheme.colorScheme.inversePrimary
        isFocused -> MaterialTheme.colorScheme.primaryContainer
        else -> Color.Transparent
    }
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = color)
            .clickable { onDayClick(day) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = MaterialTheme.typography.labelLarge
        )
    }
}