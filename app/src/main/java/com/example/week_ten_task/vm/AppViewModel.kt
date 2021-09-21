package com.example.week_ten_task.vm

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponse
import com.example.week_ten_task.model.PostResponseItem
import com.example.week_ten_task.network.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun getPostList() : LiveData<MutableList<PostResponseItem>> = repository.getAllPosts()

    fun getSinglePost() : LiveData<PostResponseItem> = repository.getSinglePostLiveData

    fun getCommentList(postId: Int) : LiveData<MutableList<CommentsResponseItem>> = repository.getComments(postId)

    fun getPostFromApi(context: Context){
        viewModelScope.launch {
            try {
                repository.getPostsFromApi()
            }catch (e: Exception){
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun getCommentFromApi(postId: Int){
        viewModelScope.launch {
            try {
                repository.getCommentFromApi(postId)
            }catch (e: Exception){}
        }
    }

    fun addPost(postResponseItem: PostResponseItem){
        viewModelScope.launch {
            try {
                repository.addPostToDatabase(postResponseItem)
            }catch (e: Exception){}
        }
    }

    fun addComment(commentsResponseItem: CommentsResponseItem, postId: Int){
        viewModelScope.launch {
            try {
                repository.addCommentToDatabase(commentsResponseItem, postId)
            }catch (e: Exception){}
        }
    }

    fun getSinglePostCall(postId: Int){
        viewModelScope.launch {
            try {
                repository.getSinglePost(postId)
            }catch (e: Exception){}
        }
    }
}