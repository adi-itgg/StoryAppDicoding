package me.syahdilla.putra.sholeh.favorit.domain.usecase

import kotlinx.coroutines.flow.map
import me.syahdilla.putra.sholeh.favorit.data.FavoriteRepositoryImpl
import me.syahdilla.putra.sholeh.story.core.domain.model.Story
import me.syahdilla.putra.sholeh.story.core.utils.DataMapper

class FavoriteUseCaseImpl(
    private val repository: FavoriteRepositoryImpl
): FavoriteUseCase {

    override fun getFavorites() = repository.getFavorites().map { flow -> flow.map { entity -> DataMapper.mapEntityToDomain(entity) } }

    override suspend fun addFavorite(story: Story) =
        repository.addFavorite(DataMapper.mapDomainToEntity(story))

    override suspend fun getFavorite(id: String) = repository.getFavorite(id)?.let { DataMapper.mapEntityToDomain(it) }

    override suspend fun deleteFavorite(id: String) = repository.deleteFavorite(id)

}