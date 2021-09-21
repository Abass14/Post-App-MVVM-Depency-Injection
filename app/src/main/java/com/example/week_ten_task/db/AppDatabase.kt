package com.example.week_ten_task.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem

@Database(entities = [PostResponseItem::class, CommentsResponseItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao() : AppDao
    abstract fun getCommentDao() : CommentDao

    companion object{
        @Volatile
        private var DB_INSTANCE: AppDatabase? = null


//        val MIGRATION_1_2 = object : Migration(1, 2){
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("CREATE TABLE IF NOT EXISTS `PostResponseItem` (`id` INTEGER, PRIMARY KEY(`id`))")
//            }
//        }

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