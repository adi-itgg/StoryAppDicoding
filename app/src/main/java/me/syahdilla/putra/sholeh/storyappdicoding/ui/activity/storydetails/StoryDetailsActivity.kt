package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails

import android.os.Bundle
import coil.load
import kotlinx.coroutines.Job
import kotlinx.coroutines.suspendCancellableCoroutine
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.sdfDisplayer
import me.syahdilla.putra.sholeh.story.core.sdfParser
import me.syahdilla.putra.sholeh.story.core.utils.safeLaunch
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityStoryDetailsBinding
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.util.*
import kotlin.coroutines.resume

class StoryDetailsActivity: BaseActivity<ActivityStoryDetailsBinding>(ActivityStoryDetailsBinding::inflate) {

    private val viewModel: StoryDetailsViewModel by viewModel()
    private val bookmarkUseCase: Any by inject(qualifier = named("favoriteUseCase"))

    override val showTopLeftBackButton = true

    override suspend fun onInitialize(savedInstanceState: Bundle?) = with(binding) {

        title = getString(R.string.title_story_details)

        val story = intent.getParcelableExtra<Story>(Story::class.java.simpleName) ?: run {
            logger.error("Story data is null!, please put data with Story::class.java.simpleName as key!")
            return@with finish()
        }

        tvDetailName.text = story.name
        tvDetailDescription.text = story.description
        uploadDate.text = sdfDisplayer.format(sdfParser.parse(story.createdAt) as Date)
        tryRun {
            ivDetailPhoto.load(story.photoUrl)
        }.onFailure {
            logger.error("failure load image correctly!")
        }

        suspend fun updateBtnFavUI() {
            btnFavorite.setImageResource(
                if (viewModel.isInFavorite(bookmarkUseCase, story.id))
                    R.drawable.round_favorite_24
                else
                    R.drawable.baseline_favorite_border_24
            )
        }


        if (viewModel.isInstalledFavorite()) {
            updateBtnFavUI()
        } else {
            btnFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }

        var job: Job? = null
        btnFavorite.setOnClickListener {
            if (job != null) return@setOnClickListener
            job = safeLaunch {
                if (!viewModel.isInstalledFavorite()) {
                    val isSuccess = suspendCancellableCoroutine { conti ->
                        viewModel.installFavorite().addOnCompleteListener {
                            conti.resume(it.isSuccessful)
                        }
                    }
                    if (!isSuccess) {
                        return@safeLaunch
                    }
                }
                viewModel.addOrDeleteFavorite(bookmarkUseCase, story)
                updateBtnFavUI()
            }
            job?.invokeOnCompletion {
                job = null
            }
        }
    }

}