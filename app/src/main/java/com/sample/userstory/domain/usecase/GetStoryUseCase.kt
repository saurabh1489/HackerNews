package com.sample.userstory.domain.usecase

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.sample.userstory.data.datasource.StoryDataSourceFactory
import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.ui.vo.Story
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class GetStoryUseCase @Inject constructor(val repository: StoryRepository) {
    operator fun invoke(disposable: CompositeDisposable, onError: (String?) -> Unit): Observable<PagedList<Story>> {
        val dataSourceFactory = StoryDataSourceFactory(repository, disposable, onError)
        val config = PagedList.Config.Builder().setEnablePlaceholders(false)
            .setPageSize(20).build();
        return RxPagedListBuilder(dataSourceFactory, config).buildObservable()
    }
}