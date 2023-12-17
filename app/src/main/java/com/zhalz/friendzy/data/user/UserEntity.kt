package com.zhalz.friendzy.data.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    val name: String?,
    val phone: String?,
    val school: String?,
    val photo: String?,
    val description: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
