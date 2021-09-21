package com.example.week_ten_task.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class PostWithComment(val user: Int, val id: Int, val theTitle: String, val theBody: String,
    val postId: Int, val commentId: Int, val name: String?, val email: String?, val body: String?,

    ) {
}