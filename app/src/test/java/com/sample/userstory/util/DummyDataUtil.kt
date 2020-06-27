package com.sample.userstory.util

import com.sample.userstory.data.entities.StoryEntity
import com.sample.userstory.data.entities.StorySchema
import com.sample.userstory.ui.vo.Story

fun getStory(): StorySchema {
    return StorySchema("Test", "http://test.com")
}

fun getStoryListFromDB(size: Int): List<StoryEntity> {
    val mutableList = mutableListOf<StoryEntity>()
    for (i in 1..size) {
        mutableList.add(StoryEntity(i))
    }
    return mutableList
}

fun getStoryIds(size: Int): List<Int> {
    val mutableList = mutableListOf<Int>()
    for (i in 1..size) {
        mutableList.add(i)
    }
    return mutableList
}

fun getStories(size: Int): List<Story> {
    val mutableList = mutableListOf<Story>()
    for (i in 1..size) {
        mutableList.add(Story("Test", "http://test.com"))
    }
    return mutableList
}