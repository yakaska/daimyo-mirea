package ru.yakaska.mireadaimyo.data.model


import com.google.gson.annotations.SerializedName

data class GroupsResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("groups")
    val groups: List<String>
)