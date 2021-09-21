package com.example.week_ten_task.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponseItem

@Dao
interface CommentDao {

    @Query("SELECT * FROM comment_table WHERE post_id = :post_id")
    fun getAllComments(post_id : Int) : LiveData<MutableList<CommentsResponseItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertComment(commentsResponseItem: CommentsResponseItem)

    @Query("DELETE FROM comment_table")
    fun deleteComments()
}