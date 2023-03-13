package me.syahdilla.putra.sholeh.storyappdicoding.fake

import kotlinx.coroutines.flow.flowOf
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.StoryPaging
import me.syahdilla.putra.sholeh.storyappdicoding.data.StoryPagingSourceTest

class FakeStoryPaging(
    private val data: List<StoryEntity>
): StoryPaging {

    override fun getStoriesMediatorFlow() = flowOf(StoryPagingSourceTest.snapshot(data))

}