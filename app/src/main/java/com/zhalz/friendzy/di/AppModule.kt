package com.zhalz.friendzy.di

import android.content.Context
import com.zhalz.friendzy.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideFriendManager(@ApplicationContext context: Context) = AppDatabase.getInstance(context)
}