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
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleList
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleScreen
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
                    ScheduleScreen(uiState)
                }
            }
        }
    }
}

