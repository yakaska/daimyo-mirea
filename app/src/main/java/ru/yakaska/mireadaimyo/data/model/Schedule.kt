package ru.yakaska.mireadaimyo.data.model


import com.google.gson.annotations.SerializedName

data class Schedule(
    @SerializedName("group")
    val group: String,
    @SerializedName("schedule")
    val schedule: Map<Int, WeekdaySchedule>
) {
    data class WeekdaySchedule(
        @SerializedName("lessons")
        val lessons: List<List<Lesson>>
    ) {
        data class Lesson(
            @SerializedName("name")
            val name: String,
            @SerializedName("rooms")
            val rooms: List<String>,
            @SerializedName("teachers")
            val teachers: List<String>,
            @SerializedName("time_end")
            val timeEnd: String,
            @SerializedName("time_start")
            val timeStart: String,
            @SerializedName("types")
            val types: String,
            @SerializedName("weeks")
            val weeks: List<Int>
        )
    }
}