package com.example.week_ten_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.R
import com.example.week_ten_task.model.CommentsResponseItem

/**
 * RecyclerView Adapter for comment entity
 */
class CommentAdapter() : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    var commentList: MutableList<CommentsResponseItem> = ArrayList()
    class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.commentNameTxt)
        var email: TextView = view.findViewById(R.id.commentEmailTxt)
        var comment: TextView = view.findViewById(R.id.commentListTxt)

        fun bind(commentsResponseItem: CommentsResponseItem){
            name.text = commentsResponseItem.name
            email.text = commentsResponseItem.email
            comment.text = commentsResponseItem.body
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount(): Int {
        return commentList.size
    }
}