package ru.yakaska.mireadaimyo.di

import org.koin.dsl.module
import ru.yakaska.mireadaimyo.data.datasource.ScheduleRemoteDatasource
import ru.yakaska.mireadaimyo.data.repository.ScheduleRepositoryImpl
import ru.yakaska.mireadaimyo.repository.ScheduleRepository

val dataModule = module {
    single { ScheduleRemoteDatasource.create() }
    single<ScheduleRepository> { ScheduleRepositoryImpl(get()) }
}