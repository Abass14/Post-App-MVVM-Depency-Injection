package com.example.week_ten_task.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

class PostResponse : ArrayList<PostResponseItem>()

@Parcelize
@Entity(tableName = "post_table")
data class PostResponseItem(
    @SerializedName("userId") @ColumnInfo(name ="user_id") val user: Int,
    @SerializedName("id")
    @ColumnInfo(name ="id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("title") @ColumnInfo(name ="title")val theTitle: String,
    @SerializedName("body") @ColumnInfo(name ="body")val theBody: String
) : Parcelable