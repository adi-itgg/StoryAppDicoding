package me.syahdilla.putra.sholeh.storyappdicoding.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import me.syahdilla.putra.sholeh.storyappdicoding.network.StoryPaging

class MainViewModel(
    private val storyPaging: StoryPaging
): ViewModel() {

    fun getStoriesMediator() = storyPaging.getStoriesMediatorFlow().cachedIn(viewModelScope)

}