package ru.yakaska.mireadaimyo.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.yakaska.mireadaimyo.ui.schedule.ScheduleViewModel

val uiModule = module {
    viewModel { ScheduleViewModel(get()) }
}