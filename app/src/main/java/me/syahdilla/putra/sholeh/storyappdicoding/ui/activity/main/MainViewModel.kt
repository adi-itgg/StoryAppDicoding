package me.syahdilla.putra.sholeh.storyappdicoding.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import kotlinx.coroutines.flow.map
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.usecase.story.StoryUseCase
import me.syahdilla.putra.sholeh.storyappdicoding.utils.DataMapper
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class MainViewModel(
    private val storyPaging: StoryUseCase
): ViewModel() {

    fun getStoriesMediator() = storyPaging.getStoriesMediatorFlow()
        .cachedIn(viewModelScope)
        .map { pagingData -> pagingData.map { entity -> DataMapper.mapEntityToDomain(entity) } }

}