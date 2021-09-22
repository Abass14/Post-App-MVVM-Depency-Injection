package com.example.week_ten_task.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.week_ten_task.db.AppDao
import com.example.week_ten_task.db.CommentDao
import com.example.week_ten_task.model.CommentsResponseItem
import com.example.week_ten_task.model.PostResponse
import com.example.week_ten_task.model.PostResponseItem
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: RetroService,
    private val dao: AppDao,
    private val commentDao: CommentDao
) {
    //uses the injected getAppDao function to access AppDao
    fun getAllPosts() : LiveData<MutableList<PostResponseItem>> {
        return dao.getAllPosts()
    }

    private fun insertPosts(postResponseItem: PostResponseItem){
        dao.insertPost(postResponseItem)
    }

    //uses the injected getCommentDao function to access CommentDao
    fun getComments(post_id: Int) : LiveData<MutableList<CommentsResponseItem>>{
        return commentDao.getAllComments(post_id)
    }

    private fun insertComments(commentsResponseItem: CommentsResponseItem){
        commentDao.insertComment(commentsResponseItem)
    }

    private val _getSinglePostLiveData: MutableLiveData<PostResponseItem> = MutableLiveData()
    val getSinglePostLiveData: LiveData<PostResponseItem> = _getSinglePostLiveData

    suspend fun getPostsFromApi() {
        val response = api.getPost()

        if (response.isSuccessful){
            dao.deletePosts()
            for (i in response.body()!!){
                insertPosts(i)
            }
        }else{
            //TODO
        }
    }

    suspend fun getCommentFromApi(postId: Int) {
        val response = api.getComments(postId)

        if (response.isSuccessful){
//            commentDao.deleteComments()
            for (i in response.body()!!){
                insertComments(i)
            }
        }else{
            //TODO
        }
    }

    suspend fun addPostToDatabase(postResponseItem: PostResponseItem){
        val response = api.createPost(postResponseItem)

        if (response.isSuccessful){
            insertPosts(response.body()!!)
        }else{
            //TODO
        }
    }

    suspend fun addCommentToDatabase(commentsResponseItem: CommentsResponseItem, postId: Int){
        val response = api.postComments(commentsResponseItem, postId)

        if (response.isSuccessful){
            insertComments(response.body()!!)
            Log.d("Comment", "Repository-success: ${response.body()}")
            Log.d("Comment", "Repository-success: $commentsResponseItem")
        }else{
            Log.d("Comment", "Repository-failure: ${response.body()}")
        }
    }

    suspend fun getSinglePost(postId: Int){
        val response = api.getSinglePost(postId)

        if (response.isSuccessful){
            _getSinglePostLiveData.postValue(response.body())
        }else{
            //TODO
        }
    }
}