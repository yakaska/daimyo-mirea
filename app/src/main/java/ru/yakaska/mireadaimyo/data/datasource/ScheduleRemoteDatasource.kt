package ru.yakaska.mireadaimyo.data.datasource

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import ru.yakaska.mireadaimyo.data.model.GroupsResponse
import ru.yakaska.mireadaimyo.data.model.Schedule

interface ScheduleRemoteDatasource {

    @GET("schedule/groups")
    suspend fun getGroups(): GroupsResponse

    @GET("schedule/{groupName}/full_schedule")
    suspend fun getScheduleByGroup(@Path("groupName") groupName: String): Schedule

    companion object {
        private const val BASE_URL = "http://schedule.mirea.ninja:5000/api/"

        fun create(): ScheduleRemoteDatasource {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder().addInterceptor(logger).build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ScheduleRemoteDatasource::class.java)
        }
    }

}