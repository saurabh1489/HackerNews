package com.sample.userstory.domain.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.ui.vo.Story
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class StoryDataSourceFactory @Inject constructor(
    private val repository: StoryRepository,
    private val disposable: CompositeDisposable,
    private val onError: (String?) -> Unit
) : DataSource.Factory<Int, Story>() {

    val sourceLiveData = MutableLiveData<StoryDataSource>()
    var latestSource: StoryDataSource? = null

    override fun create(): DataSource<Int, Story> {
        latestSource = StoryDataSource(repository, disposable, onError)
        sourceLiveData.postValue(latestSource)
        return latestSource as DataSource<Int, Story>
    }
}