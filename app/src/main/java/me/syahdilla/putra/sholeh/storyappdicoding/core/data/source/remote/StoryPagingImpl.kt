package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabase
import org.koin.core.annotation.Factory

@Factory
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