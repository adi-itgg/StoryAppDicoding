package me.syahdilla.putra.sholeh.storyappdicoding.fake

import kotlinx.coroutines.flow.flowOf
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.data.StoryPagingSourceTest
import me.syahdilla.putra.sholeh.storyappdicoding.network.StoryPaging

class FakeStoryPaging(
    private val data: List<Story>
): StoryPaging {

    override fun getStoriesMediatorFlow() = flowOf(StoryPagingSourceTest.snapshot(data))

}