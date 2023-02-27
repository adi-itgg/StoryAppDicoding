package me.syahdilla.putra.sholeh.storyappdicoding.network

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story

interface StoryPaging {

    fun getStoriesMediatorFlow(): Flow<PagingData<Story>>

}