package com.example.week_ten_task.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.week_ten_task.model.PostResponse
import com.example.week_ten_task.model.PostResponseItem

/**
 * Post Dao is created here with three methods (Get, Insert, and Delete)
 */
@Dao
interface PostDao {

    @Query("SELECT * FROM post_table")
    fun getAllPosts() : LiveData<MutableList<PostResponseItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(postResponseItem: PostResponseItem)

    @Query("DELETE FROM post_table")
    fun deletePosts()
}