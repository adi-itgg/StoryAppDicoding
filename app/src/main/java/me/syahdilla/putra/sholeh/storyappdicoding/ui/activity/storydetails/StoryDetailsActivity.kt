package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.storydetails

import android.os.Bundle
import coil.load
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityStoryDetailsBinding
import me.syahdilla.putra.sholeh.story.core.sdfDisplayer
import me.syahdilla.putra.sholeh.story.core.sdfParser
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.BaseActivity
import java.util.*

class StoryDetailsActivity: BaseActivity<ActivityStoryDetailsBinding>(ActivityStoryDetailsBinding::inflate) {

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

        val splitInstallManager = SplitInstallManagerFactory.create(mThis)
        val module = "favorite"
        if (splitInstallManager.installedModules.contains(module)) {

        }


        Unit
    }

}