package com.zhalz.friendzy.data.friend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FriendEntity(
    val name: String,
    val birth: String,
    val description: String,
    val photo: String,
    @ColumnInfo("gender", defaultValue = "")
    val school: String = ""
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
