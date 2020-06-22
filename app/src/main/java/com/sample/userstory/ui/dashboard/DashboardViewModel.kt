package com.sample.userstory.ui.dashboard

import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sample.userstory.NewsStoryApp
import com.sample.userstory.domain.usecase.GetStoryUseCase
import com.sample.userstory.ui.vo.Story
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    val getStoryUseCase: GetStoryUseCase,
    app: NewsStoryApp
) : AndroidViewModel(app) {

    private val disposable = CompositeDisposable()
    private val _stories = MutableLiveData<PagedList<Story>>()
    val stories: LiveData<PagedList<Story>>
        get() = _stories

    init {
        getStories()
    }

    private fun getStories() {
        val subscription = getStoryUseCase(disposable) { errorMessage ->
            Toast.makeText(getApplication(), "Error: $errorMessage", Toast.LENGTH_LONG).show()
        }
            .subscribe {
                _stories.postValue(it)
            }
        disposable.add(subscription)
    }

    override fun onCleared() {
        disposable.dispose()
    }
}