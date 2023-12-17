package com.zhalz.friendzy.di

import android.content.Context
import com.crocodic.core.helper.NetworkHelper
import com.zhalz.friendzy.api.ApiService
import com.zhalz.friendzy.data.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideFriendManager(@ApplicationContext context: Context) = AppDatabase.getInstance(context).friendDao()

    @Singleton
    @Provides
    fun provideUserManager(@ApplicationContext context: Context) = AppDatabase.getInstance(context).userDao()

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return NetworkHelper.provideApiService(
            "https://neptune74.crocodic.net/myfriend-kelasindustri/public/api/",
            okHttpClient = NetworkHelper.provideOkHttpClient(),
            converterFactory = listOf(GsonConverterFactory.create())
        )
    }

}