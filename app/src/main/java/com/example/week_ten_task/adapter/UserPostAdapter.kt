package com.example.week_ten_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.R
import com.example.week_ten_task.model.PostResponseItem

/**
 * RecyclerView Adapter for Post entity
 */
class UserPostAdapter(private val postClick: OnPostClickListener) : RecyclerView.Adapter<UserPostAdapter.PostViewHolder>() {

    var postList : MutableList<PostResponseItem> = mutableListOf()


    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var title: TextView = view.findViewById(R.id.postTitle)
        var post: TextView = view.findViewById(R.id.postBody)
        var postCardView: CardView = view.findViewById(R.id.postCardView)

        fun bind(postResponseItem: PostResponseItem){
            title.text = postResponseItem.theTitle
            post.text = postResponseItem.theBody
        }

    }

    interface OnPostClickListener {

        fun onPostClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.post_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(postList[position])
        holder.postCardView.setOnClickListener {
            postClick.onPostClicked(position)
        }

    }

    override fun getItemCount(): Int {
        return postList.size
    }

}