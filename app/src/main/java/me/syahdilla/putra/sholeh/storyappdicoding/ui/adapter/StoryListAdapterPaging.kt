package me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.syahdilla.putra.sholeh.storyappdicoding.R
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ItemStoryBinding
import me.syahdilla.putra.sholeh.storyappdicoding.sdfDisplayer
import me.syahdilla.putra.sholeh.storyappdicoding.sdfParser
import me.syahdilla.putra.sholeh.storyappdicoding.utils.tryRun
import java.util.*

class StoryListAdapterPaging(
    diffCallback: DiffUtil.ItemCallback<Story>
) : PagingDataAdapter<Story, StoryListAdapterPaging.ViewHolder>(
    diffCallback
) {

    var onItemClick: ItemStoryBinding.(Story) -> Unit = {}

    inner class ViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val story = getItem(position) ?: return@with
        tvItemName.text = story.name
        uploadDate.text = sdfDisplayer.format(sdfParser.parse(story.createdAt) as Date)
        tryRun(noLogs = false) {
            ivItemPhoto.load(story.photoUrl) {
                crossfade(true)
                crossfade(500)
                placeholder(R.drawable.loading_transparent)
            }
        }
        root.setOnClickListener {
            onItemClick(story)
        }
    }

}