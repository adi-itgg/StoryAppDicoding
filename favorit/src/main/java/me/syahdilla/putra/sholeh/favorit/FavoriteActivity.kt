package me.syahdilla.putra.sholeh.favorit

import android.os.Bundle
import androidx.lifecycle.flowWithLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import me.syahdilla.putra.sholeh.favorit.adapter.FavoriteAdapter
import me.syahdilla.putra.sholeh.favorit.databinding.ActivityFavoriteBinding
import me.syahdilla.putra.sholeh.favorit.di.favoriteModule
import me.syahdilla.putra.sholeh.favorit.domain.repository.FavoriteRepository
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.DataMapper
import me.syahdilla.putra.sholeh.story.core.utils.safeLaunch
import me.syahdilla.putra.sholeh.story.core.utils.safeRunOnce
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails.StoryDetailsActivity
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class FavoriteActivity: BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate) {

    private val favoriteRepository: FavoriteRepository by inject()
    private lateinit var favAdapter: FavoriteAdapter

    override suspend fun onInitialize(savedInstanceState: Bundle?) = with (binding.root) {

        // load dependecy
        loadKoinModules(favoriteModule)

        favAdapter = FavoriteAdapter()
        favAdapter.onItemClick = {
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
        favAdapter.onRemoveItemClick = {
            safeRunOnce(7192) {
                favoriteRepository.deleteFavorite(it.id)
            }
        }

        layoutManager = LinearLayoutManager(mThis)
        adapter = favAdapter


        safeLaunch {
            favoriteRepository.getFavorites().flowWithLifecycle(lifecycle).collectLatest { dataList ->
                favAdapter.differ.submitList(dataList.map { data -> DataMapper.mapEntityToDomain(data) })
            }
        }
        Unit
    }

}