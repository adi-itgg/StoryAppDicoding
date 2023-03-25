package me.syahdilla.putra.sholeh.favorit.domain.usecase

import me.syahdilla.putra.sholeh.favorit.data.FavoriteRepositoryImpl
import me.syahdilla.putra.sholeh.story.core.data.source.local.entity.StoryEntity

class FavoriteUseCaseImpl(
    private val repository: FavoriteRepositoryImpl
): FavoriteUseCase {

    override fun getFavorites() = repository.getFavorites()

    override suspend fun addFavorite(storyEntity: StoryEntity) = repository.addFavorite(storyEntity)

    override suspend fun getFavorite(id: String) = repository.getFavorite(id)

    override suspend fun deleteFavorite(id: String) = repository.deleteFavorite(id)

}