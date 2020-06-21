package com.sample.userstory.di

import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.data.repository.StoryRepositoryImpl
import com.sample.userstory.data.repository.ThemeRepository
import com.sample.userstory.data.repository.ThemeRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository

    @Binds
    abstract fun bindThemeRepository(themeRepositoryImpl: ThemeRepositoryImpl): ThemeRepository
}