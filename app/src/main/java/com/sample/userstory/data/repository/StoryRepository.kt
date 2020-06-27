package com.sample.userstory.data.repository

import androidx.paging.PagedList
import com.sample.userstory.data.entities.StoryEntity
import com.sample.userstory.data.entities.StorySchema
import com.sample.userstory.ui.vo.Story
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface StoryRepository {
    fun getStory(storyId: Int): Single<Story>
    fun getStoryRange(startPos: Int, pageSize: Int): Single<List<Story>>
    fun fetchAndPersistStories(): Single<Int>
}