package com.zhalz.friendzy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhalz.friendzy.data.user.UserDao
import com.zhalz.friendzy.data.user.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, context.packageName)
                .fallbackToDestructiveMigration()
                .build()

            INSTANCE = instance
            return instance

        }
    }

}