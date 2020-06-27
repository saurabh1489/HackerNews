package com.sample.userstory.data.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.userstory.data.entities.StoryEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class StoryDao {
    @Query("SELECT * FROM Story")
    abstract fun getAllStories(): DataSource.Factory<Int, StoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(storyEntity: List<StoryEntity>): Single<List<Long>>

    @Query("SELECT * FROM Story LIMIT :pageSize OFFSET :startPos")
    abstract fun getStories(startPos: Int, pageSize: Int): Single<List<StoryEntity>>
}