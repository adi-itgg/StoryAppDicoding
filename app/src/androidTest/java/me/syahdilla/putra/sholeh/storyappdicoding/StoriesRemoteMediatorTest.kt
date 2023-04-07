package me.syahdilla.putra.sholeh.storyappdicoding

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.story.core.data.source.local.room.story.StoryDatabaseImpl
import org.junit.After
import org.junit.runner.RunWith

@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoriesRemoteMediatorTest {

    private var mockDb: StoryDatabase = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        StoryDatabaseImpl::class.java
    ).allowMainThreadQueries().build()

   /* @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val remoteMediator = StoryRemoteMediator(
            RetrofitManagerImpl(),
            mockDb,
        )
        remoteMediator.user =
            me.syahdilla.putra.sholeh.story.core.domain.model.User("Test", "Test123", "123")
        val pagingState = PagingState<Int, StoryEntity>(
            emptyList(),
            null,
            PagingConfig(10),
            10
        )
        val result = remoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }*/

    @After
    fun tearDown() {
        (mockDb as StoryDatabaseImpl).clearAllTables()
    }


}