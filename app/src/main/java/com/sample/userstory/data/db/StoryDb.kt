package com.sample.userstory.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sample.userstory.data.entities.StoryEntity

@Database(entities = [StoryEntity::class], version = 1, exportSchema = false)
abstract class StoryDb : RoomDatabase() {
    abstract fun storyDao(): StoryDao
}