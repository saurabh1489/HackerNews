package com.sample.userstory.data.repository

import com.sample.userstory.data.api.NewsService
import com.sample.userstory.data.db.StoryDao
import com.sample.userstory.util.*
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.Mockito.times
import java.io.IOException
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class StoryRepositoryImplTest {

    private lateinit var storyRepository: StoryRepository
    private val storyDao = mock<StoryDao>()
    private val newsService = mock<NewsService>()

    @Before
    fun setUp() {
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        storyRepository = StoryRepositoryImpl(storyDao, newsService)
    }

    @Test
    fun getStories_validStoryId_notifySuccess() {
        val dummyStory = getStory()
        `when`(newsService.getStory(anyInt())).thenReturn(Single.just(dummyStory))

        val observer = storyRepository.getStory(1).test()

        observer.assertSubscribed()
        observer.assertComplete()
        observer.assertNoErrors()
            .assertValue { story ->
                story.title.equals(dummyStory.title) && story.url?.equals(
                    dummyStory.url
                )!!
            }
    }

    @Test
    fun getStories_networkError_notifyFailure() {
        `when`(newsService.getStory(anyInt())).thenReturn(Single.error(UnknownHostException()))

        val observer = storyRepository.getStory(1).test()

        observer.assertTerminated()
        observer.assertError(UnknownHostException::class.java)
    }

    @Test
    fun getStoryRange_validRange_notifySuccess() {
        val pageSize = 20
        val dummyStory = getStory()
        `when`(newsService.getStory(anyInt())).thenReturn(Single.just(dummyStory))
        `when`(storyDao.getStories(anyInt(), anyInt())).thenReturn(
            Single.just(
                getStoryListFromDB(
                    pageSize
                )
            )
        )

        val observer = storyRepository.getStoryRange(0, pageSize).test()

        observer.assertSubscribed()
        observer.assertNoErrors().assertValue { listStories ->
            listStories.size == 20 && listStories[0].title ==
                    dummyStory.title && listStories[0].url == dummyStory.url
        }
    }

    @Test
    fun getStoryRange_noNetwork_notifyError() {
        val pageSize = 20
        `when`(newsService.getStory(anyInt())).thenReturn(Single.error(UnknownHostException()))
        `when`(storyDao.getStories(anyInt(), anyInt())).thenReturn(
            Single.just(
                getStoryListFromDB(
                    pageSize
                )
            )
        )

        val observer = storyRepository.getStoryRange(0, pageSize).test()
        observer.assertError {
            it.cause is UnknownHostException
        }
    }

    @Test
    fun fetchAndPersistStories_success_notifySuccess() {
        `when`(newsService.getTopStories()).thenReturn(Single.just(getStoryIds(50)))
        `when`(storyDao.insert(any())).thenReturn(Single.just(getStoryIds(50).map {
            it.toLong()
        }))

        val observer = storyRepository.fetchAndPersistStories().test()
        verify(storyDao, times(1)).insert(any())
        observer.assertSubscribed()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it == 50
            }
    }

    @Test
    fun fetchAndPersistStories_networkError_notifyError() {
        `when`(newsService.getTopStories()).thenReturn(Single.error(UnknownHostException()))
        `when`(storyDao.insert(any())).thenReturn(Single.just(getStoryIds(50).map {
            it.toLong()
        }))

        val observer = storyRepository.fetchAndPersistStories().test()

        verify(storyDao, times(0)).insert(any())
        observer.assertSubscribed()
            .assertTerminated()
            .assertError(UnknownHostException::class.java)
    }

    @Test
    fun fetchAndPersistStories_databaseError_notifyError() {
        `when`(newsService.getTopStories()).thenReturn(Single.just(getStoryIds(50)))
        `when`(storyDao.insert(any())).thenReturn(Single.error(IOException()))

        val observer = storyRepository.fetchAndPersistStories().test()
        observer.assertSubscribed()
            .assertTerminated()
            .assertError(IOException::class.java)
    }
}