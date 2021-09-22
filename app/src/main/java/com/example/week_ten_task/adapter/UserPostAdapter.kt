package com.example.week_ten_task.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.R
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.ui.HomeFragment

class UserPostAdapter(private val postClick: OnPostClickListener) : RecyclerView.Adapter<UserPostAdapter.PostViewHolder>() {

    companion object{
        var postList : MutableList<PostResponseItem> = mutableListOf()
    }


    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var title = view.findViewById<TextView>(R.id.postTitle)
        var post = view.findViewById<TextView>(R.id.postBody)
        var postCardView = view.findViewById<CardView>(R.id.postCardView)

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