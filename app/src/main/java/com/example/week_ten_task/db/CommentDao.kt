package com.example.week_ten_task.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem

/**
 * Comment Dao is created here with three methods (Get, Insert, and Delete)
 */
@Dao
interface CommentDao {

    @Query("SELECT * FROM comment_table WHERE post_id = :post_id")
    fun getAllComments(post_id : Int) : LiveData<MutableList<CommentsResponseItem>>

    @Query("SELECT * FROM comment_table")
    fun getComments() : LiveData<MutableList<CommentsResponseItem>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(commentsResponseItem: CommentsResponseItem)

    @Query("DELETE FROM comment_table")
    fun deleteComments()
}