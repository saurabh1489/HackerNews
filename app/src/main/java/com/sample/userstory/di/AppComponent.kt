package com.sample.userstory.di

import com.sample.userstory.NewsStoryApp
import com.sample.userstory.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        MainActivityModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<NewsStoryApp> {
    @Component.Factory
    abstract class Factory: AndroidInjector.Factory<NewsStoryApp>
}