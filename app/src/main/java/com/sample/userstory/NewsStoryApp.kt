package com.sample.userstory

import android.app.Application
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.data.repository.ThemeRepository
import com.sample.userstory.di.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class NewsStoryApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var themeRepository: ThemeRepository

    private val theme by lazy {
        themeRepository.getTheme()
    }

    override fun onCreate() {
        DaggerAppComponent.factory().create(this).inject(this)
        super.onCreate()
    }

    private fun setTheme() {
        if (theme == Theme.RED) {
            setTheme(R.style.AppTheme_Red)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    override fun androidInjector() = androidInjector
}