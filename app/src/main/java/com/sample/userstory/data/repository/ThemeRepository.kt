package com.sample.userstory.data.repository

enum class Theme {
    BLUE,
    RED
}

const val PREF_CONFIGURATION = "configuration"
const val KEY_THEME = "theme"

interface ThemeRepository {
    fun getTheme(): Theme
    fun saveTheme(theme: Int)
}