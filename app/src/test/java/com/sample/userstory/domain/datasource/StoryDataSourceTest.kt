package com.sample.userstory.domain.datasource

import androidx.paging.PositionalDataSource.*
import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.ui.vo.Story
import com.sample.userstory.util.getStories
import com.sample.userstory.util.mock
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.net.UnknownHostException


@RunWith(JUnit4::class)
class StoryDataSourceTest {

    private lateinit var storyDataSource: StoryDataSource
    private val repository = mock<StoryRepository>()
    private val onError = mock<(String?) -> Unit>()
    private val disposable = CompositeDisposable()

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        storyDataSource = StoryDataSource(repository, disposable, onError)
    }

    @Test
    fun loadRange_success_callbackCalled() {
        `when`(repository.getStoryRange(anyInt(), anyInt()))
            .thenReturn(Single.just(getStories(20)))

        val params = LoadRangeParams(0, 20)
        val callback = mock<LoadRangeCallback<Story>>()

        storyDataSource.loadRange(params, callback)

        verify(callback, times(1)).onResult(anyList())
        verify(onError, times(0)).invoke(ArgumentMatchers.anyString())
        assertThat(disposable.size(), `is`(1))
    }

    @Test
    fun loadRange_error_callbackCalled() {
        `when`(repository.getStoryRange(anyInt(), anyInt()))
            .thenReturn(Single.error(UnknownHostException("Error message")))

        val params = LoadRangeParams(0, 20)
        val callback = mock<LoadRangeCallback<Story>>()

        storyDataSource.loadRange(params, callback)

        verify(callback, times(0)).onResult(anyList())
        verify(onError, times(1)).invoke(ArgumentMatchers.anyString())
        assertThat(disposable.size(), `is`(1))
    }

    @Test
    fun loadInitial_success_callbackCalled() {
        `when`(repository.fetchAndPersistStories())
            .thenReturn(Single.just(50))
        `when`(repository.getStoryRange(anyInt(), anyInt()))
            .thenReturn(Single.just(getStories(20)))

        val params = LoadInitialParams(0, 20, 20, false)
        val callback = mock<LoadInitialCallback<Story>>()

        storyDataSource.loadInitial(params, callback)

        verify(callback, times(1)).onResult(anyList(), anyInt())
        verify(onError, times(0)).invoke(anyString())
        assertThat(disposable.size(), `is`(1))
    }

    @Test
    fun loadInitial_error_callbackCalled() {
        `when`(repository.fetchAndPersistStories())
            .thenReturn(Single.just(50))
        `when`(repository.getStoryRange(anyInt(), anyInt()))
            .thenReturn(Single.error(UnknownHostException("Error message")))

        val params = LoadInitialParams(0, 20, 20, false)
        val callback = mock<LoadInitialCallback<Story>>()

        storyDataSource.loadInitial(params, callback)

        verify(callback, times(0)).onResult(anyList(), ArgumentMatchers.anyInt())
        verify(onError, times(1)).invoke(ArgumentMatchers.anyString())
        assertThat(disposable.size(), `is`(1))
    }

}