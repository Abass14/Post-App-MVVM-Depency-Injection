package com.example.week_ten_task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem

/**
 * Application database is created here with two entities
 */
@Database(entities = [PostResponseItem::class, CommentsResponseItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPostDao() : PostDao
    abstract fun getCommentDao() : CommentDao

    companion object{
        @Volatile
        private var DB_INSTANCE: AppDatabase? = null

        fun getDBInstance(context: Context) : AppDatabase {

            if (DB_INSTANCE == null){
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "App_DB"
                ).allowMainThreadQueries().build()
            }
            return DB_INSTANCE!!
        }
    }
}