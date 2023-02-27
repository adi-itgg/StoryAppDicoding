package me.syahdilla.putra.sholeh.storyappdicoding

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.data.User
import me.syahdilla.putra.sholeh.storyappdicoding.database.story.StoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.database.story.StoryDatabaseImpl
import me.syahdilla.putra.sholeh.storyappdicoding.network.ApiRepository
import me.syahdilla.putra.sholeh.storyappdicoding.network.StoryRemoteMediator
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoriesRemoteMediatorTest {

    private var mockApi: ApiRepository = FakeApiRepository()
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
        remoteMediator.user = User("Test", "Test123", "123")
        val pagingState = PagingState<Int, Story>(
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