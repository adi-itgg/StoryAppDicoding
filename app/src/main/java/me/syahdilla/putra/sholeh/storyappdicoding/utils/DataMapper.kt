package me.syahdilla.putra.sholeh.storyappdicoding.utils

import me.syahdilla.putra.sholeh.storyappdicoding.core.data.source.local.entity.StoryEntity
import me.syahdilla.putra.sholeh.storyappdicoding.core.domain.model.Story

object DataMapper {

    fun mapEntityToDomain(story: StoryEntity) = Story(
        id = story.id,
        createdAt = story.createdAt,
        description = story.description,
        lat = story.lat,
        lon = story.lon,
        name = story.name,
        photoUrl = story.photoUrl
    )

    fun mapDomainToEntity(story: Story) = StoryEntity(
        id = story.id,
        createdAt = story.createdAt,
        description = story.description,
        lat = story.lat,
        lon = story.lon,
        name = story.name,
        photoUrl = story.photoUrl
    )

}