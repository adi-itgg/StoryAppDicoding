package me.syahdilla.putra.sholeh.storyappdicoding.ui.storydetails

import android.os.Bundle
import coil.load
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.data.Story
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ActivityStoryDetailsBinding
import me.syahdilla.putra.sholeh.storyappdicoding.sdfDisplayer
import me.syahdilla.putra.sholeh.storyappdicoding.sdfParser
import me.syahdilla.putra.sholeh.storyappdicoding.utils.tryRun
import me.syahdilla.putra.sholeh.storyappdicoding.ui.BaseActivity
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
        Unit
    }

}