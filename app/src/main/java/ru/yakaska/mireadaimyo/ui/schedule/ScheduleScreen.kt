package ru.yakaska.mireadaimyo.ui.schedule

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.yakaska.mireadaimyo.R
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.ui.schedule.component.ScheduleCalendar

@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(scheduleViewModel: ScheduleViewModel, modifier: Modifier = Modifier) {

    val uiState by scheduleViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge
                    )
                }, colors = TopAppBarDefaults.smallTopAppBarColors()
            )

        }
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ScheduleCalendar(uiState.selectedDay) {
                scheduleViewModel.showSchedule(it.date)
            }
            ScheduleList(daySchedule = uiState.schedule)
        }
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
            if (lesson.rooms.isNotEmpty()) {
                ScheduleCardLine(
                    icon = Icons.Default.Place, title = lesson.rooms.joinToString()
                )
            }
            if (lesson.teachers.isNotEmpty()) {
                ScheduleCardLine(
                    icon = Icons.Default.Person, title = lesson.teachers.joinToString()
                )
            }
            val types = when (lesson.types) {
                "пр" -> "Практика"
                "лк" -> "Лекция"
                "лаб" -> "Лабораторная"
                else -> ""
            }
            if (types.isNotEmpty()) {
                ScheduleCardLine(
                    icon = Icons.Default.Info, title = types
                )
            }
        }
    }
}

@Composable
fun ScheduleCardLine(icon: ImageVector, title: String) {
    Row {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.surfaceTint)
        Text(text = title, Modifier.padding(start = 8.dp))
    }
}
