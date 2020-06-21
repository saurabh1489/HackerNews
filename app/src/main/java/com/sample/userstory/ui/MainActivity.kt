package com.sample.userstory.ui

import android.content.res.Resources
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.sample.userstory.R
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.ui.common.ThemeViewModel
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val themeViewModel: ThemeViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        val currentTheme = themeViewModel.getTheme()
        if (currentTheme == Theme.RED) {
            theme.applyStyle(R.style.AppTheme_Red, true)
        } else {
            theme.applyStyle(R.style.AppTheme_Blue, true)
        }
        return theme
    }

    override fun androidInjector() = androidInjector
}