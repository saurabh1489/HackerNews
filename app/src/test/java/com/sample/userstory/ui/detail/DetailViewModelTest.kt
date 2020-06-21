package com.sample.userstory.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.sample.userstory.ui.login.LoginViewModel
import com.sample.userstory.util.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

@RunWith(JUnit4::class)
class DetailViewModelTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun setUp() {
        detailViewModel = DetailViewModel()
    }

    @Test
    fun init_NoObserverNotified() {
        val observer = mock<Observer<String>>()
        detailViewModel.contentUrl.observeForever(observer)

        Mockito.verify(observer, Mockito.times(0)).onChanged(anyString())
    }

    @Test
    fun loadUrl_urlProvided_observerNotified() {
        val observer = mock<Observer<String>>()
        detailViewModel.contentUrl.observeForever(observer)

        detailViewModel.loadUrl("http://testurl.com")

        Mockito.verify(observer, Mockito.times(1)).onChanged("http://testurl.com")
    }
}