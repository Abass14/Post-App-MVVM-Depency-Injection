package com.example.week_ten_task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_ten_task.R
import com.example.week_ten_task.model.UserMomentModel

/**
 * RecyclerView Adapter for static Moments
 */
class UserMomentAdapter(private val momentsList: ArrayList<UserMomentModel>) : RecyclerView.Adapter<UserMomentAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val momentImage: ImageView = view.findViewById(R.id.moments)
        val name: TextView = view.findViewById(R.id.profile_name)

        fun bind(userMomentModel: UserMomentModel){
            val imageId = userMomentModel.image
            momentImage.setImageResource(imageId)
            name.text = userMomentModel.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(momentsList[position])
    }

    override fun getItemCount(): Int {
        return momentsList.size
    }
}