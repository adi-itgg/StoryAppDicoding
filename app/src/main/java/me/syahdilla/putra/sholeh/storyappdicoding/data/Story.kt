package me.syahdilla.putra.sholeh.storyappdicoding.data


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "story")
@Parcelize
@Serializable
data class Story(
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
) : Parcelable