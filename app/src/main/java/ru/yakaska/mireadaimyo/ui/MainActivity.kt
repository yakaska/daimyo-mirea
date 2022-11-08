package ru.yakaska.mireadaimyo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.yakaska.mireadaimyo.data.model.Schedule.WeekdaySchedule.Lesson
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleViewModel
import ru.yakaska.mireadaimyo.ui.theme.MireaScheduleTheme


class MainActivity : ComponentActivity() {

    private val scheduleViewModel: ScheduleViewModel by viewModel()

    @OptIn(ExperimentalLifecycleComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MireaScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val uiState by scheduleViewModel.uiState.collectAsStateWithLifecycle()
                    ScheduleList(daySchedule = uiState.schedule)
                }
            }
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
                    icon = Icons.Default.AccessTime,
                    title = "${lesson.timeStart} - ${lesson.timeEnd}"
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

}

