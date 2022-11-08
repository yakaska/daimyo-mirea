package ru.yakaska.mireadaimyo.repository

import kotlinx.coroutines.flow.Flow
import ru.yakaska.mireadaimyo.data.model.Schedule

interface ScheduleRepository {

    fun getSchedule(group: String): Flow<Schedule>

    fun getAllGroups(): Flow<List<String>>

    suspend fun getActiveGroup(): String

    suspend fun setActiveGroup()

    suspend fun deleteSchedule()

}