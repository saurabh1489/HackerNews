package com.sample.userstory.di

import android.content.Context
import androidx.room.Room
import com.sample.userstory.NewsStoryApp
import com.sample.userstory.data.api.NewsService
import com.sample.userstory.data.db.StoryDao
import com.sample.userstory.data.db.StoryDb
import com.sample.userstory.data.repository.StoryRepository
import com.sample.userstory.data.repository.ThemeRepository
import com.sample.userstory.domain.usecase.*
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun newsService(): NewsService {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = BODY
        val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
        return Retrofit.Builder()
            .baseUrl("https://hacker-news.firebaseio.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .client(client)
            .build()
            .create(NewsService::class.java)
    }

    @Singleton
    @Provides
    fun storyDao(db: StoryDb): StoryDao {
        return db.storyDao()
    }

    @Singleton
    @Provides
    fun provideStoryDb(app: NewsStoryApp): StoryDb {
        return Room
            .databaseBuilder(app, StoryDb::class.java, "StoryDb.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    fun provideUseCases(storyRepo: StoryRepository, themeRepo: ThemeRepository) = UseCases(
        GetStoryUseCase(storyRepo),
        LoginUseCase(),
        SetThemeUseCase(themeRepo),
        GetThemeUseCase(themeRepo)
    )

    @Singleton
    @Provides
    fun provideApplicationContext(app: NewsStoryApp) : Context {
        return app.applicationContext
    }

}