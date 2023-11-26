package com.zhalz.friendzy.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendEntity(
    val name: String,
    val birth: String,
    val description: String,
    val photo: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
