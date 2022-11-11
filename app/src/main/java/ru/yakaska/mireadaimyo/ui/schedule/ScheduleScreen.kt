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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.ui.schedule.component.ScheduleCalendar
import java.util.*

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ScheduleScreen(scheduleViewModel: ScheduleViewModel, modifier: Modifier = Modifier) {

    val uiState by scheduleViewModel.uiState.collectAsStateWithLifecycle()

    Column {
        ScheduleCalendar(uiState.selectedDay) { scheduleViewModel.showSchedule(it.date) }
        ScheduleList(daySchedule = uiState.schedule)
    }

}

@Composable
fun ScheduleList(daySchedule: List<Lesson>, modifier: Modifier = Modifier) {
    if (daySchedule.isEmpty()) {
        // TODO: сообщение, что расписания на сегодня нет
    } else {
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
