package com.example.week_ten_task.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Comment Response Entity
 */
class CommentsResponse : ArrayList<CommentsResponseItem>()

@Parcelize
@Entity(tableName = "comment_table")
data class CommentsResponseItem(
    @ColumnInfo(name = "post_id") val postId: Int,
    @SerializedName("id") @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "comment_id")val commentId: Int,
    @ColumnInfo(name = "name")val name: String?,
    @ColumnInfo(name = "email")val email: String?,
    @ColumnInfo(name = "body")val body: String?,
) : Parcelable

