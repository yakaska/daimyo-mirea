package ru.yakaska.mireadaimyo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleScreen
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleViewModel
import ru.yakaska.mireadaimyo.ui.theme.MireaScheduleTheme


class MainActivity : ComponentActivity() {

    private val scheduleViewModel: ScheduleViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MireaScheduleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ScheduleScreen(scheduleViewModel)
                }
            }
        }
    }
}

