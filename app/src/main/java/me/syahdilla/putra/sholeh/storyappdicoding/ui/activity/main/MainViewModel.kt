package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import me.syahdilla.putra.sholeh.story.core.domain.usecase.story.StoryUseCase
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val storyUseCase: StoryUseCase
): ViewModel() {

    fun getStoriesMediator() = storyUseCase.getStoriesMediator().cachedIn(viewModelScope)

}