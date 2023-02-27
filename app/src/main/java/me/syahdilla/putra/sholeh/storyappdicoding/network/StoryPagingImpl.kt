package me.syahdilla.putra.sholeh.storyappdicoding.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.syahdilla.putra.sholeh.storyappdicoding.database.story.StoryDatabase

class StoryPagingImpl(
    private val storyMediator: StoryRemoteMediator,
    private val storyDatabase: StoryDatabase
): StoryPaging {

    @OptIn(ExperimentalPagingApi::class)
    override fun getStoriesMediatorFlow() = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = storyMediator,
        pagingSourceFactory = { storyDatabase.getAllStories() }
    ).flow

}