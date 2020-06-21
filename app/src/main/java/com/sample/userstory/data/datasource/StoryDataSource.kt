package com.sample.userstory.data.datasource

import android.util.Log
import androidx.paging.PositionalDataSource
import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.ui.vo.Story
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction

class StoryDataSource(
    private val repository: StoryRepository,
    private val disposable: CompositeDisposable
) : PositionalDataSource<Story>() {

    private var pageSize = 0

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Story>) {
        val startPos = params.startPosition
        Log.d("StoryDataSource", "loadRange : $startPos")
        val subscription = repository.getStoryRange(startPos, pageSize)
            .doOnSuccess {
                callback.onResult(it)
            }.subscribe()
        disposable.add(subscription)
    }

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<Story>
    ) {
        val startPos = params.requestedStartPosition
        pageSize = params.pageSize
        Log.d("StoryDataSource", "loadInitial : $startPos , $pageSize")
        val allStories = repository.fetchAndPeristStories()
        allStories.flatMap {
            Single.zip<Int, List<Story>, Pair<Int, List<Story>>>(
                Single.just(it),
                repository.getStoryRange(startPos, pageSize),
                BiFunction { t1, t2 ->
                    Pair(t1, t2)
                })
        }.doOnSuccess {
            callback.onResult(it.second, 0)
        }.subscribe()
    }
}