package me.syahdilla.putra.sholeh.storyappdicoding.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.ListUpdateCallback
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import me.syahdilla.putra.sholeh.storyappdicoding.data.DataDummy
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.fake.FakeStoryPaging
import me.syahdilla.putra.sholeh.storyappdicoding.network.StoryPaging
import me.syahdilla.putra.sholeh.storyappdicoding.ui.main.MainViewModel
import me.syahdilla.putra.sholeh.storyappdicoding.utils.KoinTesting
import me.syahdilla.putra.sholeh.storyappdicoding.utils.MainDispatcherRule
import org.junit.Rule
import org.junit.Test
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExperimentalCoroutinesApi
class MainViewModelTest: KoinTesting {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    private val dummyListModule = module { factory { DataDummy.generateDummyStoryResponse() } }

    override fun extraModules(): Module = module {
        factory { Dispatchers.Main } bind CoroutineDispatcher::class
        factory { listOf<Story>() }
        factory {
            object : ListUpdateCallback {
                override fun onInserted(position: Int, count: Int) {}
                override fun onRemoved(position: Int, count: Int) {}
                override fun onMoved(fromPosition: Int, toPosition: Int) {}
                override fun onChanged(position: Int, count: Int, payload: Any?) {}
            }
        } bind ListUpdateCallback::class
        factoryOf(::FakeStoryPaging) bind StoryPaging::class
    }

    override fun dispose() {
        unloadKoinModules(dummyListModule)
        super.dispose()
    }


    @Test
    fun `when Get Stories Should Not Null and Return Data`() = runTest {
        loadKoinModules(dummyListModule)
        val mainViewModel: MainViewModel by inject()
        val stories: List<Story> by inject()
        val actualStories = mainViewModel.getStoriesMediator().first()
        val differ = AsyncPagingDataDiffer<Story>(get(), get(), get())
        differ.submitData(actualStories)

        assertNotNull(differ.snapshot())
        assertEquals(stories.size, differ.snapshot().size)
        assertEquals(stories[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when Get Stories Empty Should Return No Data`() = runTest {
        val mainViewModel: MainViewModel by inject()
        val actualStories = mainViewModel.getStoriesMediator().first()
        val differ = AsyncPagingDataDiffer<Story>(get(), get(), get())
        differ.submitData(actualStories)

        assertEquals(0, differ.snapshot().size)
    }

}