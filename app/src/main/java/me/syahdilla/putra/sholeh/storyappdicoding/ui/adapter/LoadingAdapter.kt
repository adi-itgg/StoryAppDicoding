package me.syahdilla.putra.sholeh.storyappdicoding.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import me.syahdilla.putra.sholeh.storyappdicoding.databinding.ItemLoadingBinding
import org.koin.core.annotation.Factory

@Factory
class LoadingAdapter: LoadStateAdapter<LoadingAdapter.ViewHolder>() {
    
    var onRetryClick: () -> Unit = {}

    inner class ViewHolder(val binding: ItemLoadingBinding): RecyclerView.ViewHolder(binding.root) {
        init { binding.btnRetry.setOnClickListener { onRetryClick() } }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = with(holder.binding) {
        progressView.isVisible = loadState is LoadState.Loading
        btnRetry.isVisible = loadState is LoadState.Error
        errorText.isVisible = btnRetry.isVisible
        if (loadState is LoadState.Error)
            errorText.text = loadState.error.localizedMessage
    }

}