package com.example.week_ten_task.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

class CommentsResponse : ArrayList<CommentsResponseItem>()

@Entity(tableName = "comment_table")
data class CommentsResponseItem(
    @ColumnInfo(name = "post_id") val postId: Int,
    @SerializedName("id") @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "comment_id")val commentId: Int,
    @ColumnInfo(name = "name")val name: String?,
    @ColumnInfo(name = "email")val email: String?,
    @ColumnInfo(name = "body")val body: String?,
)

//@Entity(foreignKeys = arrayOf(
//    ForeignKey(
//        entity = PostResponseItem::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("comment_id"),
//        onDelete = ForeignKey.CASCADE
//    )
//) )