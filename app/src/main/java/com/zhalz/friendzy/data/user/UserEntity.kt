package com.zhalz.friendzy.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int = 0,
    val id: Int?,
    val name: String?,
    val school: String?,
    val description: String?,
    val photo: String?,
    @field:SerializedName("like_by_you")
    val liked: Boolean?
)