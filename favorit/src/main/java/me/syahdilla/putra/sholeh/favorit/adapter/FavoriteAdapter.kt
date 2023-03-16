package me.syahdilla.putra.sholeh.favorit.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.syahdilla.putra.sholeh.favorit.databinding.ItemFavStoryBinding
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.sdfDisplayer
import me.syahdilla.putra.sholeh.story.core.sdfParser
import me.syahdilla.putra.sholeh.story.core.utils.tryRun
import me.syahdilla.putra.sholeh.storyappdicoding.R
import java.util.*

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Story, newItem: Story) = oldItem == newItem
    })
    var onItemClick: ItemFavStoryBinding.(Story) -> Unit = {}
    var onRemoveItemClick: ItemFavStoryBinding.(Story) -> Unit = {}

    inner class ViewHolder(val binding: ItemFavStoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemFavStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder.binding) {
        val story = differ.currentList[position]
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
        remove.setOnClickListener {
            onRemoveItemClick(story)
        }
    }


}