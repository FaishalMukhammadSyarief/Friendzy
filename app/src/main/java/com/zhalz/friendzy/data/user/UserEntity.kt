package com.zhalz.friendzy.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int = 0,
    val name: String?,
    val school: String?,
    val description: String?
)