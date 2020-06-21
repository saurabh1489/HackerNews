package com.sample.userstory.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Story")
data class StoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "Story Id")
    val storyId: Int
)