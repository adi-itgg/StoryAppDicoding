package me.syahdilla.putra.sholeh.storyappdicoding

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote.StoryRemoteMediator
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.repository.StoryRepository
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoriesRemoteMediatorTest {

    private var mockApi: StoryRepository =
        FakeStoryRepository()
    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabaseImpl::class.java
    ).allowMainThreadQueries().build()

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            mockApi,
            mockDb,
        )
        remoteMediator.user =
            me.syahdilla.putra.sholeh.storyappdicoding.core.data.User("Test", "Test123", "123")
        val pagingState = PagingState<Int, StoryEntity>(
            emptyList(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        (mockDb as StoryDatabaseImpl).clearAllTables()
    }


}