package com.sample.userstory.data.repository

import android.text.method.SingleLineTransformationMethod
import android.util.Log
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.paging.toLiveData
import com.sample.userstory.data.api.NewsService
import com.sample.userstory.data.datasource.StoryDataSourceFactory
import com.sample.userstory.data.db.StoryDao
import com.sample.userstory.data.entities.StoryEntity
import com.sample.userstory.data.entities.StorySchema
import com.sample.userstory.ui.vo.Story
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
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
        Log.d("StoryRepositoryImpl", "getStoryRange $startPos $pageSize")
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

    override fun fetchAndPeristStories(): Single<Int> {
        Log.d("StoryRepositoryImpl", "fetchAndPeristStories")
        return service.getTopStories()
            .toObservable()
            .flatMapIterable { it }
            .map {
                StoryEntity(it)
            }
            .toList()
            .flatMap {
                dao.insert(it)
                Single.just(it.size)
            }
    }

    private fun getStoryIdByRange(startPos: Int, pageSize: Int): Single<List<StoryEntity>> {
        return dao.getStories(startPos, pageSize)
    }
}