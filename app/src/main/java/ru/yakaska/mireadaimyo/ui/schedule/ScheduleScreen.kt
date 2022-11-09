package ru.yakaska.mireadaimyo.ui.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.util.CalendarUtil
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Year
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*

@Composable
fun ScheduleScreen(uiState: ScheduleUiState, modifier: Modifier = Modifier) {

    Column {
        ScheduleCalendar(uiState.currentDay)
        ScheduleList(uiState.schedule)
    }

}

@Composable
fun ScheduleCalendar(weekDay: Int, modifier: Modifier = Modifier) {
    println("Weekday: $weekDay")
    val currentDate = remember { LocalDate.now() }
    val startDate = remember { CalendarUtil.getSemesterStart() } // Adjust as needed
    val endDate = remember { CalendarUtil.getSemesterEnd() } // Adjust as needed
    val daysOfWeek = remember { daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY) } // Available from the library

    val state = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = currentDate,
        firstDayOfWeek = daysOfWeek.first()
    )

    Column {
        DaysOfWeekTitle(daysOfWeek = daysOfWeek)
        WeekCalendar(state = state, dayContent = { Day(it) })
    }
        
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
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
fun Day(day: WeekDay) {
    Box(
        modifier = Modifier.aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(text = day.date.dayOfMonth.toString())
    }
}

@Composable
fun ScheduleList(daySchedule: List<Lesson>, modifier: Modifier = Modifier) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(daySchedule) { lesson ->
            ScheduleCard(lesson)
        }
    }
}

@Composable
fun ScheduleCard(
    lesson: Lesson,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = lesson.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            ScheduleCardLine(
                icon = Icons.Default.AccessTime, title = "${lesson.timeStart} - ${lesson.timeEnd}"
            )
            ScheduleCardLine(
                icon = Icons.Default.Place, title = lesson.rooms.joinToString()
            )
            ScheduleCardLine(
                icon = Icons.Default.Person, title = lesson.teachers.joinToString()
            )
            ScheduleCardLine(
                icon = Icons.Default.Info, title = lesson.types
            )
        }
    }
}

@Composable
fun ScheduleCardLine(icon: ImageVector, title: String) {
    Row {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(text = title, Modifier.padding(start = 8.dp))
    }
}
