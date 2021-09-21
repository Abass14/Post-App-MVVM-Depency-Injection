package com.example.week_ten_task.di

import android.content.Context
import com.example.week_ten_task.db.AppDao
import com.example.week_ten_task.db.AppDatabase
import com.example.week_ten_task.db.CommentDao
import com.example.week_ten_task.network.Repository
import com.example.week_ten_task.network.RetroService
import com.example.week_ten_task.network.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun getAppDataBase(@ApplicationContext context: Context) : AppDatabase = AppDatabase.getDBInstance(context)

    @Provides
    @Singleton
    fun provideAppDao(appDatabase: AppDatabase) : AppDao = appDatabase.getAppDao()

    @Provides
    @Singleton
    fun provideCommentDao(appDatabase: AppDatabase) : CommentDao = appDatabase.getCommentDao()

    @Provides
    @Singleton
    fun provideRetroService() : RetroService = RetrofitInstance.retrofitInstance().create(RetroService::class.java)

    @Provides
    @Singleton
    fun provideRepository(api: RetroService, dao: AppDao, commentDao: CommentDao) : Repository = Repository(api, dao, commentDao)
}