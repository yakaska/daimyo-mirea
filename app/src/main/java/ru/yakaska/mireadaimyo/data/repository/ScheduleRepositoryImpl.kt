package ru.yakaska.mireadaimyo.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.yakaska.mireadaimyo.data.datasource.ScheduleRemoteDatasource
import ru.yakaska.mireadaimyo.data.model.Schedule
import ru.yakaska.mireadaimyo.repository.ScheduleRepository


class ScheduleRepositoryImpl(
    private val scheduleRemoteDatasource: ScheduleRemoteDatasource
) : ScheduleRepository {


    override fun getSchedule(group: String): Flow<Schedule> {
        return flow { emit(scheduleRemoteDatasource.getScheduleByGroup(group)) }.flowOn(Dispatchers.IO)
    }

    override fun getAllGroups(): Flow<List<String>> {
        return flow {
            emit(scheduleRemoteDatasource.getGroups().groups)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getActiveGroup(): String {
        TODO("Not yet implemented")
    }

    override suspend fun setActiveGroup() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSchedule() {
        TODO("Not yet implemented")
    }


}
