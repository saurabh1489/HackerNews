package com.sample.userstory.ui.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.sample.userstory.data.repository.Theme
import com.sample.userstory.domain.usecase.GetThemeUseCase
import com.sample.userstory.domain.usecase.SetThemeUseCase
import com.sample.userstory.ui.vo.Story
import javax.inject.Inject

class ThemeViewModel @Inject constructor(
    private val setThemeUseCase: SetThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase
) : ViewModel() {

    private val _theme = MutableLiveData<Theme>()
    val theme: LiveData<Theme>
        get() = theme

    init {
        val theme = getTheme()
        _theme.value = theme
    }

    fun setTheme(checked: Boolean) {
        if (checked) {
            setThemeUseCase(Theme.RED.ordinal)
        } else {
            setThemeUseCase(Theme.BLUE.ordinal)
        }
    }

    fun getTheme() = getThemeUseCase()

}