package com.sample.userstory.data.repository

import com.sample.userstory.data.api.NewsService
import com.sample.userstory.data.db.StoryDao
import com.sample.userstory.data.entities.StoryEntity
import com.sample.userstory.ui.vo.Story
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class StoryRepositoryImpl @Inject constructor(
    private val dao: StoryDao,
    private val service: NewsService
) : StoryRepository {

    override fun getStory(storyId: Int): Single<Story> {
        return service.getStory(storyId).map {
            Story(it.title, it.url)
        }
    }

    override fun getStoryRange(startPos: Int, pageSize: Int): Single<List<Story>> {
        val storyIds = getStoryIdByRange(startPos, pageSize)
        return storyIds.toFlowable()
            .flatMapIterable {
                it
            }
            .parallel(10)
            .runOn(Schedulers.io())
            .map {
                getStory(it.storyId).blockingGet()
            }
            .sequential()
            .toList()
    }

    override fun fetchAndPersistStories(): Single<Int> {
        return service.getTopStories()
            .toObservable()
            .flatMapIterable { it }
            .map {
                StoryEntity(it)
            }
            .toList()
            .flatMap {
                dao.insert(it)
            }.map {
                it.size
            }
    }

    private fun getStoryIdByRange(startPos: Int, pageSize: Int): Single<List<StoryEntity>> {
        return dao.getStories(startPos, pageSize)
    }
}