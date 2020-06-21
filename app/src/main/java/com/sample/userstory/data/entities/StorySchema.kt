package com.sample.userstory.data.entities


import com.google.gson.annotations.SerializedName

data class StorySchema(
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)