package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.flowWithLifecycle
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.delay
import me.syahdilla.putra.sholeh.storyappdicoding.PREF_USER_SESSION
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story
import me.syahdilla.putra.sholeh.storyappdicoding.dataStore
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityMainBinding
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.LoadingAdapter
import me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter.StoryListAdapterPaging
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.addstory.AddStoryActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.login.LoginActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.maps.MapsActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails.StoryDetailsActivity
import me.syahdilla.putra.sholeh.storyappdicoding.utils.DataMapper
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeLaunch
import me.syahdilla.putra.sholeh.storyappdicoding.utils.safeRunOnce
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val adapterPaging: StoryListAdapterPaging by inject()
    private val footerAdapter: LoadingAdapter by inject()

    private val viewModel: MainViewModel by viewModel()

    private var hasCreatedNewStory = false
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode != RESULT_OK) return@registerForActivityResult
        hasCreatedNewStory = true
        adapterPaging.refresh()
    }

    override suspend fun onInitialize(savedInstanceState: Bundle?) = with(binding) {
        with(storyListRV) {
            layoutManager = LinearLayoutManager(mThis)
            setItemViewCacheSize(5)
            adapter = adapterPaging.withLoadStateFooter(footerAdapter)
        }

        footerAdapter.onRetryClick = {
            adapterPaging.retry()
        }

        adapterPaging.onItemClick = {
            StoryDetailsActivity::class.openActivity(
                useActivityTransition = false,
                ivItemPhoto to "image",
                tvItemName to "name",
                uploadDate to "date",
                profile to "profile"
            ) {
                putExtra(Story::class.java.simpleName, it)
            }
        }


        safeLaunch {
            viewModel.getStoriesMediator().flowWithLifecycle(lifecycle).collect { pagingData ->
                adapterPaging.submitData(pagingData.map { entity -> DataMapper.mapEntityToDomain(entity) })
                if (!hasCreatedNewStory) return@collect
                safeRunOnce(id = 90, cancelAndWait = true) {
                    delay(1_000)
                    hasCreatedNewStory = false
                    binding.storyListRV.scrollToPosition(0)
                }
            }
        }

        root.setOnRefreshListener {
            adapterPaging.refresh()
            root.isRefreshing = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) = with(menu) {
        menuInflater.inflate(R.menu.main_menu, this)
        true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.action_logout -> {
            safeRunOnce(99) {
                dataStore.edit { it.remove(PREF_USER_SESSION) }
                LoginActivity::class.openActivity()
                finish()
            }
            true
        }
        R.id.action_create_story -> {
            launcher.open(AddStoryActivity::class)
            true
        }
        R.id.action_maps -> {
            MapsActivity::class.openActivity()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}