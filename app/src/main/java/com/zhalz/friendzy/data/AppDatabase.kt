package com.zhalz.friendzy.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.zhalz.friendzy.data.friend.FriendDao
import com.zhalz.friendzy.data.friend.FriendEntity
import com.zhalz.friendzy.data.user.UserDao
import com.zhalz.friendzy.data.user.UserEntity

@Database(
    entities = [FriendEntity::class, UserEntity::class],
    version = 5,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(1,2),
        AutoMigration(2,3, AppDatabase.Migrate23::class),
        AutoMigration(3,4, AppDatabase.Migrate34::class)
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun friendDao(): FriendDao
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

    @RenameColumn("FriendEntity", "school", "gender")
    class Migrate23: AutoMigrationSpec

    @DeleteColumn("FriendEntity", "gender")
    class Migrate34: AutoMigrationSpec

}