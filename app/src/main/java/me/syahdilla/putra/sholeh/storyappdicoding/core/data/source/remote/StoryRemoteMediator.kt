package me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.syahdilla.putra.sholeh.storyappdicoding.UserManager
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.RemoteKeysEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.room.story.StoryDatabase
import me.syahdilla.putra.sholeh.storyappdicoding.isUITest
import me.syahdilla.putra.sholeh.storyappdicoding.utils.*
import org.koin.core.annotation.Factory

private const val INITIAL_PAGE = 1

@Factory([StoryRemoteMediator::class])
@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val retrofitManager: RetrofitManager,
    private val storyDatabase: StoryDatabase
) : RemoteMediator<Int, StoryEntity>() {

    private val logger by customLogger()

    var user: me.syahdilla.putra.sholeh.storyappdicoding.core.data.User? = null

    override suspend fun initialize() = InitializeAction.LAUNCH_INITIAL_REFRESH

    override suspend fun load(loadType: LoadType, state: PagingState<Int, StoryEntity>) = try {
        if (isUITest) EspressoIdlingResource.increment()
        logger.debug { "loadType: ${loadType.name}" }

        val pageOrResult = when (loadType) {
            LoadType.REFRESH -> getRemoteKeyClosestToCurrentPosition(state)?.nextKey?.minus(1) ?: INITIAL_PAGE
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                remoteKeys?.prevKey
                    ?: MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextKey
                    ?: MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        if (pageOrResult is Int) {
            logger.debug { "request new load page $pageOrResult" }
            val response = withContext(Dispatchers.IO) {
                tryRun(noLogs = false) {
                    val res = retrofitManager.api.getAllStories(
                        token = "Bearer ${(user ?: UserManager.session).token}",
                        //withLocation = false,
                        page = pageOrResult,
                        size = state.config.pageSize
                    ).execute()
                    logger.debug("HTTP Response: ${res.code()}", res.message())
                    res.body() ?: res.errorBody()?.string()?.asObject() ?: throw NullPointerException("Body is null!")
                }
            }.getOrThrow()

            if (response.error) throw IllegalStateException("response is error true!")

            val endOfPaginationReached = response.listStory.isEmpty()

            storyDatabase.withTransaction {
                if (loadType == LoadType.REFRESH)
                    storyDatabase.deleteAll()

                val prevKey = if (pageOrResult == 1) null else pageOrResult - 1
                val nextKey = if (endOfPaginationReached) null else pageOrResult + 1
                val keys = response.listStory.map {
                    RemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                storyDatabase.remoteKeysDao.insertAll(keys)
                storyDatabase.insertStories(response.listStory.map { DataMapper.mapDomainToEntity(it) })
                MediatorResult.Success(
                    endOfPaginationReached = endOfPaginationReached
                )
            }
        } else pageOrResult as MediatorResult.Success
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        e.printStackTrace()
        MediatorResult.Error(e)
    } finally {
        if (isUITest) EspressoIdlingResource.decrement()
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoryEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            storyDatabase.remoteKeysDao.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoryEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            storyDatabase.remoteKeysDao.getRemoteKeysId(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoryEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                storyDatabase.remoteKeysDao.getRemoteKeysId(id)
            }
        }

    }
}