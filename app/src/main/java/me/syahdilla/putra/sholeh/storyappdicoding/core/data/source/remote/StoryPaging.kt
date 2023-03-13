package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity

interface StoryPaging {

    fun getStoriesMediatorFlow(): Flow<PagingData<StoryEntity>>

}