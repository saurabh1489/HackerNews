package com.sample.userstory.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import javax.inject.Inject


class ThemeRepositoryImpl @Inject constructor(private val context: Context) : ThemeRepository {
    override fun getTheme(): Theme {
        val pref = context.getSharedPreferences(PREF_CONFIGURATION, Context.MODE_PRIVATE)
        val theme = pref.getInt(KEY_THEME, 0)
        Log.d("Awasthi","getTheme $theme")
        return Theme.values()[theme]
    }

    override fun saveTheme(theme: Int) {
        val pref = context.getSharedPreferences(PREF_CONFIGURATION, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = pref.edit()
        editor.putInt(KEY_THEME, theme)
        editor.apply()
        Log.d("Awasthi","setTheme $theme")
    }
}