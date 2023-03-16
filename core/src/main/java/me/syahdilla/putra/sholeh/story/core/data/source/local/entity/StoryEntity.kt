package me.syahdilla.putra.sholeh.story.core.data.source.local.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "story")
data class StoryEntity(
    @PrimaryKey
    @SerialName("id")
    val id: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("lat")
    val lat: Double? = null,
    @SerialName("lon")
    val lon: Double? = null,
    @SerialName("name")
    val name: String,
    @SerialName("photoUrl")
    @ColumnInfo(name = "photo_url")
    val photoUrl: String
)