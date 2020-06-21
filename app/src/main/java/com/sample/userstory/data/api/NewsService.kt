package com.sample.userstory.data.api

import com.sample.userstory.data.entities.StorySchema
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsService {

    @GET("/v0/topstories.json")
    fun getTopStories(): Single<List<Int>>

    @GET("/v0/item/{storyId}.json")
    fun getStory(@Path("storyId") storyId: Int): Single<StorySchema>
}